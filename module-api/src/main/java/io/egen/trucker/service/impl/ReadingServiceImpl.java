package io.egen.trucker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.egen.trucker.entity.Reading;
import io.egen.trucker.repository.ReadingRepository;
import io.egen.trucker.service.ReadingService;

@Service
public class ReadingServiceImpl implements ReadingService{

	@Autowired
	private ReadingRepository rrepository;
	
	public ReadingServiceImpl(ReadingRepository rrepository) {
		this.rrepository = rrepository;
	}
	
	@Override
	public Reading create(Reading reading) {
		return rrepository.create(reading);
	}

}
