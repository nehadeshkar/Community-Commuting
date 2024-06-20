package com.tripmanagement.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tripmanagement.entities.Booking;
import com.tripmanagement.entities.Trip;

@Repository
public interface BookingRepository extends CrudRepository <Booking, Integer> {

	List<Booking> findByTrip(Trip trip);

}
