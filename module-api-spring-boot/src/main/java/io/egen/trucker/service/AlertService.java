package io.egen.trucker.service;

import java.util.List;

import io.egen.trucker.entity.Alert;
import io.egen.trucker.entity.Vehicle;

public interface AlertService {

	public List<Alert> getAllHighAlerts();

	public List<Alert> getAllAlertsByVin(String vin);

	public void setAlerts(Vehicle vehicle);
}
