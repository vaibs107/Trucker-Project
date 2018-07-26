package io.egen.trucker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.egen.trucker.entity.Alert;
import io.egen.trucker.entity.Reading;
import io.egen.trucker.entity.Vehicle;
import io.egen.trucker.exception.NotFoundException;
import io.egen.trucker.repository.AlertRepository;
import io.egen.trucker.service.AlertService;
import io.egen.trucker.service.VehicleService;

@Service
public class AlertServiceImpl implements AlertService {

	@Autowired
	private AlertRepository arepository;

	@Autowired
	private VehicleService vservice;

	@Override
	@Transactional
	public List<Alert> getAllHighAlerts() {
		return arepository.getAllHighAlerts();
	}

	@Override
	public List<Alert> getAllAlertsByVin(String vin) {
		Vehicle existingVehicle = vservice.findById(vin);
		if (existingVehicle != null) {
			return arepository.getAllAlertsByVin(vin);
		} else {
			throw new NotFoundException("Vehicle with id: " + vin + " not found!");
		}
	}

	@Override
	public void setAlerts(Vehicle vehicle) {
		vehicle.getReadings().forEach(reading -> createAlert(reading, vehicle));
	}

	public void createAlert(Reading reading, Vehicle vehicle) {
		String rule = "";
		String priority = "";

		// rules for alerts
		// rule 1: "engineRpm > redlineRpm"
		if (reading.getEngineRpm() > vehicle.getRedlineRpm()) {
			rule = "engineRpm > redlineRpm";
			priority = "HIGH";
			arepository.setAlerts(vehicle.getVin(), rule, priority);
		}

		// rule 2: "fuelVolume < 10% of maxFuelVolume"
		if (reading.getFuelVolume() < (vehicle.getMaxFuelVolume() * 0.10)) {
			rule = "fuelVolume < 10% of maxFuelVolume";
			priority = "MEDIUM";
			arepository.setAlerts(vehicle.getVin(), rule, priority);
		}

		// rule 3: "tire pressure of any tire < 32psi || > 36psi"
		int fl = reading.getTires().getFrontLeft();
		int fr = reading.getTires().getFrontRight();
		int rl = reading.getTires().getRearLeft();
		int rr = reading.getTires().getRearRight();
		if ((fl < 32 || fr < 32 || rl < 32 || rr < 32) || (fl > 36 || fr > 36 || rl > 36 || rr > 36)) {
			rule = "tire pressure of any tire < 32psi || > 36psi";
			priority = "LOW";
			arepository.setAlerts(vehicle.getVin(), rule, priority);
		}

		// rule 4: "engineCoolantLow = true || checkEngineLightOn = true"
		if (reading.isEngineCoolantLow() || reading.isCheckEngineLightOn()) {
			rule = "engineCoolantLow = true || checkEngineLightOn = true";
			priority = "LOW";
			arepository.setAlerts(vehicle.getVin(), rule, priority);
		}
	}
}
