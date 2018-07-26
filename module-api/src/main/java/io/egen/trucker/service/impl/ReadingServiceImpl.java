package io.egen.trucker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.egen.trucker.entity.Reading;
import io.egen.trucker.repository.ReadingRepository;
import io.egen.trucker.service.ReadingService;
import io.egen.trucker.service.TireService;

@Service
public class ReadingServiceImpl implements ReadingService {

	@Autowired
	private ReadingRepository rrepository;

	@Autowired
	private TireService tservice;

	public ReadingServiceImpl(ReadingRepository rrepository, TireService tservice) {
		this.rrepository = rrepository;
		this.tservice = tservice;
	}

	@Override
	@Transactional
	public Reading create(Reading reading) {
		tservice.create(reading.getTires());
		return rrepository.create(reading);
	}

	@Override
	public List<Reading> getAllReadingsByVin(String vin) {
		return rrepository.getAllReadingsByVin(vin);
	}
}
