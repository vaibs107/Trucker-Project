package io.egen.trucker.repository;

import java.util.List;

import io.egen.trucker.entity.Vehicle;

public interface VehicleRepository {

	public Vehicle create(Vehicle vehicle);

	public List<Vehicle> getAllVehicles();

	public Vehicle findById(String vin);

	public Vehicle update(String vin, Vehicle vehicle);
}
