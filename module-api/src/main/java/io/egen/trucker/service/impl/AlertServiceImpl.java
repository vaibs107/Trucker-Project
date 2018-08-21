package io.egen.trucker.service.impl;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

	Alert alertObjectFound;

	@Autowired
	private AlertRepository arepository;

	@Autowired
	private VehicleService vservice;

	// Fetch HIGH alerts within last 2 hours for all the vehicles and ability to
	// sort list of vehicles based on it.
	@Override
	@Transactional
	public List<Alert> getAllHighAlerts() {
		List<String> alertsTimeList = arepository.getAlertsTimeList();
		List<Alert> highAlerts = new ArrayList<Alert>();
		alertsTimeList.forEach(alertTime -> {
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			LocalTime parsedAlertTime = LocalTime.parse(alertTime, formatter1);

			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalTime localTimeNow = LocalTime.parse(formatter2.format(LocalTime.now()), formatter2);

			Duration d = Duration.between(localTimeNow, parsedAlertTime);
			if (((double) d.getSeconds() / 3600) <= 2) {
				alertObjectFound = arepository.getHighAlert(alertTime);
				highAlerts.add(alertObjectFound);
			}
		});
		return highAlerts;
	}

	// Ability to list a vehicle's all historical alerts.
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

	// Create alerts with given priority when specific rules are triggered.
	public void createAlert(Reading reading, Vehicle vehicle) {
		String rule = "";
		String priority = "";
		String alertTimestamp = "";

		// rules for alerts
		// rule 1: "engineRpm > redlineRpm"
		if (reading.getEngineRpm() > vehicle.getRedlineRpm()) {
			rule = "engineRpm > redlineRpm";
			priority = "HIGH";
			alertTimestamp = reading.getTimestamp();
			arepository.setAlerts(vehicle.getVin(), rule, priority, alertTimestamp);
		}

		// rule 2: "fuelVolume < 10% of maxFuelVolume"
		if (reading.getFuelVolume() < (vehicle.getMaxFuelVolume() * 0.10)) {
			rule = "fuelVolume < 10% of maxFuelVolume";
			priority = "MEDIUM";
			alertTimestamp = reading.getTimestamp();
			arepository.setAlerts(vehicle.getVin(), rule, priority, alertTimestamp);
		}

		// rule 3: "tire pressure of any tire < 32psi || > 36psi"
		int fl = reading.getTires().getFrontLeft();
		int fr = reading.getTires().getFrontRight();
		int rl = reading.getTires().getRearLeft();
		int rr = reading.getTires().getRearRight();
		if ((fl < 32 || fr < 32 || rl < 32 || rr < 32) || (fl > 36 || fr > 36 || rl > 36 || rr > 36)) {
			rule = "tire pressure of any tire < 32psi || > 36psi";
			priority = "LOW";
			alertTimestamp = reading.getTimestamp();
			arepository.setAlerts(vehicle.getVin(), rule, priority, alertTimestamp);
		}

		// rule 4: "engineCoolantLow = true || checkEngineLightOn = true"
		if (reading.isEngineCoolantLow() || reading.isCheckEngineLightOn()) {
			rule = "engineCoolantLow = true || checkEngineLightOn = true";
			priority = "LOW";
			alertTimestamp = reading.getTimestamp();
			arepository.setAlerts(vehicle.getVin(), rule, priority, alertTimestamp);
		}
	}
}
