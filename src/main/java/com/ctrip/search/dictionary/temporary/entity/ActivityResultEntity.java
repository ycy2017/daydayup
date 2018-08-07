package com.ctrip.search.dictionary.temporary.entity;

public class ActivityResultEntity {

	
	Integer districtId;
	Integer cityId;
	String cityName;
	Integer poiid;
	String poiName;
	Integer poiDistrictId;
	Integer poiCityId;
	String poiCityName;
	int count;
	Integer sales;
	Float distance;
	String districtidPathid;
	Float comment_rating;
	
	
	public ActivityResultEntity() {

	}

	public ActivityResultEntity(Integer cityId, Integer poiid, Integer sales) {
		this.cityId = cityId;
		this.poiid = poiid;
		this.sales = sales;
	}
	
	public ActivityResultEntity(Integer poiid,String poiName,String districtidPathid,Float comment_rating) {
		this.poiid = poiid;
		this.poiName = poiName;
		this.districtidPathid = districtidPathid;
		this.comment_rating = comment_rating;
	}
	
	public String getDistrictidPathid() {
		return districtidPathid;
	}

	public void setDistrictidPathid(String districtidPathid) {
		this.districtidPathid = districtidPathid;
	}

	public Float getComment_rating() {
		return comment_rating;
	}

	public void setComment_rating(Float comment_rating) {
		this.comment_rating = comment_rating;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public Integer getPoiDistrictId() {
		return poiDistrictId;
	}

	public void setPoiDistrictId(Integer poiDistrictId) {
		this.poiDistrictId = poiDistrictId;
	}

	public Integer getPoiCityId() {
		return poiCityId;
	}

	public void setPoiCityId(Integer poiCityId) {
		this.poiCityId = poiCityId;
	}

	public String getPoiCityName() {
		return poiCityName;
	}

	public void setPoiCityName(String poiCityName) {
		this.poiCityName = poiCityName;
	}

	public ActivityResultEntity(Integer cityId, Integer poiid, Integer sales,int count) {
		this.cityId = cityId;
		this.poiid = poiid;
		this.sales = sales;
		this.count = count;
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

	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ActivityResultEntity [cityId=" + cityId + ", cityName=" + cityName + ", poiid=" + poiid + ", poiName="
				+ poiName + ", poiDistrictId=" + poiDistrictId + ", poiCityId=" + poiCityId + ", poiCityName="
				+ poiCityName + ", count=" + count + ", sales=" + sales + ", distance=" + distance + "]";
	}
	

}
