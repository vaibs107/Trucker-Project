package io.egen.trucker.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import io.egen.trucker.entity.Geolocation;
import io.egen.trucker.entity.Reading;
import io.egen.trucker.entity.Vehicle;
import io.egen.trucker.exception.NotFoundException;
import io.egen.trucker.repository.GeolocationRepository;
import io.egen.trucker.service.GeolocationService;
import io.egen.trucker.service.VehicleService;

@Service
public class GeolocationServiceImpl implements GeolocationService {

	@Autowired
	private GeolocationRepository grepository;

	@Autowired
	private VehicleService vservice;

	@Override
	@Transactional
	public List<Geolocation> getAllGeolocationsByVin(String vin) {
		Vehicle existingVehicle = vservice.findById(vin);
		if (existingVehicle != null) {
			Optional<List<Geolocation>> geolocationsList = grepository.findByVin(vin);
			if (geolocationsList.isPresent())
				return geolocationsList.get();
			else
				return null;
		} else {
			throw new NotFoundException("Vehicle with id: " + vin + " not found!");
		}
	}

	@Override
	public void setGeolocations(Vehicle vehicle) {
		vehicle.getReadings().forEach(reading -> {
			System.out.println("In GeolocationServiceImpl, reading: \n" + reading);
			try {
				createGeolocation(reading, vehicle.getVin());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void createGeolocation(Reading reading, String vin) throws ApiException, InterruptedException, IOException {

		double lat = reading.getLatitude();
		double lng = reading.getLongitude();

		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDlAwc6POEqgebiSommBDhvOcjKmQcPpUg").build();

		GeocodingResult[] result = GeocodingApi.reverseGeocode(context, new LatLng(lat, lng)).await();

		if (result.length != 0) {
			System.out.println(result[0].geometry.location.lat);
			System.out.println(result[0].geometry.location.lng);

			Geolocation location = new Geolocation();
			location.setFormattedAddress(result[0].formattedAddress);
			location.setVin(vin);

			grepository.save(location);
		} else {
			ElevationResult eResult = ElevationApi.getByPoint(context, new LatLng(lat, lng)).await();
			if (eResult.elevation < 0) {
				String formattedAddress = "Location not found! Elevation of location is: " + eResult.elevation
						+ " meters. Located inside ocean!!!";

				Geolocation location = new Geolocation();
				location.setFormattedAddress(formattedAddress);
				location.setVin(vin);

				grepository.save(location);
			}
		}
		context.shutdown();
	}
}
