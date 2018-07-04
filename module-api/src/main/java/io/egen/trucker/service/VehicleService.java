package io.egen.trucker.service;

import java.util.List;

import io.egen.trucker.entity.Vehicle;

public interface VehicleService {

	public Vehicle create(Vehicle vehicle);

	public List<Vehicle> getAllVehicles();

	public Vehicle findById(String vin);

	public Vehicle update(String vin, Vehicle vehicle);
}
