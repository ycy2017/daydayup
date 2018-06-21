package com.ctrip.search.dictionary.temporary.entity;

public class ActivitySearchEntity {

	private String locationcityids;
	
	private String poiids;
	
	private Integer sales;

	
	public String getLocationcityids() {
		return locationcityids;
	}

	public void setLocationcityids(String locationcityids) {
		this.locationcityids = locationcityids;
	}

	public String getPoiids() {
		return poiids;
	}

	public void setPoiids(String poiids) {
		this.poiids = poiids;
	}

	

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	@Override
	public String toString() {
		return "ActivitySearchEntity [locationcityids=" + locationcityids + ", poiids=" + poiids + ", sales=" + sales
				+ "]";
	}

	
	
	
}
