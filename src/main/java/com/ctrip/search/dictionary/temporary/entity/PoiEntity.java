package com.ctrip.search.dictionary.temporary.entity;

public class PoiEntity {

	Integer poiid;
	String name;
	Float glon;
	Float glat;
	Integer cityId;
	String cityName;
	Integer districtId;
	String districtName;
	String districtpathid;
	Integer commentcount;
	Float rating;
	
	public PoiEntity() {

	}

	public PoiEntity(Integer poiid, String name, Float glon, Float glat) {
		this.poiid = poiid;
		this.name = name;
		this.glon = glon;
		this.glat = glat;
	}
	
	
	
	
	
	public String getDistrictpathid() {
		return districtpathid;
	}

	public void setDistrictpathid(String districtpathid) {
		this.districtpathid = districtpathid;
	}

	public Integer getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(Integer commentcount) {
		this.commentcount = commentcount;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getPoiid() {
		return poiid;
	}

	public void setPoiid(Integer poiid) {
		this.poiid = poiid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getGlat() {
		return glat;
	}

	public void setGlat(Float glat) {
		this.glat = glat;
	}

	public Float getGlon() {
		return glon;
	}

	public void setGlon(Float glon) {
		this.glon = glon;
	}

	@Override
	public String toString() {
		return "PoiEntity [poiid=" + poiid + ", name=" + name + ", glon=" + glon + ", glat=" + glat + ", cityId="
				+ cityId + "]";
	}

	
}
