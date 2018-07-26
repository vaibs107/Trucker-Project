package io.egen.trucker.repository;

import java.util.List;

import io.egen.trucker.entity.Reading;

public interface ReadingRepository {

	public Reading create(Reading reading);
	
	public List<Reading> getAllReadingsByVin(String vin);

}
