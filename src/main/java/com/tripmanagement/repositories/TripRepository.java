package com.tripmanagement.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tripmanagement.entities.Trip;

@Repository
public interface TripRepository extends CrudRepository<Trip, String> {

	Trip findByTripId(String tripId);

}
