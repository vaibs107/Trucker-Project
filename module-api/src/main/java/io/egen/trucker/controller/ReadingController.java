package io.egen.trucker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.egen.trucker.constant.URI;
import io.egen.trucker.entity.Reading;
import io.egen.trucker.service.ReadingService;

@CrossOrigin
@RestController
@RequestMapping(value = URI.READINGS)
public class ReadingController {

	@Autowired
	private ReadingService rservice;

	public ReadingController(ReadingService rservice) {
		this.rservice = rservice;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Reading create(@RequestBody Reading reading) {
		return rservice.create(reading);
	}
}
