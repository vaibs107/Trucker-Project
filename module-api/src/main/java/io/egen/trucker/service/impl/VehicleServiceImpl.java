package io.egen.trucker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.egen.trucker.entity.Vehicle;
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
	public Vehicle create(Vehicle vehicle) {
		return vrepository.create(vehicle);
	}

	@Override
	public List<Vehicle> getAllVehicles() {
		return vrepository.getAllVehicles();
	}

	@Override
	public Vehicle findById(String vin) {
		Vehicle existingVehicle = vrepository.findById(vin);
		if (existingVehicle == null) {
			return null;
		} else {
			return existingVehicle;
		}
	}

	@Override
	public Vehicle update(String vin, Vehicle vehicle) {
		Vehicle existingVehicle = findById(vin);
		if (existingVehicle == null) {
			return create(vehicle);
		} else {
			return vrepository.update(vin, existingVehicle);
		}
	}
}
