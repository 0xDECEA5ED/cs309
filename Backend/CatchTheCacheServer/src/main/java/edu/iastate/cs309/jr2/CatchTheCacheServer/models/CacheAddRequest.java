package edu.iastate.cs309.jr2.CatchTheCacheServer.models;

public class CacheAddRequest {
	private String name;
	private double longitude, latitude;
	//private String creator;

	public CacheAddRequest(String name, double lon, double lat) {
		this.name = name;
		this.longitude = lon;
		this.latitude = lat;
		//this.creator = creator;
	}

	public String getName() {
		return this.name;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public double getLatitude() {
		return this.latitude;
	}

	/*public String getCreator() {
		return this.creator;
	}*/

}
