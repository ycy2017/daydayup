package com.ctrip.search.dictionary.temporary.entity;

public class ActivityResultEntity {

	String cityId;
	String cityName;
	String poiid;
	String poiName;
	Integer sales;
	Float distance;
	
	
	public ActivityResultEntity(String cityId,String poiid,Integer sales) {
		this.cityId = cityId;
		this.poiid = poiid;
		this.sales = sales;
	}
	
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPoiid() {
		return poiid;
	}
	public void setPoiid(String poiid) {
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
