package io.egen.trucker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.egen.trucker.constant.URI;
import io.egen.trucker.entity.Geolocation;
import io.egen.trucker.service.GeolocationService;

@RestController
@RequestMapping(value = URI.GEOLOCATIONS)
public class GeolocationController {

	@Autowired
	private GeolocationService gservice;

	@RequestMapping(method = RequestMethod.GET, value = URI.VIN)
	public List<Geolocation> getAllGeolocationsByVin(@PathVariable("vin") String vin) {
		return gservice.getAllGeolocationsByVin(vin);
	}
}
