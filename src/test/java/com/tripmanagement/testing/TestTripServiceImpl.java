package com.tripmanagement.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
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
import com.tripmanagement.model.TripDTO;
import com.tripmanagement.repositories.BookingRepository;
import com.tripmanagement.repositories.TripRepository;
import com.tripmanagement.service.impl.TripServiceImpl;

class TestTripServiceImpl {
	@Mock
	private TripRepository tripRepo;
	
	@Mock 
	private BookingRepository bookingRepo;

	@InjectMocks
	private TripServiceImpl tripServiceImpl;
	
	@BeforeEach
	void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
	}
	
	@AfterEach
	void tearDown() throws Exception{
		
	}
	
	@Test
	void testUpdateTrip () {
			Trip t = new Trip();
			
			t.setTripId("1211NAGW");
			t.setCreatorUserId("123N");
			t.setVehicleId("MH31DP9136");
			t.setRideDate(LocalDate.of(2024, Month.MARCH, 12));
			t.setRideTime(LocalTime.parse("12:11:00"));
			t.setRideStatus(RideStatus.Planned);
			t.setNoOfSeat(20);
			t.setSeatsFilled(5);
			t.setFromLoc("Nagpur");
			t.setToLoc("Gwalior");
			
			Optional<Trip> optionalOfTrip = Optional.of(t);
			when(tripRepo.findById("1211NAGW")).thenReturn(optionalOfTrip);
			
			TripDTO tripDto = new TripDTO();
			tripDto.setRideStatus(RideStatus.Cancelled);
			
			Trip tripObj = new Trip();
			tripObj.setRideStatus(tripDto.getRideStatus());
			tripRepo.save(tripObj);
			when(tripRepo.save(t)).thenReturn(tripObj);
			
			String actual = tripServiceImpl.updateTrip("1211NAGW", tripDto);
			assertEquals("success", actual);
			
	}
	
	@Test
	void testUpdateTripRequestStatus_Negative() {
		try {
			Trip t = new Trip();
			
			t.setTripId("8900PUGW");
			t.setCreatorUserId("123N");
			t.setVehicleId("MP07GA6442");
			t.setRideDate(LocalDate.of(2024, Month.FEBRUARY, 27));
			t.setRideTime(LocalTime.parse("15:28:35"));
			t.setRideStatus(RideStatus.Planned);
			t.setNoOfSeat(63);
			t.setSeatsFilled(32);
			t.setFromLoc("PUNE");
			t.setToLoc("GWALIOR");
			Optional<Trip> optionalOfTrip = Optional.of(t);
			when(tripRepo.findById("8900PUGW")).thenReturn(optionalOfTrip);

			TripDTO updatedDto = new TripDTO();
			updatedDto.setRideStatus(RideStatus.Cancelled);

			Trip tnew = new Trip();
			tnew.setRideStatus(updatedDto.getRideStatus());
			tripRepo.save(tnew);
			when(tripRepo.save(t)).thenReturn(null);

			String actual = tripServiceImpl.updateTrip("8900PUGW", updatedDto);
			assertEquals("fail", actual);
			System.out.println(actual);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	void testUpdateTripNotFound() {
		try {
			Trip t = new Trip();
			
			t.setTripId("8900PUGW");
			t.setCreatorUserId("123N");
			t.setVehicleId("MP07GA6442");
			t.setRideDate(LocalDate.of(2024, Month.FEBRUARY, 27));
			t.setRideTime(LocalTime.parse("15:28:35"));
			t.setRideStatus(RideStatus.Planned);
			t.setNoOfSeat(63);
			t.setSeatsFilled(32);
			t.setFromLoc("PUNE");
			t.setToLoc("GWALIOR");
			Optional<Trip> optionalOfTrip = Optional.empty();
			when(tripRepo.findById("8900PUGW")).thenReturn(optionalOfTrip);

			TripDTO updatedDto = new TripDTO();
			updatedDto.setRideStatus(RideStatus.Cancelled);

			Trip tnew = new Trip();
			tnew.setRideStatus(updatedDto.getRideStatus());
			tripRepo.save(tnew);
			when(tripRepo.save(t)).thenReturn(tnew);
			
			String actual = tripServiceImpl.updateTrip("8900PUGW", updatedDto);
			assertEquals("fail", actual);
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(false);
		}

	}
	@Test
	void testUpdateTripToBeCancelled() {
		try {
			Trip t = new Trip();
			
			t.setTripId("8900PUGW");
			t.setCreatorUserId("123N");
			t.setVehicleId("MP07GA6442");
			t.setRideDate(LocalDate.of(2024, Month.FEBRUARY, 27));
			t.setRideTime(LocalTime.parse("15:28:35"));
			t.setRideStatus(RideStatus.Planned);
			t.setNoOfSeat(63);
			t.setSeatsFilled(32);
			t.setFromLoc("PUNE");
			t.setToLoc("GWALIOR");
			Optional<Trip> optionalOfTrip = Optional.of(t);
			when(tripRepo.findById("8900PUGW")).thenReturn(optionalOfTrip);
			

			Booking b = new Booking();
			b.setTrip(new Trip("0900PUGW"));
			b.setBookingId(3);
			b.setNumberOfSeat(5);
			b.setSeekerId("RESH00");
			b.setBookingStatus(BookingStatus.Booked);

			List<Booking>booktoupdate=new ArrayList<Booking>();
			booktoupdate.add(b);
			when(bookingRepo.findByTrip(t)).thenReturn(booktoupdate);

			TripDTO updatedDto = new TripDTO();
			updatedDto.setRideStatus(RideStatus.Cancelled);

			Trip tnew = new Trip();
			tnew.setRideStatus(updatedDto.getRideStatus());
			tripRepo.save(tnew);
			when(tripRepo.save(t)).thenReturn(tnew);
			
			String actual = tripServiceImpl.updateTrip("8900PUGW", updatedDto);
			System.out.println(actual);
			assertEquals("success", actual);
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(false);
		}

	}
	
	@Test
	void testUpdateTripToBeCompleted() {
		try {
			Trip t = new Trip();
			
			t.setTripId("8900PUGW");
			t.setCreatorUserId("1223A");
			t.setVehicleId("MP07GA6442");
			t.setRideDate(LocalDate.of(2024, Month.FEBRUARY, 27));
			t.setRideTime(LocalTime.parse("15:28:35"));
			t.setRideStatus(RideStatus.Planned);
			t.setNoOfSeat(63);
			t.setSeatsFilled(32);
			t.setFromLoc("PUNE");
			t.setToLoc("GWALIOR");
			Optional<Trip> optionalOfTrip = Optional.of(t);
			when(tripRepo.findById("8900PUGW")).thenReturn(optionalOfTrip);

			TripDTO updatedDto = new TripDTO();
			updatedDto.setRideStatus(RideStatus.Completed);
			

			Booking b = new Booking();
			b.setTrip(new Trip("0900PUGW"));
			b.setBookingId(3);
			b.setNumberOfSeat(5);
			b.setSeekerId("RESH00");
			b.setBookingStatus(BookingStatus.Booked);

			List<Booking>booktoupdate=new ArrayList<Booking>();
			booktoupdate.add(b);
			when(bookingRepo.findByTrip(t)).thenReturn(booktoupdate);
			
			Trip tnew = new Trip();
			tnew.setRideStatus(updatedDto.getRideStatus());
			tripRepo.save(tnew);
			when(tripRepo.save(t)).thenReturn(tnew);
			
			String actual = tripServiceImpl.updateTrip("8900PUGW", updatedDto);
			
			assertEquals("success", actual);
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(false);
		}

	}
	@Test
	void testUpdateTripToBeStarted() {
		try {
			Trip t = new Trip();
			
			t.setTripId("8900PUGW");
			t.setCreatorUserId("001L");
			t.setVehicleId("MP07GA6442");
			t.setRideDate(LocalDate.of(2024, Month.FEBRUARY, 27));
			t.setRideTime(LocalTime.parse("15:28:35"));
			t.setRideStatus(RideStatus.Planned);
			t.setNoOfSeat(63);
			t.setSeatsFilled(32);
			t.setFromLoc("PUNE");
			t.setToLoc("GWALIOR");
			Optional<Trip> optionalOfTrip = Optional.of(t);
			when(tripRepo.findById("8900PUGW")).thenReturn(optionalOfTrip);
			
			Booking b = new Booking();
			b.setTrip(new Trip("0900PUGW"));
			b.setBookingId(5);
			b.setNumberOfSeat(5);
			b.setSeekerId("RESH00");
			b.setBookingStatus(BookingStatus.Booked);

			List<Booking>booktoupdate=new ArrayList<Booking>();
			booktoupdate.add(b);
			when(bookingRepo.findByTrip(t)).thenReturn(booktoupdate);

			TripDTO updatedDto = new TripDTO();
			updatedDto.setRideStatus(RideStatus.Started);

			Trip tnew = new Trip();
			tnew.setRideStatus(updatedDto.getRideStatus());
			tripRepo.save(tnew);
			when(tripRepo.save(t)).thenReturn(tnew);
			
			String actual = tripServiceImpl.updateTrip("8900PUGW", updatedDto);
			System.out.println(actual);
			assertEquals("success", actual);
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(false);
		}

	}
	
	@Test
	void getTripList_positiveWhenListIsFound() {
		try {
			Iterable<Trip> iterableMock = Mockito.mock(Iterable.class);
			when(tripRepo.findAll()).thenReturn(iterableMock);
			Iterator<Trip> iteratorMock = Mockito.mock(Iterator.class);
			when(iterableMock.iterator()).thenReturn(iteratorMock);
			when(iteratorMock.hasNext()).thenReturn(true).thenReturn(false);
			Trip tripMock = Mockito.mock(Trip.class);
			when(iteratorMock.next()).thenReturn(tripMock);
			when(tripMock.getTripId()).thenReturn("0900PUGW");
			when(tripMock.getCreatorUserId()).thenReturn("123w");
			when(tripMock.getFromLoc()).thenReturn("Pune");
			when(tripMock.getToLoc()).thenReturn("Gwalior");
			when(tripMock.getNoOfSeat()).thenReturn(45);
			when(tripMock.getSeatsFilled()).thenReturn(4);
			when(tripMock.getRideDate()).thenReturn(LocalDate.of(2024, Month.FEBRUARY, 27));
			when(tripMock.getRideStatus()).thenReturn(RideStatus.Planned);
			when(tripMock.getRideTime()).thenReturn(LocalTime.parse("15:28:35"));
			when(tripMock.getVehicleId()).thenReturn("MP07GA6442");

			List<TripDTO> list = tripServiceImpl.getTripList();
			assertTrue(list.size() == 1);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	void getTripList_positiveWhenMoreThanOneIsFound() {
		try {
			Iterable<Trip> iterableMock = Mockito.mock(Iterable.class);
			when(tripRepo.findAll()).thenReturn(iterableMock);
			Iterator<Trip> iteratorMock = Mockito.mock(Iterator.class);
			when(iterableMock.iterator()).thenReturn(iteratorMock);
			when(iteratorMock.hasNext()).thenReturn(true).thenReturn(false);
			Trip tripMock = Mockito.mock(Trip.class);
			when(iteratorMock.next()).thenReturn(tripMock);
			when(tripMock.getTripId()).thenReturn("0900PUGW");
			when(tripMock.getCreatorUserId()).thenReturn("123w");
			when(tripMock.getFromLoc()).thenReturn("Pune");
			when(tripMock.getToLoc()).thenReturn("Gwalior");
			when(tripMock.getNoOfSeat()).thenReturn(45);
			when(tripMock.getSeatsFilled()).thenReturn(4);
			when(tripMock.getRideDate()).thenReturn(LocalDate.of(2024, Month.FEBRUARY, 27));
			when(tripMock.getRideStatus()).thenReturn(RideStatus.Planned);
			when(tripMock.getRideTime()).thenReturn(LocalTime.parse("15:28:35"));
			when(tripMock.getVehicleId()).thenReturn("MP07GA6442");
			
			List<TripDTO> list = tripServiceImpl.getTripList();
			assertTrue(list.size() > 1);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
		@Test
		void getTripList_Exception() {
			try {
			Iterable<Trip> iterableMock=Mockito.mock(Iterable.class);
			when(tripRepo.findAll()).thenThrow(SQLException.class);
			Iterator<Trip> iteratorMock=Mockito.mock(Iterator.class);
			when(iterableMock.iterator()).thenReturn(iteratorMock);
			when(iteratorMock.hasNext()).thenReturn(false);
			Trip tripMock=Mockito.mock(Trip.class);
			when(iteratorMock.next()).thenReturn(tripMock);
			when(tripMock.getTripId()).thenReturn("0900PUGW");
			when(tripMock.getCreatorUserId()).thenReturn("123N");
			when(tripMock.getFromLoc()).thenReturn("Pune");
			when(tripMock.getToLoc()).thenReturn("Gwalior");
			when(tripMock.getNoOfSeat()).thenReturn(45);
			when(tripMock.getSeatsFilled()).thenReturn(4);
			when(tripMock.getRideDate()).thenReturn(LocalDate.of(2024,Month.FEBRUARY,27));
			when(tripMock.getRideStatus()).thenReturn(RideStatus.Planned);
			when(tripMock.getRideTime()).thenReturn(LocalTime.parse("15:28:35"));
			when(tripMock.getVehicleId()).thenReturn("MP07GA6442");		
			List<TripDTO> list=tripServiceImpl.getTripList();
			assertTrue(false);
			}catch(Exception e) {
				assertTrue(true);
			}
		}
		
		@Test
		void insertNewRide_Positive() {
			try {
				Trip mockOfTrip = Mockito.mock(Trip.class);
				TripDTO tripRequest = new TripDTO();
//				tripRequest.setTripId("0900PUGW");
				tripRequest.setCreatorUserId("RSH00");
				tripRequest.setVehicleId("MP07GA6442");
				tripRequest.setRideDate(LocalDate.of(2025, Month.FEBRUARY, 27));
				tripRequest.setRideTime(LocalTime.parse("15:28:35"));
				tripRequest.setRideStatus(RideStatus.Planned);
				tripRequest.setNoOfSeat(63);
				tripRequest.setSeatsFilled(32);
				tripRequest.setFromLoc("PUNE");
				tripRequest.setToLoc("GWALIOR");
				DateTimeFormatter Hoursformatter = DateTimeFormatter.ofPattern("HH");
				String hours = tripRequest.getRideTime().format(Hoursformatter);
				DateTimeFormatter Minutesformatter = DateTimeFormatter.ofPattern("mm");
				String minutes = tripRequest.getRideTime().format(Minutesformatter);
				String ftcOfDest = tripRequest.getToLoc().substring(0, 2);
				String ftcOfSour = tripRequest.getFromLoc().substring(0, 2);
				String tripId = hours + minutes + ftcOfSour + ftcOfDest;
				tripRequest.setTripId(tripId);
				when(tripRepo.save(Mockito.any())).thenReturn(mockOfTrip);
				String actual = tripServiceImpl.insertNewRide(tripRequest);
				assertEquals("success", actual);
			} catch (Exception e) {
				e.printStackTrace();
				assertTrue(true);
			}

		}
		@Test
		void insertNewRide_Negative() {
			try {
				Trip mockOfTrip = Mockito.mock(Trip.class);
				TripDTO tripRequest = new TripDTO();
//				tripRequest.setTripId("234");
				tripRequest.setCreatorUserId("RSH00");
				tripRequest.setVehicleId("MP07GA6442");
				tripRequest.setRideDate(LocalDate.of(2025, Month.FEBRUARY, 27));
				tripRequest.setRideTime(LocalTime.parse("15:28:35"));
				tripRequest.setRideStatus(RideStatus.Planned);
				tripRequest.setNoOfSeat(63);
				tripRequest.setSeatsFilled(32);
				tripRequest.setFromLoc("PUNE");
				tripRequest.setToLoc("GWALIOR");
				DateTimeFormatter Hoursformatter = DateTimeFormatter.ofPattern("HH");
				String hours = tripRequest.getRideTime().format(Hoursformatter);
				DateTimeFormatter Minutesformatter = DateTimeFormatter.ofPattern("mm");
				String minutes = tripRequest.getRideTime().format(Minutesformatter);
				String ftcOfDest = tripRequest.getToLoc().substring(0, 2);
				String ftcOfSour = tripRequest.getFromLoc().substring(0, 2);
				String tripId = hours + minutes + ftcOfSour + ftcOfDest;
				tripRequest.setTripId(tripId);
				when(tripRepo.save(Mockito.any())).thenReturn(null);

				assertEquals("fail", tripServiceImpl.insertNewRide(tripRequest));
			
			} catch (Exception e) {

				assertTrue(true);
			}
		}
		
		@Test
		void insertNewRide_LocIsSame() {
			try {
				Trip mockOfTrip = Mockito.mock(Trip.class);
				TripDTO tripRequest = new TripDTO();
//				tripRequest.setTripId("0900PUGW");
				tripRequest.setCreatorUserId("RSH00");
				tripRequest.setVehicleId("MP07GA6442");
				tripRequest.setRideDate(LocalDate.of(2025, Month.FEBRUARY, 27));
				tripRequest.setRideTime(LocalTime.parse("15:28:35"));
				tripRequest.setRideStatus(RideStatus.Planned);
				tripRequest.setNoOfSeat(63);
				tripRequest.setSeatsFilled(32);
				tripRequest.setFromLoc("PUNE");
				tripRequest.setToLoc("PUNE");
				DateTimeFormatter Hoursformatter = DateTimeFormatter.ofPattern("HH");
				String hours = tripRequest.getRideTime().format(Hoursformatter);
				DateTimeFormatter Minutesformatter = DateTimeFormatter.ofPattern("mm");
				String minutes = tripRequest.getRideTime().format(Minutesformatter);
				String ftcOfDest = tripRequest.getToLoc().substring(0, 2);
				String ftcOfSour = tripRequest.getFromLoc().substring(0, 2);
				String tripId = hours + minutes + ftcOfSour + ftcOfDest;
				tripRequest.setTripId(tripId);
				when(tripRepo.save(Mockito.any())).thenReturn(mockOfTrip);
				String actual = tripServiceImpl.insertNewRide(tripRequest);
				assertEquals("success", actual);
			} catch (Exception e) {
				e.printStackTrace();
				assertTrue(true);
			}

		}
		
		@Test
		void insertNewRide_DateIsPast() {
			try {
				Trip mockOfTrip = Mockito.mock(Trip.class);
				TripDTO tripRequest = new TripDTO();
//				tripRequest.setTripId("0900PUGW");
				tripRequest.setCreatorUserId("RSH00");
				tripRequest.setVehicleId("MP07GA6442");
				tripRequest.setRideDate(LocalDate.of(2024, Month.APRIL, 11));
				tripRequest.setRideTime(LocalTime.parse("09:11:00"));
				tripRequest.setRideStatus(RideStatus.Planned);
				tripRequest.setNoOfSeat(63);
				tripRequest.setSeatsFilled(32);
				tripRequest.setFromLoc("PUNE");
				tripRequest.setToLoc("GWALIOR");
				DateTimeFormatter Hoursformatter = DateTimeFormatter.ofPattern("HH");
				String hours = tripRequest.getRideTime().format(Hoursformatter);
				DateTimeFormatter Minutesformatter = DateTimeFormatter.ofPattern("mm");
				String minutes = tripRequest.getRideTime().format(Minutesformatter);
				String ftcOfDest = tripRequest.getToLoc().substring(0, 2);
				String ftcOfSour = tripRequest.getFromLoc().substring(0, 2);
				String tripId = hours + minutes + ftcOfSour + ftcOfDest;
				tripRequest.setTripId(tripId);
				when(tripRepo.save(Mockito.any())).thenReturn(mockOfTrip);
				String actual = tripServiceImpl.insertNewRide(tripRequest);
				assertEquals("success", actual);
			} catch (Exception e) {
				e.printStackTrace();
				assertTrue(true);
			}

		}
		
		@Test
		void insertNewRide_Exception() {
			try {
				TripDTO tripRequest = new TripDTO();
				tripRequest.setTripId("234");
				tripRequest.setCreatorUserId("RSH01");
				tripRequest.setVehicleId("MP07GA6442");
				tripRequest.setRideDate(LocalDate.of(2024, Month.FEBRUARY, 27));
				tripRequest.setRideTime(LocalTime.parse("15:28:35"));
				tripRequest.setRideStatus(RideStatus.Planned);
				tripRequest.setNoOfSeat(63);
				tripRequest.setSeatsFilled(32);
				tripRequest.setFromLoc("PUNE");
				tripRequest.setToLoc("GWALIOR");

				when(tripRepo.save(Mockito.any())).thenThrow(SQLException.class);
				tripServiceImpl.insertNewRide(tripRequest);
				assertTrue(false);
			} catch (Exception e) {

				assertTrue(true);
			}
		}
		
		@Test
		void testUpdateTripRequestStatus_Positive() {
			try {
				Trip t = new Trip();
				
				t.setTripId("8900PUGW");
				t.setCreatorUserId("001L");
				t.setVehicleId("MP07GA6442");
				t.setRideDate(LocalDate.of(2024, Month.FEBRUARY, 27));
				t.setRideTime(LocalTime.parse("15:28:35"));
				t.setRideStatus(RideStatus.Planned);
				t.setNoOfSeat(63);
				t.setSeatsFilled(32);
				t.setFromLoc("PUNE");
				t.setToLoc("GWALIOR");
				Optional<Trip> optionalOfTrip = Optional.of(t);
				when(tripRepo.findById("8900PUGW")).thenReturn(optionalOfTrip);

				TripDTO updatedDto = new TripDTO();
				updatedDto.setRideStatus(RideStatus.Cancelled);

				Trip tnew = new Trip();
				tnew.setRideStatus(updatedDto.getRideStatus());
				tripRepo.save(tnew);
				when(tripRepo.save(t)).thenReturn(tnew);

				String actual = tripServiceImpl.updateTrip("8900PUGW", updatedDto);
				assertEquals("success", actual);
			
			} catch (Exception e) {
				e.printStackTrace();
				assertTrue(false);
			}

		}
		

		
		
}
