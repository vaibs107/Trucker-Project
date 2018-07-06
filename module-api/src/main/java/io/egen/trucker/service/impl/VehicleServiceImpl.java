package io.egen.trucker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.egen.trucker.entity.Vehicle;
import io.egen.trucker.exception.NotFoundException;
import io.egen.trucker.repository.VehicleRepository;
import io.egen.trucker.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleRepository vrepository;

	public VehicleServiceImpl(VehicleRepository vrepository) {
		this.vrepository = vrepository;
	}

	@Override
	@Transactional
	public Vehicle create(Vehicle vehicle) {
		Vehicle existingVehicle = vrepository.findById(vehicle.getVin());
		if (existingVehicle == null) {
			return vrepository.create(vehicle);
		}
		return vrepository.update(vehicle);
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
