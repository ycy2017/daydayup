package com.ctrip.search.dictionary.temporary.entity;

public class ActivityResultEntity {

	Integer cityId;
	String cityName;
	Integer poiid;
	String poiName;
	Integer sales;
	Float distance;
	
	
	public ActivityResultEntity(Integer cityId,Integer poiid,Integer sales) {
		this.cityId = cityId;
		this.poiid = poiid;
		this.sales = sales;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public Integer getPoiid() {
		return poiid;
	}

	public void setPoiid(Integer poiid) {
		this.poiid = poiid;
	}

	public String getPoiName() {
		return poiName;
	}
	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "ActivityResultEntity [cityId=" + cityId + ", cityName=" + cityName + ", poiid=" + poiid + ", poiName="
				+ poiName + ", sales=" + sales + ", distance=" + distance + "]";
	}
	
	
	
}
