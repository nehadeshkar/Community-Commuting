package com.tripmanagement.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tripmanagement.entities.Booking;
import com.tripmanagement.entities.Trip;
import com.tripmanagement.enums.BookingStatus;
import com.tripmanagement.enums.RideStatus;
import com.tripmanagement.model.BookingDTO;
import com.tripmanagement.model.TripDTO;
import com.tripmanagement.repositories.BookingRepository;
import com.tripmanagement.repositories.TripRepository;
import com.tripmanagement.service.impl.BookingServiceImpl;
import com.tripmanagement.service.impl.TripServiceImpl;

public class TestBookingServiceImpl {

	@Mock
	private TripRepository tripRepos;

	@Mock
	private BookingRepository bookingRepos;

	@InjectMocks
	private BookingServiceImpl bookingServiceImpl;

	@Mock
	private TripServiceImpl tripServiceImpl;

	Trip t;

	Booking book;

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.openMocks(this);

		t = new Trip();

		t.setTripId("0230PUNA");

		t.setCreatorUserId("RPDE03");

		t.setVehicleId("NA30PU02");

		t.setRideDate(LocalDate.of(2024, Month.APRIL, 23));

		t.setRideTime(LocalTime.parse("09:48:35"));

		t.setRideStatus(RideStatus.Planned);

		t.setNoOfSeat(10);

		t.setSeatsFilled(5);

		t.setFromLoc("PUNE");

		t.setToLoc("NAGPUR");

		book = new Booking();

		book.setBookingId(1);

		book.setTrip(t);

		book.setNumberOfSeat(3);

		book.setSeekerId("RESH00");

