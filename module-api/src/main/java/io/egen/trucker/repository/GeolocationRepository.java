package io.egen.trucker.repository;

import java.util.List;

import io.egen.trucker.entity.Geolocation;

public interface GeolocationRepository {

	public List<Geolocation> getAllGeolocationsByVin(String vin);

	public void setGeolocations(String formattedAddress, String vin);
}
