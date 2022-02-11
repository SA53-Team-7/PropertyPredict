package com.team7.propertypredict.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {

	private String SEARCHVAL;
	private String X;
	private String Y;
	private String LATITUDE;
	private String LONGITUDE;
	private String LONGTITUDE;
	
	public Results() {
		super();
	}

	public String getSEARCHVAL() {
		return SEARCHVAL;
	}

	public void setSEARCHVAL(String sEARCHVAL) {
		SEARCHVAL = sEARCHVAL;
	}

	public String getX() {
		return X;
	}

	public void setX(String x) {
		X = x;
	}

	public String getY() {
		return Y;
	}

	public void setY(String y) {
		Y = y;
	}

	public String getLATITUDE() {
		return LATITUDE;
	}

	public void setLATITUDE(String lATITUDE) {
		LATITUDE = lATITUDE;
	}

	public String getLONGITUDE() {
		return LONGITUDE;
	}

	public void setLONGITUDE(String lONGITUDE) {
		LONGITUDE = lONGITUDE;
	}

	public String getLONGTITUDE() {
		return LONGTITUDE;
	}

	public void setLONGTITUDE(String lONGTITUDE) {
		LONGTITUDE = lONGTITUDE;
	}
}
