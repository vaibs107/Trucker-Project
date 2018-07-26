package io.egen.trucker.service;

import java.util.List;

import io.egen.trucker.entity.Reading;

public interface ReadingService {

	public Reading create(Reading reading);

	public List<Reading> getAllReadingsByVin(String vin);
}
