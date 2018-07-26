package io.egen.trucker.service;

import java.io.IOException;
import java.util.List;

import com.google.maps.errors.ApiException;

import io.egen.trucker.entity.Geolocation;
import io.egen.trucker.entity.Vehicle;

public interface GeolocationService {

	public List<Geolocation> getAllGeolocationsByVin(String vin);

	public void setGeolocations(Vehicle vehicle) throws ApiException, InterruptedException, IOException;
}
