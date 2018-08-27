package io.egen.trucker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.egen.trucker.entity.Alert;

public interface AlertRepository extends CrudRepository<Alert, String> {

	Optional<List<Alert>> findByVin(String vin);

	Alert findFirstByAlertTimestampAndPriority(String alertTimestamp, String priority);

	//Optional<List<String>> findAlertTimestamp();
	
	@Query("SELECT a.alertTimestamp FROM Alert a")
	Optional<List<String>> findAllByAlertTimestamp();

}
