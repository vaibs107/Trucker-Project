package io.egen.trucker.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

		System.out.println("Inside AlertServiceImpl\n");

		Optional<List<String>> alertsTimeList = arepository.findAllByAlertTimestamp();
		List<Alert> highAlerts = new ArrayList<Alert>();

		System.out.println("\nIn AlertServiceImpl, list of alert timestamps from DB:\n" + alertsTimeList);

		alertsTimeList.get().forEach(alertTime -> {

			System.out.println("\n\nIn AlertServiceImpl, alertTime = " + alertTime);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			LocalDateTime parsedDateTimeString = LocalDateTime.parse(alertTime, formatter);

			// System.out.println("parsedDateTimeString: " + parsedDateTimeString +
			// "\nClass: " + parsedDateTimeString.getClass());

			LocalDateTime localDateTimeNow = LocalDateTime.now();

			// System.out.println("localDateTimeNow: " + localDateTimeNow + "\nClass: " +
			// localDateTimeNow.getClass());

			Duration d = Duration.between(parsedDateTimeString, localDateTimeNow);

			System.out.println("Duretion d: " + d.getSeconds() + " seconds.");
			// System.out.println("Duration d: " + (double) (d.getSeconds() / 3600) + "
			// hours.");
			// System.out.println(((double) d.getSeconds() / 3600) <= 2.0);

			if (d.getSeconds() <= 7200) {

				alertObjectFound = arepository.findFirstByAlertTimestampAndPriority(alertTime, "HIGH");

				System.out.println("\n\nalertObjectFound = " + alertObjectFound);

				if (!highAlerts.contains(alertObjectFound))
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
			Optional<List<Alert>> alertList = arepository.findByVin(vin);
			if (alertList.get().isEmpty())
				return null;
			else
				return alertList.get();
		} else {
			throw new NotFoundException("Vehicle with id: " + vin + " not found!");
		}
	}

	@Override
	public void setAlerts(Vehicle vehicle) {
		int i = 0;
		System.out.println("\n\nIn AlertServiceImpl, inside setAlerts, vehicle = " + vehicle.getReadings().size());
		System.out.println("Reading : " + i++ + " \n");
		vehicle.getReadings().forEach(reading -> createAlert(reading, vehicle));
		System.out.println("End of reading\n");
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

			Alert alert = new Alert();
			alert.setVin(vehicle.getVin());
			alert.setRule(rule);
			alert.setPriority(priority);
			alert.setAlertTimestamp(alertTimestamp);

			arepository.save(alert);
		}

		// rule 2: "fuelVolume < 10% of maxFuelVolume"
		if (reading.getFuelVolume() < (vehicle.getMaxFuelVolume() * 0.10)) {
			rule = "fuelVolume < 10% of maxFuelVolume";
			priority = "MEDIUM";
			alertTimestamp = reading.getTimestamp();

			Alert alert = new Alert();
			alert.setVin(vehicle.getVin());
			alert.setRule(rule);
			alert.setPriority(priority);
			alert.setAlertTimestamp(alertTimestamp);

			arepository.save(alert);
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

			Alert alert = new Alert();
			alert.setVin(vehicle.getVin());
			alert.setRule(rule);
			alert.setPriority(priority);
			alert.setAlertTimestamp(alertTimestamp);

			arepository.save(alert);
		}

		// rule 4: "engineCoolantLow = true || checkEngineLightOn = true"
		if (reading.isEngineCoolantLow() || reading.isCheckEngineLightOn()) {
			rule = "engineCoolantLow = true || checkEngineLightOn = true";
			priority = "LOW";
			alertTimestamp = reading.getTimestamp();

			Alert alert = new Alert();
			alert.setVin(vehicle.getVin());
			alert.setRule(rule);
			alert.setPriority(priority);
			alert.setAlertTimestamp(alertTimestamp);

			arepository.save(alert);
		}
	}
}