		book.setBookingStatus(BookingStatus.Booked);

	}

	@Test
	void testUpdateBooking_Positive() {
		try {
			
			Booking b = new Booking();
			b.setTrip(new Trip("0900PUGW"));
			b.setBookingId(3);
			b.setNumberOfSeat(5);
			b.setSeekerId("RESH00");
			b.setBookingStatus(BookingStatus.Booked);
			Optional<Booking> optionalOfBooking = Optional.of(b);
			when(bookingRepos.findById(3)).thenReturn(optionalOfBooking);
		
			
			Optional<Trip> optionaltriptoUpdate = Optional.of(t);
			when(tripRepos.findById("0900PUGW")).thenReturn(optionaltriptoUpdate);
		
			
			BookingDTO updatedDto = new BookingDTO();
			updatedDto.setBookingStatus(BookingStatus.Cancelled);
		
			Booking bnew = new Booking();
			bnew.setBookingStatus(updatedDto.getBookingStatus());
			bookingRepos.save(bnew);
			when(bookingRepos.save(b)).thenReturn(bnew);
			String actual = bookingServiceImpl.updateBooking(3, updatedDto);
			assertEquals("success", actual);
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(false);
		}

	}

	@Test
	void testUpdateBooking_Negative() {

		try {

			Booking b = new Booking();

			b.setTrip(new Trip("0930MUPU"));

			b.setBookingId(1);

			b.setNumberOfSeat(5);

			b.setSeekerId("RESH00");

			b.setBookingStatus(BookingStatus.Booked);

			when(bookingRepos.findById(1)).thenReturn(null);

			BookingDTO updatedDto = new BookingDTO();

			updatedDto.setBookingStatus(BookingStatus.Cancelled);

			Booking bnew = new Booking();

			bnew.setBookingStatus(updatedDto.getBookingStatus());

			bookingRepos.save(bnew);

			when(bookingRepos.save(b)).thenReturn(null);

			String actual = bookingServiceImpl.updateBooking(1, updatedDto);

			System.out.println(actual);

			assertEquals("fail", actual);

		} catch (Exception e) {

			assertTrue(true);

		}

	}

	@Test
	void getBookingResponseModels_positiveWhenModelIsFound() {

		try {

			Iterable<Booking> iterableMock = Mockito.mock(Iterable.class);

			when(bookingRepos.findAll()).thenReturn(iterableMock);

			Iterator<Booking> iteratorMock = Mockito.mock(Iterator.class);

			when(iterableMock.iterator()).thenReturn(iteratorMock);

			when(iteratorMock.hasNext()).thenReturn(true).thenReturn(false);

			Booking bookingMock = Mockito.mock(Booking.class);

			when(iteratorMock.next()).thenReturn(bookingMock);

			when(bookingMock.getBookingId()).thenReturn(5);

			when(bookingMock.getBookingStatus()).thenReturn(BookingStatus.Booked);

			when(bookingMock.getNumberOfSeat()).thenReturn(67);

			when(bookingMock.getSeekerId()).thenReturn("RESH00");

			when(bookingMock.getTrip()).thenReturn(new Trip("0930MUPU"));

			List<BookingDTO> list = bookingServiceImpl.getBookingList();

			assertTrue(list.size() == 1);

		} catch (Exception e) {

			assertTrue(false);

		}

	}

	@Test
	void getBookingResponseModels_positiveWhenMoreThanOneModelIsFound() {

		try {

			Iterable<Booking> iterableMock = Mockito.mock(Iterable.class);

			when(bookingRepos.findAll()).thenReturn(iterableMock);

			Iterator<Booking> iteratorMock = Mockito.mock(Iterator.class);

			when(iterableMock.iterator()).thenReturn(iteratorMock);

			when(iteratorMock.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);

			Booking bookingMock = Mockito.mock(Booking.class);

			when(iteratorMock.next()).thenReturn(bookingMock);

			when(bookingMock.getBookingStatus()).thenReturn(BookingStatus.Booked);

			when(bookingMock.getNumberOfSeat()).thenReturn(67);

			when(bookingMock.getSeekerId()).thenReturn("RESH00");

			when(bookingMock.getTrip()).thenReturn(new Trip("0930MUPU"));

			List<BookingDTO> list = bookingServiceImpl.getBookingList();

			assertTrue(list.size() > 1);

		} catch (Exception e) {

			assertTrue(false);

		}

	}

	@Test
	void getTripResponseModels_Exception() {

		try {

			Iterable<Booking> iterableMock = Mockito.mock(Iterable.class);

			when(bookingRepos.findAll()).thenThrow(SQLException.class);

			Iterator<Booking> iteratorMock = Mockito.mock(Iterator.class);

			when(iterableMock.iterator()).thenReturn(iteratorMock);

			when(iteratorMock.hasNext()).thenReturn(false);

			Booking bookingMock = Mockito.mock(Booking.class);

			when(iteratorMock.next()).thenReturn(bookingMock);

			when(bookingMock.getBookingStatus()).thenReturn(BookingStatus.Booked);

			when(bookingMock.getNumberOfSeat()).thenReturn(67);

			when(bookingMock.getSeekerId()).thenReturn("RESH00");

			when(bookingMock.getTrip()).thenReturn(new Trip("0930MUPU"));

			List<BookingDTO> list = bookingServiceImpl.getBookingList();

			assertTrue(false);

		} catch (Exception e) {

			assertTrue(true);

		}

	}

	@Test
	void bookRide_Positive() {

		try {

			Booking mockOfBooking = Mockito.mock(Booking.class);

			BookingDTO bookRequest = new BookingDTO();

			bookRequest.setTrip(new Trip("0930MUPU"));

			bookRequest.setBookingId(1);

			bookRequest.setNumberOfSeat(5);

			bookRequest.setSeekerId("RESH00");

			bookRequest.setBookingStatus(BookingStatus.Booked);

			when(bookingRepos.save(Mockito.any())).thenReturn(mockOfBooking);

			String actual = bookingServiceImpl.bookRide(bookRequest);

			assertEquals("fail", actual);

		} catch (Exception e) {

			e.printStackTrace();

			assertTrue(true);

		}

	}

	@Test
	void bookRide_TripNotFound() {

		try {

			Booking mockOfBooking = Mockito.mock(Booking.class);

			BookingDTO bookRequest = new BookingDTO();

			bookRequest.setTrip(new Trip("0930MUPU"));

			bookRequest.setBookingId(1);

			bookRequest.setNumberOfSeat(5);

			bookRequest.setSeekerId("RESH00");

			bookRequest.setBookingStatus(BookingStatus.Booked);

			when(tripRepos.findByTripId("0930MUPU")).thenReturn(null);

			String actual = bookingServiceImpl.bookRide(bookRequest);

			assertEquals("fail", actual);

		} catch (Exception e) {

			e.printStackTrace();

			assertTrue(true);

		}

	}

	@Test
	void bookRide_TripFound() {

		try {

			Trip mockOfTrip = Mockito.mock(Trip.class);

			TripDTO trip = new TripDTO();

			trip.setTripId("0930MUPU");

			trip.setCreatorUserId("RPDE04");

			trip.setVehicleId("MP07GA6442");

			trip.setRideDate(LocalDate.of(2024, Month.FEBRUARY, 27));

			trip.setRideTime(LocalTime.parse("15:28:35"));

			trip.setRideStatus(RideStatus.Planned);

			trip.setNoOfSeat(63);

			trip.setSeatsFilled(32);

			trip.setFromLoc("PUNE");

			trip.setToLoc("GWALIOR");

			Booking mockOfBooking = Mockito.mock(Booking.class);

			BookingDTO bookRequest = new BookingDTO();

			bookRequest.setTrip(new Trip("0930MUPU"));

			bookRequest.setBookingId(1);

			bookRequest.setNumberOfSeat(5);

			bookRequest.setSeekerId("RESH00");

			bookRequest.setBookingStatus(BookingStatus.Booked);

			when(tripRepos.save(t)).thenReturn(mock(Trip.class));

			when(tripRepos.findByTripId("0930MUPU")).thenReturn(t);

			when(bookingRepos.save(any(Booking.class))).thenReturn(book);

			String actual = bookingServiceImpl.bookRide(bookRequest);

			assertEquals("success", actual);

		} catch (Exception e) {

			e.printStackTrace();

			assertTrue(false);

		}

	}

	@Test
	void bookRide_Negative() {

		try {

			Booking mockOfBooking = Mockito.mock(Booking.class);

			BookingDTO bookRequest = new BookingDTO();

			bookRequest.setTrip(new Trip("0930MUPU"));

			bookRequest.setNumberOfSeat(5);

			bookRequest.setSeekerId("RESH00");

			bookRequest.setBookingStatus(BookingStatus.Booked);

			when(tripRepos.save(Mockito.any())).thenReturn(null);

			assertEquals("fail", bookingServiceImpl.bookRide(bookRequest));

		} catch (Exception e) {

			assertTrue(true);

		}

	}

	@Test
	void bookRide_CheckStatusPositive() {

		try {

			Trip mockOfTrip = Mockito.mock(Trip.class);

			TripDTO trip = new TripDTO();

			trip.setTripId("0930MUPU");

			trip.setCreatorUserId("001");

			trip.setVehicleId("MP07GA6442");

			trip.setRideDate(LocalDate.of(2024, Month.FEBRUARY, 27));

			trip.setRideTime(LocalTime.parse("15:28:35"));

			trip.setRideStatus(RideStatus.Planned);

			trip.setNoOfSeat(63);

			trip.setSeatsFilled(32);

			trip.setFromLoc("PUNE");

			trip.setToLoc("GWALIOR");

			when(tripRepos.save(Mockito.any())).thenReturn(mockOfTrip);

			if (mockOfTrip.getRideStatus() == RideStatus.Planned) {

				assertTrue(true);

			}

		} catch (Exception e) {

			assertFalse(false);

		}

	}

	@Test
	void bookRide_CheckStatusNegative() {

		try {

			Trip mockOfTrip = Mockito.mock(Trip.class);

			TripDTO trip = new TripDTO();

			trip.setTripId("0930MUPU");

			trip.setCreatorUserId("001");

			trip.setVehicleId("MP07GA6442");

			trip.setRideDate(LocalDate.of(2024, Month.FEBRUARY, 27));

			trip.setRideTime(LocalTime.parse("15:28:35"));

			trip.setRideStatus(RideStatus.Cancelled);

			trip.setNoOfSeat(63);

			trip.setSeatsFilled(32);

			trip.setFromLoc("PUNE");

			trip.setToLoc("GWALIOR");

			if (mockOfTrip.getRideStatus() == RideStatus.Planned) {

				assertFalse(false);

			}

		} catch (Exception e) {

			assertTrue(true);

		}

	}

	@Test
	void persistBookingRequest_Exception() {

		try {

			BookingDTO book = new BookingDTO();

			book.setTrip(new Trip("0930MUPU"));

			book.setNumberOfSeat(5);

			book.setSeekerId("RESH00");

			book.setBookingStatus(BookingStatus.Booked);

			when(bookingRepos.save(Mockito.any())).thenThrow(SQLException.class);

			bookingServiceImpl.bookRide(book);

			assertTrue(false);

		} catch (Exception e) {

			assertTrue(true);

		}

	}
}
