package io.egen.trucker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.egen.trucker.constant.URI;
import io.egen.trucker.entity.Vehicle;
import io.egen.trucker.service.VehicleService;

@RestController
@RequestMapping(value = URI.VEHICLES)
public class VehicleController {

	@Autowired
	private VehicleService vservice;

	public VehicleController(VehicleService vservice) {
		this.vservice = vservice;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Vehicle create(@RequestBody Vehicle vehicle) {
		return vservice.create(vehicle);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Vehicle> getAllVehicles() {
		return vservice.getAllVehicles();
	}

	@RequestMapping(method = RequestMethod.GET, value = URI.VIN)
	public Vehicle findById(@PathVariable("vin") String vin) {
		return vservice.findById(vin);
	}

	@RequestMapping(method = RequestMethod.PUT, value = URI.VIN)
	public Vehicle update(@PathVariable("vin") String vin, @RequestBody Vehicle vehicle) {
		return vservice.update(vin, vehicle);
	}
}
