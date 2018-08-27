package io.egen.trucker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.egen.trucker.entity.Reading;

public interface ReadingRepository extends CrudRepository<Reading, String> {

	public Optional<List<Reading>> findAllByVin(String vin);

}
