package com.ctrip.search.dictionary.temporary.entity;

public class CityEntity {

	public Integer city;
	public String cityname_mapbar;
	public Float lon;
	public Float lat;
	
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
