package com.team7.propertypredict.service;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.team7.propertypredict.model.Amenity;
import com.team7.propertypredict.model.AmenityType;
import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.repository.ProjectRepository;

import helper.SearchResultHelper;

@Component
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository pRepo;
	
	@Autowired
	private AmenityTypeService atService;
	
	@Autowired
	private AmenityService aService;

	@Autowired
	private TransactionService tService;

	@Autowired
	private MapRestController mController;

	@Override
	public List<Project> findAllProjects() {
		return pRepo.findAllProjects();
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
	public ArrayList<Project> getMobileRecommendationsByDistrict(String district) {
		return pRepo.getMobileRecommendationsByDistrict(district);
	}

	@Override
	public ProjectDetails getProjectDetails(Integer pid) {
		ProjectDetails pd = new ProjectDetails();

		Project project = findProjectById(pid);
		Integer min = findMinAreaByProjectId(pid).intValue();
		Integer max = findMaxAreaByProjectId(pid).intValue();
		ArrayList<String> floors = findfloorRangeByProjectId(pid);

		Locale usa = new Locale("en", "US");
		NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
		String averagePrice = dollarFormat.format(findAveragePriceByProjectId(pid));

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
			topFloor = top.toString();
		}

		pd.setName(project.getName());
		pd.setStreet(project.getStreet());
		pd.setAveragePrice(averagePrice);
		pd.setArea(min + "-" + max + " (square metre)");
		pd.setFloorRange(topFloor);
		return pd;
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
			region = "Ouside Central Region (OCR)";
		}

		prop.setProjectId(pid);
		prop.setPropertyName(project.getName());
		prop.setRegion(region);
		prop.setStreet(project.getStreet());
		return prop;
	}
	
	@Override
	public Map<String, List<Location>> getLocationDetails(Integer pid){
		Map<String, List<Location>> details = new HashMap<String, List<Location>>();
		
		List<AmenityType> types = atService.findAll();
		for(AmenityType type: types) {
			List<Location> locations = new ArrayList<Location>();
			List<Amenity> amenities = aService.findAmenitiesByAmenityType(type.getTypeId());
			for(Amenity amenity: amenities) {
				String name = amenity.getName();
				Double lat = Double.parseDouble(amenity.getLatitude());
				Double lng = Double.parseDouble(amenity.getLongitude());
				Double distance = calculateDistance(pid, lat, lng);
				if(distance!=-1.0) {
					Location location = new Location(name, lat, lng, distance*1000);
					locations.add(location);
				}		
			}
			if(!locations.isEmpty()) {
				Collections.sort(locations, new Comparator<Location>(){
				    @Override
				    public int compare(Location l1, Location l2) {
				        return Double.compare(l1.getDistance(),l2.getDistance());
				    }
				});
				details.put(type.getType(), locations);
			}	
		}
		return details;
	}
	
	@Override
	public Map<String, List<Location>> filterLocationsByDistance(Map<String, List<Location>> locations, Integer filter){
		Map<String, List<Location>> filteredMap = new HashMap<String, List<Location>>();
		
		for(String key: locations.keySet()) {
			List<Location> loc = locations.get(key);
			List<Location> filteredLocations = loc.stream()
					.filter(x -> x.getDistance() < filter)
					.limit(3)
					.collect(Collectors.toList());
			if(!filteredLocations.isEmpty()) {
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
		String x = findXById(pid);
		String y = findYById(pid);
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
		String map2 = "&zoom=17&height=300&width=400";

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
			for(List<Location> locs: locations.values()) {
				for(Location loc: locs) {
					if(markers.size()<15) {
						markers.add(loc);
					}
				}
			}
			Integer idx = 0;
			for(Location marker: markers) {
				Double markerLat = marker.getLatitude();
				Double markerLng = marker.getLongitude();
				String markerPoint = AmenityHelper.markerPoints.get(idx);
				marker.setMarker(markerPoint);
				map += "|[" + markerLat + "," + markerLng + ",\"255,255,178\",\""+ markerPoint + "\"]";
				idx ++;
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
	public List<String> getAmenityNameFromOneMapKmlFile(String filename) throws IOException, ParserConfigurationException, SAXException{
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
			
			int count=0;
			for (int ex=0; ex<exts.getLength(); ex++) {
				Node ext = exts.item(ex);
				Element elem = (Element) ext;
				String value = elem.getAttribute("name");

				if(value.contains("NAME")) {
					count=ex;
					break;
				}
			}
			locations.add(exts.item(count).getTextContent());		
		}
		return locations;
	}
}
