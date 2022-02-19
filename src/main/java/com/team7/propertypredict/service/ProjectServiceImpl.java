package com.team7.propertypredict.service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.team7.propertypredict.controller.MapRestController;
import com.team7.propertypredict.helper.AmenityHelper;
import com.team7.propertypredict.helper.Location;
import com.team7.propertypredict.helper.ProjectDetails;
import com.team7.propertypredict.helper.Property;
import com.team7.propertypredict.helper.SearchResultHelper;
import com.team7.propertypredict.helper.TransactionHelper;
import com.team7.propertypredict.model.Amenity;
import com.team7.propertypredict.model.AmenityType;
import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.model.User;
import com.team7.propertypredict.repository.ProjectRepository;
import com.team7.propertypredict.repository.UserRepository;

@Component
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository pRepo;

	@Autowired
	private UserRepository uRepo;

	@Autowired
	private AmenityTypeService atService;

	@Autowired
	private AmenityService aService;

	@Autowired
	private TransactionService tService;

	@Autowired
	private UserService uService;

	@Autowired
	private ProjectService pService;

	@Autowired
	private MapRestController mController;

	@Autowired

	@Override
	public List<Project> findAllProjects() {
		return pRepo.findAllProjects();
	}

	@Override
	public List<String> findAllProjectNames() {
		List<Project> projects = findAllProjects();
		List<String> names = new ArrayList<String>();
		for (Project project : projects) {
			names.add(project.getName());
		}
		return names;
	}

	@Override
	public List<Project> getTop20Projects() {
		return pRepo.getTop20Projects();
	}

	@Override
	public ArrayList<Project> searchProjects(String searchString) {
		return pRepo.searchProjects(searchString);
	}

	@Override
	public ArrayList<Project> findProjectsByStreet(String street) {
		return pRepo.findProjectsByStreet(street);
	}

	@Override
	public Project findProjectByName(String name) {
		return pRepo.findProjectByName(name);
	}

	@Override
	public ArrayList<Project> getMobileRecommendationsByDistrict(String district) {
		if (district.length() == 1) {
			district = "0" + district;
		}
		return pRepo.getMobileRecommendationsByDistrict(district);
	}

	@Override
	public ProjectDetails getProjectDetails(Integer pid) {
		ProjectDetails pd = new ProjectDetails();

		Project project = findProjectById(pid);
		Integer min = findMinAreaByProjectId(pid).intValue();
		Integer max = findMaxAreaByProjectId(pid).intValue();
		ArrayList<String> floors = findfloorRangeByProjectId(pid);
		List<String> TOPYears = tService.getDistinctTOP(pid);
		List<String> tenureYears = tService.getDistinctTenure(pid);
		String map = getMap(project.getProjectId());
		Boolean exist = (map == "@{/images/unknown.png}") ? false : true;

		Locale usa = new Locale("en", "US");
		NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
		Double av = findAveragePriceByProjectId(pid);
		Integer avInt = (int) Math.round(av);
		String averagePrice = dollarFormat.format(av);
		
		String region = project.getSegment();
		if (region == "CCR") {
			region = "Core Central Region (CCR)";
		} else if (region == "RCR") {
			region = "Rest of Central Region (RCR)";
		} else {
			region = "Outside Central Region (OCR)";
		}

		Integer top = 0;
		for (String floor : floors) {
			if (floor.length() == 5) {
				String f = floor.substring(3, 5);
				Integer t = Integer.parseInt(f);
				if (t > top) {
					top = t;
				}
			}
		}
		String topFloor = "";
		if (top == 0) {
			topFloor = "-";
		} else if (top / 10 == 0) {
			topFloor = "01-0" + top;
		} else {
			topFloor = "01-" +top;
		}
		pd.setProjectId(project.getProjectId());
		pd.setName(project.getName());
		pd.setSegment(region);
		pd.setStreet(project.getStreet());
		pd.setPrice(avInt);
		pd.setAveragePrice(averagePrice);
		pd.setArea(min + "-" + max + " (square metre)");
		pd.setFloorRange(topFloor);
		pd.setTOPYears(TOPYears);
		pd.setTenureYears(tenureYears);
		pd.setMap(map);
		pd.setMapExist(exist);
		return pd;
	}

	@Override
	public List<ProjectDetails> getProjectsDetails(Integer uid) {
		User user = uService.findUserById(uid);
		List<Project> projects = user.getProjects();
		List<ProjectDetails> projectsDetails = new ArrayList<ProjectDetails>();

		for (Project project : projects) {
			ProjectDetails pd = pService.getProjectDetails(project.getProjectId());
			projectsDetails.add(pd);
		}
		return projectsDetails;
	}

	@Override
	public Property getPropertyDetails(Integer pid) {
		Property prop = new Property();
		Project project = findProjectById(pid);
		String region = project.getSegment();
		if (region == "CCR") {
			region = "Core Central Region (CCR)";
		} else if (region == "RCR") {
			region = "Rest of Central Region (RCR)";
		} else {
			region = "Outside Central Region (OCR)";
		}
		prop.setProjectId(pid);
		prop.setPropertyName(project.getName());
		prop.setRegion(region);
		prop.setStreet(project.getStreet());
		return prop;
	}

	@Override
	public Map<String, List<Location>> getLocationDetails(Integer pid) {
		Map<String, List<Location>> details = new HashMap<String, List<Location>>();

		List<AmenityType> types = atService.findAll();
		for (AmenityType type : types) {
			List<Location> locations = new ArrayList<Location>();
			List<Amenity> amenities = aService.findAmenitiesByAmenityType(type.getTypeId());
			for (Amenity amenity : amenities) {
				String name = amenity.getName();
				Double lat = Double.parseDouble(amenity.getLatitude());
				Double lng = Double.parseDouble(amenity.getLongitude());
				Double distance = calculateDistance(pid, lat, lng);
				if (distance != -1.0) {
					Location location = new Location(name, lat, lng, distance * 1000);
					locations.add(location);
				}
			}
			if (!locations.isEmpty()) {
				Collections.sort(locations, new Comparator<Location>() {
					@Override
					public int compare(Location l1, Location l2) {
						return Double.compare(l1.getDistance(), l2.getDistance());
					}
				});
				details.put(type.getType(), locations);
			}
		}
		return details;
	}

	@Override
	public Map<String, List<Location>> filterLocationsByDistance(Map<String, List<Location>> locations,
			Integer filter) {
		Map<String, List<Location>> filteredMap = new HashMap<String, List<Location>>();

		for (String key : locations.keySet()) {
			List<Location> loc = locations.get(key);
			List<Location> filteredLocations = loc.stream().filter(x -> x.getDistance() < filter).limit(3)
					.collect(Collectors.toList());
			if (!filteredLocations.isEmpty()) {
				filteredMap.put(key, filteredLocations);
			}
		}
		return filteredMap;
	}

	@Override
	public Project findProjectById(Integer pid) {
		return pRepo.findProjectById(pid);
	}

	@Override
	public Double findAveragePriceByProjectId(Integer pid) {
		return pRepo.findAveragePriceByProjectId(pid);
	}

	@Override
	public Integer findTotalUnitsByProjectId(Integer pid) {
		return pRepo.findTotalUnitsByProjectId(pid);
	}

	@Override
	public String findTenureByProjectId(Integer pid) {
		return pRepo.findTenureByProjectId(pid);
	}

	@Override
	public String findSaleTypeByProjectId(Integer pid) {
		return pRepo.findSaleTypeByProjectId(pid);
	}

	@Transactional
	public Double findMinAreaByProjectId(Integer pid) {
		Double min = pRepo.findMinAreaByProjectId(pid);
		return min;
	}

	@Transactional
	public Double findMaxAreaByProjectId(Integer pid) {
		Double max = pRepo.findMaxAreaByProjectId(pid);
		return max;
	}

	@Transactional
	public ArrayList<String> findfloorRangeByProjectId(Integer pid) {
		ArrayList<String> floors = pRepo.findfloorRangeByProjectId(pid);
		return floors;
	}

	@Override
	public ArrayList<String> findDistinctSegment() {
		ArrayList<String> result = new ArrayList<>();
		result.add("All");
		result.addAll(pRepo.findDistinctSegments());
		return result;
	}

	@Override
	public ArrayList<SearchResultHelper> searchProjectsWeb(String searchStr, String segment, String district,
			String type) {
		ArrayList<Project> result = pRepo.searchProjectsWeb(searchStr, segment, district, type);
		ArrayList<SearchResultHelper> searchResults = new ArrayList<>();

		for (Project p : result) {

			String tenureModified = "";
			String districtModified = "";
			String typeModified = "";

			// Get tenure
			List<String> tenureList = tService.getDistinctTenure(p.getProjectId());

			for (String s : tenureList) {
				tenureModified += s + ",";
			}

			// Get district
			List<String> districtList = tService.getDistrictValues(p.getProjectId());

			for (String s : districtList) {
				districtModified += s + ",";
			}

			// Get type
			List<String> typeList = tService.getDistinctPropertyTypeById(p.getProjectId());
			for (String s : typeList) {
				typeModified += s + ",";
			}

			SearchResultHelper s = new SearchResultHelper(p.getProjectId().toString(), p.getName(), p.getStreet(),
					p.getSegment(), districtModified.substring(0, districtModified.lastIndexOf(',')),
					typeModified.substring(0, typeModified.lastIndexOf(',')),
					tenureModified.substring(0, tenureModified.lastIndexOf(',')));
			searchResults.add(s);

		}

		return searchResults;
	}

	@Override
	public ArrayList<String> findDistinctPropTypeByPara(String searchStr, String segment, String district,
			String type) {
		return pRepo.findDistinctTypeByPara(searchStr, segment, district, type);
	}

	@Override
	public ArrayList<String> findDistinctTenureByPara(String searchStr, String segment, String district, String type) {
		List<String> result = pRepo.findDistinctTenureByPara(searchStr, segment, district, type);
		ArrayList<String> filters = new ArrayList<>();

		for (String s : result) {
			String[] splitArr = s.split(" ", 2);

			if (!filters.contains(splitArr[0])) {
				filters.add(splitArr[0]);
			}
		}

		return filters;
	}

	@Override
	public Property getProperty(Integer pid) {
		String x = findXById(pid) == null ? "" : findXById(pid);
		String y = findYById(pid) == null ? "" : findYById(pid);
		String lat;
		String lng;

		if (x.isEmpty() || y.isEmpty()) {
			lat = "";
			lng = "";
		} else {
			lat = mController.getCoordinates(x, y).getLatitude().toString();
			lng = mController.getCoordinates(x, y).getLongitude().toString();
		}

		Property p = new Property(pid);
		p.setyCoordinates(lat);
		p.setxCoordinates(lng);
		return p;
	}

	@Override
	public String getMap(Integer pid) {

		String map;
		String map1 = "https://developers.onemap.sg/commonapi/staticmap/getStaticImage?" + "layerchosen=default&lat=";
		String map2 = "&zoom=12&height=350&width=350";

		Property prop = getProperty(pid);

		if (prop.getyCoordinates().isEmpty() || prop.getxCoordinates().isEmpty()) {
			map = "@{/images/unknown.png}";
		} else {
			String lat = prop.getyCoordinates();
			String lng = prop.getxCoordinates();
			map = map1 + lat + "&lng=" + lng + map2 + "&points=[" + lat + "," + lng + ",\"168,228,160\", \"P\"]";
		}
		return map;
	}

	@Override
	public String getMapWithNearestTrain(Integer pid) {
		Map<String, Double> nearestMrt = getNearestTrainAndLocation(pid);
		String nearestLocation = "";
		for (Map.Entry<String, Double> entry : nearestMrt.entrySet()) {
			nearestLocation = entry.getKey();
		}

		Amenity train = aService.findAmenityByName(nearestLocation);
		String nearestLat = train.getLatitude();
		String nearestLng = train.getLongitude();

		String map;
		String map1 = "https://developers.onemap.sg/commonapi/staticmap/getStaticImage?" + "layerchosen=default&lat=";
		String map2 = "&zoom=15&height=300&width=400";

		Property prop = getProperty(pid);

		if (prop.getyCoordinates().isEmpty() || prop.getxCoordinates().isEmpty()) {
			map = "@{/images/unknown.png}";
		} else {
			String lat = prop.getyCoordinates();
			String lng = prop.getxCoordinates();
			map = map1 + lat + "&lng=" + lng + map2 + "&points=[" + lat + "," + lng + ",\"168,228,160\", \"P\"]";
		}
		map += "|[" + nearestLat + "," + nearestLng + ",\"255,255,178\",\"" + "A" + "\"]";

		return map;
	}

	@Override
	public Map<String, Double> getNearestTrainAndLocation(Integer pid) {
		Map<String, Double> mrtHashMap = new HashMap<>();
		List<Amenity> trains = aService.getAllTrainStations();
		for (Amenity train : trains) {
			Double lat = Double.parseDouble(train.getLatitude());
			Double lng = Double.parseDouble(train.getLongitude());
			Double distance = calculateDistance(pid, lat, lng);
			mrtHashMap.put(train.getName(), distance);
		}
		String nearestLocation = "";
		double nearestDistance = Collections.min(mrtHashMap.values());

		for (Map.Entry<String, Double> entry : mrtHashMap.entrySet()) {
			if (entry.getValue().equals(nearestDistance)) {
				nearestLocation = entry.getKey();
			}
		}
		Map<String, Double> nearestMrt = new HashMap<>();
		nearestMrt.put(nearestLocation, nearestDistance);

		return nearestMrt;
	}

	@Override
	public String getMapWithAmenities(Integer pid, Map<String, List<Location>> locations) {
		String map;
		String map1 = "https://developers.onemap.sg/commonapi/staticmap/getStaticImage?" + "layerchosen=default&lat=";
		String map2 = "&zoom=15&height=300&width=400";

		Property prop = getProperty(pid);

		if (prop.getyCoordinates().isEmpty() || prop.getxCoordinates().isEmpty()) {
			map = "@{/images/unknown.png}";
		} else {
			String lat = prop.getyCoordinates();
			String lng = prop.getxCoordinates();
			map = map1 + lat + "&lng=" + lng + map2 + "&points=[" + lat + "," + lng + ",\"168,228,160\", \"P\"]";

			List<Location> markers = new ArrayList<Location>();
			for (List<Location> locs : locations.values()) {
				for (Location loc : locs) {
					if (markers.size() < 15) {
						markers.add(loc);
					}
				}
			}
			Integer idx = 0;
			for (Location marker : markers) {
				Double markerLat = marker.getLatitude();
				Double markerLng = marker.getLongitude();
				String markerPoint = AmenityHelper.markerPoints.get(idx);
				marker.setMarker(markerPoint);
				map += "|[" + markerLat + "," + markerLng + ",\"255,255,178\",\"" + markerPoint + "\"]";
				idx++;
			}
		}
		return map;
	}

	@Override
	public String findXById(Integer pid) {
		return pRepo.findXById(pid);
	}

	@Override
	public String findYById(Integer pid) {
		return pRepo.findYById(pid);
	}

	@Transactional
	@Override
	public Double calculateDistance(Integer pid, Double latitude, Double longitude) {

		Property prop = getProperty(pid);
		Double distance;

		if (prop.getyCoordinates().isEmpty() || prop.getxCoordinates().isEmpty()) {
			distance = -1.0;

		} else {
			double lat = Double.parseDouble(prop.getyCoordinates());
			double lng = Double.parseDouble(prop.getxCoordinates());
			double propertyLatitude = Math.toRadians(lat);
			double propertyLongitude = Math.toRadians(lng);
			double locationLatitude = Math.toRadians(latitude);
			double locationLongitude = Math.toRadians(longitude);

			// Haversine formula
			double dLat = locationLatitude - propertyLatitude;
			double dLon = locationLongitude - propertyLongitude;

			double a = Math.pow(Math.sin(dLat / 2), 2)
					+ Math.cos(propertyLatitude) * Math.cos(locationLatitude) * Math.pow(Math.sin(dLon / 2), 2);
			double c = 2 * Math.asin(Math.sqrt(a));

			double r = 6371;

			distance = (c * r);
		}
		return distance;
	}

	@Override
	public Map<String, Double> getAmenities(Integer pid, List<Location> locations) {

		Property prop = getProperty(pid);
		Double distance;
		Map<String, Double> amenities = new HashMap<>();

		if (prop.getyCoordinates().isEmpty() || prop.getxCoordinates().isEmpty()) {
			distance = -1.0;
			amenities.put("unavailable", distance);
		} else {
			for (Location location : locations) {
				Double lat = location.getLatitude();
				Double lng = location.getLongitude();
				amenities.put(location.getName(), calculateDistance(pid, lat, lng));
			}
		}
		return amenities;
	}

	@Override
	public List<String> getAmenityNameFromOneMapKmlFile(String filename)
			throws IOException, ParserConfigurationException, SAXException {
		List<String> locations = new ArrayList<String>();

		Resource resource = new ClassPathResource(filename);
		File file = resource.getFile();
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(file);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("Placemark");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			Element element = (Element) nNode;
			NodeList exts = element.getElementsByTagName("SimpleData");

			int count = 0;
			for (int ex = 0; ex < exts.getLength(); ex++) {
				Node ext = exts.item(ex);
				Element elem = (Element) ext;
				String value = elem.getAttribute("name");

				if (value.contains("NAME")) {
					count = ex;
					break;
				}
			}
			locations.add(exts.item(count).getTextContent());
		}
		return locations;
	}

	@Override
	public List<Project> findAllShortlistProjects(Integer uid) {
		return pRepo.findAllShortlistProjects(uid);
	}

	@Override
	public void updateShortlistedProject(Integer pid, Integer uid) {
		User user = uService.findUserById(uid);
		Project project = pService.findProjectById(pid);
		List<Project> projectList = user.getProjects();

		if (projectList.contains(project)) {
			projectList.remove(project);
		} else {
			projectList.add(project);
		}
		user.setProjects(projectList);
		uRepo.saveAndFlush(user);
	}

	@Override
	public Integer checkIfShortlisted(Integer pid, Integer uid) {
		Integer shortlisted = -1;

		if (uid != null) {
			User user = uService.findUserById(uid);
			Project project = pService.findProjectById(pid);
			List<Project> list = user.getProjects();
			if (list.contains(project)) {
				shortlisted = 1;
			}
		}
		return shortlisted;
	}

	@Override
	public List<SearchResultHelper> getPopularLocationsByTxn() {
		List<SearchResultHelper> recommendations = new ArrayList<SearchResultHelper>();
		List<String> pids = tService.getTopProjectIDsByTransactions();

		for (String id : pids) {
			String districtModified = "", typeModified = "";
			Project p = pRepo.findProjectById(Integer.valueOf(id));

			// Get district
			List<String> districtList = tService.getDistrictValues(p.getProjectId());

			for (String s : districtList) {
				districtModified += s + ",";
			}

			// Get type
			List<String> typeList = tService.getDistinctPropertyTypeById(p.getProjectId());
			for (String s : typeList) {
				typeModified += s + ",";
			}

			SearchResultHelper s = new SearchResultHelper(p.getProjectId().toString(), p.getName(), p.getStreet(),
					p.getSegment(), districtModified.substring(0, districtModified.lastIndexOf(',')),
					typeModified.substring(0, typeModified.lastIndexOf(',')), null);

			recommendations.add(s);
		}

		return recommendations;
	}

	@Override
	public List<SearchResultHelper> getUsersRecommendations(Integer userId) {
		List<Project> projects = tService.getSimilarProjectIDsByPrice(userId);
		List<SearchResultHelper> results = new ArrayList<SearchResultHelper>();

		for (Project p : projects) {
			String tenureModified = "";
			String districtModified = "";
			String typeModified = "";

			// Get tenure
			List<String> tenureList = tService.getDistinctTenure(p.getProjectId());

			for (String s : tenureList) {
				tenureModified += s + ",";
			}

			// Get district
			List<String> districtList = tService.getDistrictValues(p.getProjectId());

			for (String s : districtList) {
				districtModified += s + ",";
			}

			// Get type
			List<String> typeList = tService.getDistinctPropertyTypeById(p.getProjectId());
			for (String s : typeList) {
				typeModified += s + ",";
			}

			SearchResultHelper s = new SearchResultHelper(p.getProjectId().toString(), p.getName(), p.getStreet(),
					p.getSegment(), districtModified.substring(0, districtModified.lastIndexOf(',')),
					typeModified.substring(0, typeModified.lastIndexOf(',')),
					tenureModified.substring(0, tenureModified.lastIndexOf(',')));

			results.add(s);
		}
		return results;
	}

	@Override
	public List<SearchResultHelper> getRecentTxn() {
		List<Project> recentProjs = tService.getRecentlyTransactedProjects();
		List<SearchResultHelper> results = new ArrayList<SearchResultHelper>();

		for (Project p : recentProjs) {
			String tenureModified = "";
			String districtModified = "";
			String typeModified = "";

			// Get tenure
			List<String> tenureList = tService.getDistinctTenure(p.getProjectId());

			for (String s : tenureList) {
				tenureModified += s + ",";
			}

			// Get district
			List<String> districtList = tService.getDistrictValues(p.getProjectId());

			for (String s : districtList) {
				districtModified += s + ",";
			}

			// Get type
			List<String> typeList = tService.getDistinctPropertyTypeById(p.getProjectId());
			for (String s : typeList) {
				typeModified += s + ",";
			}

			SearchResultHelper s = new SearchResultHelper(p.getProjectId().toString(), p.getName(), p.getStreet(),
					p.getSegment(), districtModified.substring(0, districtModified.lastIndexOf(',')),
					typeModified.substring(0, typeModified.lastIndexOf(',')),
					tenureModified.substring(0, tenureModified.lastIndexOf(',')));

			results.add(s);
		}

		return results;
	}

	@Override
	public List<String> getNamesFromProjectDetailList(List<ProjectDetails> pd, Integer uid) {
		List<String> names = new ArrayList<String>();

		for (ProjectDetails p : pd) {
			names.add(p.getName());
		}
		return names;
	}
	
	@Override
	public List<ProjectDetails> getProjectDetailFromSearch(List<ProjectDetails> pd, String str){
		List<ProjectDetails> filterProjects = new ArrayList<ProjectDetails>();
		
		for (ProjectDetails p : pd) {
			if(p.getName().toLowerCase().contains(str.toLowerCase())) {
				filterProjects.add(p);
			}
		}
		return filterProjects;
	}
	
	@Override
	public List<ProjectDetails> filterProjectDetailList(List<ProjectDetails> pd, String filter){
		if (filter != null) {
			if (filter.equals("nameAsc")) {
				Collections.sort(pd, new Comparator<ProjectDetails>() {
					@Override
					public int compare(ProjectDetails o1, ProjectDetails o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				
			} else if (filter.equals("nameDes")) {
				Collections.sort(pd, new Comparator<ProjectDetails>() {
					@Override
					public int compare(ProjectDetails o1, ProjectDetails o2) {
						return o2.getName().compareTo(o1.getName());
					}
				});
			} else if (filter.equals("priceAsc")) {
				Collections.sort(pd, new Comparator<ProjectDetails>() {
					@Override
					public int compare(ProjectDetails o1, ProjectDetails o2) {
						return o1.getPrice() - o2.getPrice();
					}
				});
			} else if (filter.equals("priceDes")) {
				Collections.sort(pd, new Comparator<ProjectDetails>() {
					@Override
					public int compare(ProjectDetails o1, ProjectDetails o2) {
						return o2.getPrice() - o1.getPrice();
					}
				});
			}
		}
		return pd;
	}
	
	@Override
	public String validateSearchStrings(String str1, String str2, String str3) {
		String errorMsg = "No error";
		List<String> names = pService.findAllProjectNames();

		if (!names.contains(str1.toUpperCase())) {
			errorMsg = "There is no project with name \"" + str1 + "\" . Reenter again for Project 1.";
		} else if (!names.contains(str2.toUpperCase())) {
			errorMsg = "There is no project with name \"" + str2 + "\" . Reenter again for Project 2.";
		} else if (!names.contains(str3.toUpperCase())) {
			errorMsg = "There is no project with name \"" + str3 + "\" . Reenter again for Project 3.";
		} else {
			List<String> searchStrs = Arrays.asList(str1, str2, str3);
			List<String> distinctNames = searchStrs.stream().distinct().collect(Collectors.toList());
			if (distinctNames.size() == 1) {
				errorMsg = "Need at least 2 distinct project names. Reenter the names again.";
			}
		}
		return errorMsg;
	}
	
	@Override
	public List<ProjectDetails> getProjectDetailsFromSearchStrings(String str1, String str2, String str3) throws ParseException{
		List<String> searchStrs = Arrays.asList(str1, str2, str3);
		List<Project> projects = new ArrayList<Project>();
		List<ProjectDetails> projectDetails = new ArrayList<ProjectDetails>();	
		
		for (String str : searchStrs) {
			projects.add(pService.findProjectByName(str));
		}
		for (Project project : projects) {
			List<String> dates = new ArrayList<String>();
			List<Double> prices = new ArrayList<Double>();
			List<TransactionHelper> ths = new ArrayList<TransactionHelper>();
			
			List<Transaction> txns = project.getTransactions();
			for(Transaction txn: txns) {
				String d = txn.getContractDate();
				String month = d.substring(0,1) == "0" ? d.substring(1,2) : d.substring(0,2);
				String y = "20";
				String year = d.substring(2);
				String txnDate = 15 + "/" + month + "/" + y + year;
			    Date date =new SimpleDateFormat("dd/MM/yyyy").parse(txnDate);  
				//DateTime txnDate = new Date(year, parseInt(month) - 1, 15);
				TransactionHelper th = new TransactionHelper(date, txn.getPrice());
				ths.add(th);
			}
			Collections.sort(ths, new Comparator<TransactionHelper>() {
				@Override
				public int compare(TransactionHelper o1, TransactionHelper o2) {
					return o1.getDate().compareTo(o2.getDate());
				}
			});
			for(TransactionHelper th: ths) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM");  
				String strDate = dateFormat.format(th.getDate());  
				dates.add(strDate);
				prices.add(th.getPrice());
			}
			ProjectDetails pd = pService.getProjectDetails(project.getProjectId());
			pd.setPrices(prices);
			pd.setDates(dates);
			projectDetails.add(pd);
			
		}	
		return projectDetails;
	}
}
