package com.team7.propertypredict.helper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmenityAPI {
	 
	private Integer found;
	private Integer totalNumPages;
	private Integer pageNum;
	private List<Results> results;
	
	public AmenityAPI() {
		super();
	}

	public Integer getFound() {
		return found;
	}

	public void setFound(Integer found) {
		this.found = found;
	}

	public Integer getTotalNumPages() {
		return totalNumPages;
	}

	public void setTotalNumPages(Integer totalNumPages) {
		this.totalNumPages = totalNumPages;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}
	
	

}
