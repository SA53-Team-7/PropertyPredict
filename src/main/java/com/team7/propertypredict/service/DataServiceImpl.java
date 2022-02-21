package com.team7.propertypredict.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.SaleType;
import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.repository.ProjectRepository;
import com.team7.propertypredict.repository.SaleTypeRepository;
import com.team7.propertypredict.repository.TransactionRepository;

@Component
public class DataServiceImpl implements DataService {
	@Autowired
	ProjectRepository prepo;
	
	@Autowired
	TransactionRepository trepo;
	
	@Autowired
	SaleTypeRepository srepo;
	
	@Value("${spring.datasource.url}")
	private String db;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	// Access key
	private String key = "8d92f1c1-fa02-4764-8f80-4ecca9296ca9";
	
	private HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(10))
			.build();
	
	
	@Override
	public String getToken() {
		HttpRequest requestToken = HttpRequest.newBuilder(
				URI.create("https://www.ura.gov.sg/uraDataService/insertNewToken.action"))
					.header("AccessKey", key)
					.build();
				
		HttpResponse<String> responseToken = null;
		
		try {
			responseToken = httpClient.send(requestToken, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// get token
		Integer index = responseToken.body().indexOf("Result") + 9;
		String token = responseToken.body().substring(index).replaceAll("\"|}", "");
		return token;
	}

	@Override
	public void extractData(String token, Integer batch) {
		
		String input = "";
		SaleType[] saleTypes = new SaleType[3];
		
		// Get all sales type objects available
		for (int i = 0; i < 3; i++) {
			saleTypes[i] = srepo.findById(i+1).orElse(null);
		}
		
		// Send request to URA's API
		HttpRequest requestTxn = HttpRequest.newBuilder(
				URI.create("https://www.ura.gov.sg/uraDataService/invokeUraDS?service=PMI_Resi_Transaction&batch=" + String.valueOf(batch)))
					.headers("AccessKey", key, "Token", token)
					.build();
				
		try {
			HttpResponse<String> responseTxn = httpClient.send(requestTxn, HttpResponse.BodyHandlers.ofString());
			input = responseTxn.body();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		if (input.charAt(0) != '{') {
			input = input.substring(input.indexOf("{"));
		}
		
		// Get project
		JSONArray projectResults = null;
		
		try {
			JSONObject root = new JSONObject(input);
			projectResults = root.getJSONArray("Result");

		} catch (JSONException e) {
			e.printStackTrace();
		}
				
		// Iterate through all projects
		for (int i = 0; i < projectResults.length(); i++) {
					
			JSONObject projObj = null;
			
			// Create new project object
			Project txn = new Project();

			try {
				projObj = projectResults.getJSONObject(i);
				txn.setStreet(projObj.isNull("street") ? null : projObj.getString("street"));
				txn.setName(projObj.isNull("project") ? null : projObj.getString("project"));		
				txn.setSegment(projObj.isNull("marketSegment") ? null : projObj.getString("marketSegment"));
				txn.setX(projObj.isNull("x") ? null : projObj.getString("x"));
				txn.setY(projObj.isNull("y") ? null : projObj.getString("y"));
			} catch (JSONException e) {
				e.printStackTrace();
			}			

			Project p = prepo.saveAndFlush(txn);
				
			List<Transaction> transactionList = new ArrayList<>();
					
			// Iterate through all transactions for a project
			JSONArray transactionArr = null;
			
			try {
				transactionArr = projObj.getJSONArray("transaction");
			} catch (JSONException e) {
				e.printStackTrace();
			}
					
			for (int j = 0; j < transactionArr.length(); j++) {
				JSONObject txnObj = null;
				Transaction t = new Transaction();

				try {
					txnObj = transactionArr.getJSONObject(j);
					t.setContractDate(txnObj.isNull("contractDate") ? null : txnObj.getString("contractDate"));
					t.setFloorArea(txnObj.isNull("area") ? null : txnObj.getDouble("area"));
					t.setPrice(txnObj.isNull("price") ? null : txnObj.getDouble("price"));
					t.setPropType(txnObj.isNull("propertyType") ? null : txnObj.getString("propertyType"));
					t.setAreaType(txnObj.isNull("typeOfArea") ? null : txnObj.getString("typeOfArea"));
					t.setTenure(txnObj.isNull("tenure") ? null : txnObj.getString("tenure"));
					t.setFloorRange(txnObj.isNull("floorRange") ? null : txnObj.getString("floorRange"));
					t.setDistrict(txnObj.isNull("district") ? null : txnObj.getString("district"));
					t.setNoOfUnits(txnObj.isNull("noOfUnits") ? null : txnObj.getInt("noOfUnits"));
					
					int sale_id = txnObj.isNull("typeOfSale") ? 3 : txnObj.getInt("typeOfSale");
					t.setSaleType(saleTypes[sale_id - 1]);		
					
					t.setProject(p);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				transactionList.add(t);
			}
					
			txn.setTransactions(transactionList);
			prepo.saveAndFlush(txn);
			// trepo.saveAllAndFlush(transactionList);
		}
	}

	@Override
	public void clearTables() {

		try {
			Connection con = DriverManager.getConnection(db, username, password);
			Statement truncQuery = con.createStatement();
			truncQuery.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
			truncQuery.executeUpdate("TRUNCATE TABLE transactions");
			truncQuery.executeUpdate("TRUNCATE TABLE projects");
			truncQuery.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public long getProjectCount() {
		return prepo.count();
	}

	@Override
	public long getTransactionCount() {
		return trepo.count();
	}	
}
