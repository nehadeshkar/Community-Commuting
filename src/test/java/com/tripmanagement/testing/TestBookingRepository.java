package com.tripmanagement.testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import com.tripmanagement.entities.Booking;
import com.tripmanagement.entities.Trip;
import com.tripmanagement.enums.BookingStatus;
import com.tripmanagement.enums.RideStatus;
import com.tripmanagement.repositories.BookingRepository;
import com.tripmanagement.tm.TripmanagementApplication;

@DataJpaTest
@ContextConfiguration(classes = TripmanagementApplication.class)
class TestBookingRepository {
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testFindAllPositive() {
		Booking b = new Booking();
		b.setBookingId(1);
		b.setNumberOfSeat(10);
		b.setSeekerId("RESH03");
		b.setBookingStatus(BookingStatus.Completed);

		Trip e = new Trip();
		e.setTripId("1340BHNA");
		e.setCreatorUserId("RPGI05");
		e.setVehicleId("NA40BH13");
		e.setRideDate(LocalDate.now());
		e.setRideTime(LocalTime.now());
		e.setRideStatus(RideStatus.Completed);
		e.setFromLoc("BHOPAL");
		e.setToLoc("NAGPUR");
		e.setNoOfSeat(10);
		e.setSeatsFilled(9);
		entityManager.persist(e);

		b.setTrip(e);

		bookingRepository.save(b);
		Iterable<Booking> booking = bookingRepository.findAll();
		assertTrue(booking.iterator().hasNext());
	}

	@Test
	public void testSavePositive() {

		Booking b = new Booking();
		b.setBookingId(1);
		b.setNumberOfSeat(10);
		b.setSeekerId("RESH03");
		b.setBookingStatus(BookingStatus.Completed);

		Trip e = new Trip();
		e.setTripId("1340BHNA");
		e.setCreatorUserId("RPGI05");
		e.setVehicleId("NA40BH13");
		e.setRideDate(LocalDate.now());
		e.setRideTime(LocalTime.now());
		e.setRideStatus(RideStatus.Completed);
		e.setFromLoc("BHOPAL");
		e.setToLoc("NAGPUR");
		e.setNoOfSeat(10);
		e.setSeatsFilled(9);
		entityManager.persist(e);

		b.setTrip(e);

		bookingRepository.save(b);
		Optional<Booking> booking = bookingRepository.findById(1);
		assertTrue(booking.isPresent());
	}

	@Test
	public void testFindByIdPositive() {
		Booking b = new Booking();
		b.setBookingId(2);
		b.setNumberOfSeat(10);
		b.setSeekerId("RESH04");
		b.setBookingStatus(BookingStatus.Cancelled);

		Trip e = new Trip();
		e.setTripId("1340BHNA");
		e.setCreatorUserId("RPGI05");
		e.setVehicleId("NA40BH13");
		e.setRideDate(LocalDate.now());
		e.setRideTime(LocalTime.now());
		e.setRideStatus(RideStatus.Started);
		e.setFromLoc("BHOPAL");
		e.setToLoc("NAGPUR");
		e.setNoOfSeat(5);
		e.setSeatsFilled(1);
		entityManager.persist(e);
		b.setTrip(e);
		bookingRepository.save(b);
		Optional<Booking> employee = bookingRepository.findById(2);
		assertTrue(employee.isPresent());
	}

	@Test
	public void testFindByIdNegative() {
		Optional<Booking> employee = bookingRepository.findById(12);
		assertTrue(!employee.isPresent());
	}

	@Test
	public void testDeletePositive() {
		Booking b = new Booking();
		b.setBookingId(3);
		b.setNumberOfSeat(10);
		b.setSeekerId("RESH04");
		b.setBookingStatus(BookingStatus.Completed);

		Trip e = new Trip();
		e.setTripId("1230NABH");
		e.setCreatorUserId("RPDA90");
		e.setVehicleId("NA30BH12");
		e.setRideDate(LocalDate.now());
		e.setRideTime(LocalTime.now());
		e.setRideStatus(RideStatus.Planned);
		e.setFromLoc("NAGPUR");
		e.setToLoc("BHOPAL");
		e.setNoOfSeat(15);
		e.setSeatsFilled(9);

		// passing object of trip
		b.setTrip(e);
		entityManager.persist(e);
		bookingRepository.delete(b);
		Optional<Booking> employee = bookingRepository.findById(3);
		assertTrue(!employee.isPresent());

	}
}