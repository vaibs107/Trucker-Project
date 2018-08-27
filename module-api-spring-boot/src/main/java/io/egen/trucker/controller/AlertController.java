package io.egen.trucker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.egen.trucker.constant.URI;
import io.egen.trucker.entity.Alert;
import io.egen.trucker.service.AlertService;

@RestController
@RequestMapping(value = URI.ALERTS)
public class AlertController {

	@Autowired
	private AlertService aservice;

	@RequestMapping(method = RequestMethod.GET)
	public List<Alert> getAllHighAlerts() {
		return aservice.getAllHighAlerts();
	}

	@RequestMapping(method = RequestMethod.GET, value = URI.VIN)
	public List<Alert> getAllAlertsByVin(@PathVariable("vin") String vin) {
		return aservice.getAllAlertsByVin(vin);
	}
}
