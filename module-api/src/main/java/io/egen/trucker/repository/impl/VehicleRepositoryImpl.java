package io.egen.trucker.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.egen.trucker.entity.Vehicle;
import io.egen.trucker.repository.VehicleRepository;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

	@Override
	public Vehicle create(Vehicle vehicle) {
		return null;
	}

	@Override
	public List<Vehicle> getAllVehicles() {
		return null;
	}

	@Override
	public Vehicle findById(String vin) {
		return null;
	}

	@Override
	public Vehicle update(String vin, Vehicle vehicle) {
		return null;
	}
}
