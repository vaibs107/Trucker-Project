package io.egen.trucker.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.egen.trucker.entity.Reading;
import io.egen.trucker.entity.Vehicle;
import io.egen.trucker.exception.NotFoundException;
import io.egen.trucker.repository.VehicleRepository;
import io.egen.trucker.service.AlertService;
import io.egen.trucker.service.GeolocationService;
import io.egen.trucker.service.ReadingService;
import io.egen.trucker.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {
	List<Reading> readingsList = new ArrayList<Reading>();

	@Autowired
	private VehicleRepository vrepository;

	@Autowired
	private ReadingService rservice;

	@Autowired
	private AlertService aservice;

	@Autowired
	private GeolocationService gservice;

	@Override
	@Transactional
	public Vehicle create(Vehicle vehicle) {
		Vehicle existingVehicle = vrepository.findById(vehicle.getVin());
		if (existingVehicle == null) {
			readingsList = rservice.getAllReadingsByVin(vehicle.getVin());
			vehicle.setReadings(readingsList);
			Vehicle createdVehicle = vrepository.create(vehicle);
			aservice.setAlerts(createdVehicle);
			try {
				gservice.setGeolocations(createdVehicle);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return createdVehicle;
		} else {
			return vrepository.update(vehicle);
		}
	}

	@Override
	@Transactional
	public List<Vehicle> getAllVehicles() {
		return vrepository.getAllVehicles();
	}

	@Override
	@Transactional
	public Vehicle findById(String vin) {
		Vehicle existingVehicle = vrepository.findById(vin);
		if (existingVehicle == null) {
			throw new NotFoundException("Vehicle with id: " + vin + " not found!"); // throw runtime exception 404
																					// vehicle not found to client
		}
		return existingVehicle;
	}
}
