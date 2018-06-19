package com.ctrip.search.dictionary.temporary.entity;

public class PoiEntity {

	private Integer poiid;
	private String name;
	private Float Lon;
	private Float lat;
	
	public PoiEntity(Integer poiid,String name,Float lon,Float lat){
		this.poiid = poiid;
		this.name = name;
		this.Lon = lon;
		this.lat = lat;
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
	public Float getLon() {
		return Lon;
	}
	public void setLon(Float lon) {
		Lon = lon;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "PoiEntity [poiid=" + poiid + ", name=" + name + ", Lon=" + Lon + ", lat=" + lat + "]";
	}
	
	
	
}
