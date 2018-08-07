package com.ctrip.search.dictionary.temporary.entity;

public class CityEntity {

	Integer city;
	String cityname_mapbar;
	Float lon;
	Float lat;
	
	
	
	public CityEntity() {

	}

	public CityEntity(Integer city, String cityname_mapbar, Float lon, Float lat) {
		this.city = city;
		this.cityname_mapbar = cityname_mapbar;
		this.lon = lon;
		this.lat = lat;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCityname_mapbar() {
		return cityname_mapbar;
	}

	public void setCityname_mapbar(String cityname_mapbar) {
		this.cityname_mapbar = cityname_mapbar;
	}

	public Float getLon() {
		return lon;
	}

	public void setLon(Float lon) {
		this.lon = lon;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "CityEntity [city=" + city + ", cityname_mapbar=" + cityname_mapbar + ", lon=" + lon + ", lat=" + lat
				+ "]";
	}

}
