package com.tripmanagement.testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import com.tripmanagement.entities.Trip;
import com.tripmanagement.enums.RideStatus;
import com.tripmanagement.repositories.TripRepository;
import com.tripmanagement.tm.TripmanagementApplication;

@DataJpaTest
@ContextConfiguration(classes = TripmanagementApplication.class)
public class TestTripRepository {
	
	@Autowired
	private TripRepository tripRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testFindAllPositive() {
		Trip t=new Trip();
		t.setTripId("1340BHNA");
		t.setCreatorUserId("RPGI05");
		t.setVehicleId("NA40BH13");
		t.setRideDate(LocalDate.now());
		t.setRideTime(LocalTime.now());
		t.setRideStatus(RideStatus.Completed);
		t.setFromLoc("BHOPAL");
		t.setToLoc("NAGPUR");
		t.setNoOfSeat(10);
		t.setSeatsFilled(9);
		entityManager.persist(t);
		Iterable<Trip> it=tripRepository.findAll();
		assertTrue(it.iterator().hasNext());
	}
	
	@Test
	public void testFindAllNegative() {
		Iterable<Trip> tripIterator=tripRepository.findAll();
		assertFalse(!tripIterator.iterator().hasNext());
	}
	
	
	@Test
	public void testFindByIdPositive() {
		Trip trip=new Trip();
		trip.setTripId("1340BHNA");
		trip.setCreatorUserId("RPGI05");
		trip.setVehicleId("NA40BH13");
		trip.setRideDate(LocalDate.now());
		trip.setRideTime(LocalTime.now());
		trip.setRideStatus(RideStatus.Started);
		trip.setFromLoc("BHOPAL");
		trip.setToLoc("NAGPUR");
		trip.setNoOfSeat(10);
		trip.setSeatsFilled(9);
		entityManager.persist(trip);
		Optional<Trip> optionalOfTrip=tripRepository.findById("1340BHNA");
		assertTrue(optionalOfTrip.isPresent());
	}
	
	@Test
	public void testFindByIdNegative() {
		Optional<Trip> employee=tripRepository.findById("1340BHNA");
		assertTrue(!employee.isPresent());
	}
	
	@Test
	public void testSavePositive() {
		Trip trip=new Trip();
		trip.setTripId("1340BHNA");
		trip.setCreatorUserId("RPGI05");
		trip.setVehicleId("NA40BH13");
		trip.setRideDate(LocalDate.now());
		trip.setRideTime(LocalTime.now());
		trip.setRideStatus(RideStatus.Started);
		trip.setFromLoc("BHOPAL");
		trip.setToLoc("NAGPUR");
		trip.setNoOfSeat(10);
		trip.setSeatsFilled(9);
		tripRepository.save(trip);
		Optional<Trip> optionalOfTrip=tripRepository.findById("1340BHNA");
		assertTrue(optionalOfTrip.isPresent());
	}
	@Test
	public void testDeletePositive() {
		Trip trip=new Trip();
		trip.setTripId("1340BHNA");
		trip.setCreatorUserId("RPGI05");
		trip.setVehicleId("NA40BH13");
		trip.setRideDate(LocalDate.now());
		trip.setRideTime(LocalTime.now());
		trip.setRideStatus(RideStatus.Completed);
		trip.setFromLoc("BHOPAL");
		trip.setToLoc("NAGPUR");
		trip.setNoOfSeat(10);
		trip.setSeatsFilled(9);
		entityManager.persist(trip);
		tripRepository.delete(trip);
		Optional<Trip> optionalOfTrip=tripRepository.findById("1340BHNA");
		assertTrue(!optionalOfTrip.isPresent());

	
}}
