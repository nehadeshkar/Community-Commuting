package com.tripmanagement.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripmanagement.entities.Booking;
import com.tripmanagement.entities.Trip;
import com.tripmanagement.enums.BookingStatus;
import com.tripmanagement.enums.RideStatus;
import com.tripmanagement.exception.ResourceNotFoundException;
import com.tripmanagement.exception.StatusIsNotConsistent;
import com.tripmanagement.model.BookingDTO;
import com.tripmanagement.repositories.BookingRepository;
import com.tripmanagement.repositories.TripRepository;
import com.tripmanagement.service.BookingService;

@Service("bookingServiceImpl")
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private TripRepository tripRepository;
	
	String tripId="TripId";
	
	/**
	 * Fetch booking List
	 */
	@Override
	public List<BookingDTO> getBookingList() {
		Iterable<Booking> bookingList = bookingRepository.findAll();
		List<BookingDTO> displayBookingList = new ArrayList<BookingDTO>();
		Iterator<Booking> iteratorOfBooking=bookingList.iterator();
		while(iteratorOfBooking.hasNext()) {
			Booking b=iteratorOfBooking.next();
			BookingDTO bookingDto = new BookingDTO();
			
			bookingDto.setNumberOfSeat(b.getNumberOfSeat());
			bookingDto.setBookingId(b.getBookingId());
			bookingDto.setBookingStatus(b.getBookingStatus());
			bookingDto.setSeekerId(b.getSeekerId());
			bookingDto.setTrip(b.getTrip());
			displayBookingList.add(bookingDto);

		}
		return displayBookingList;

	}

	/**
	 * Register or Book a new ride
	 */
	@Override
	public String bookRide(BookingDTO bookingDto) {
		Booking booking = new Booking();

		Trip trip = tripRepository.findByTripId(bookingDto.getTrip().getTripId());

		if (trip == null) {
			throw new ResourceNotFoundException(tripId, tripId, bookingDto.getTrip().getTripId());
		}
		if (bookingDto.getNumberOfSeat() <= 0) {
			throw new StatusIsNotConsistent("Number of seat's can't be less than zero", String.valueOf(bookingDto.getNumberOfSeat()));
		}
		if (bookingDto.getNumberOfSeat()> trip.getNoOfSeat()-trip.getSeatsFilled()) {
			throw new StatusIsNotConsistent("Number of seat's can't be greater than available seats", String.valueOf(bookingDto.getNumberOfSeat()));
		}

		if (trip.getRideStatus() != RideStatus.Planned) {
			throw new StatusIsNotConsistent("Can't book ride as trip ride status is not planned", trip.getRideStatus().toString());
		} else {
			int newSeats = trip.getSeatsFilled() + bookingDto.getNumberOfSeat();
			trip.setSeatsFilled(newSeats);
			tripRepository.save(trip);

			booking.setBookingId(bookingDto.getBookingId());
			booking.setTrip(bookingDto.getTrip());
			booking.setNumberOfSeat(bookingDto.getNumberOfSeat());
			booking.setSeekerId(bookingDto.getSeekerId());
			booking.setBookingStatus(bookingDto.getBookingStatus());
			Booking book = bookingRepository.save(booking);
			if (book != null)
				return "success";
			else
				return "fail";
		}

	}

	/**
	 *  Update Ride
	 */
	@Override
public String updateBooking(int bookingId, BookingDTO bookingDto) {
		
		Optional<Booking> optionalofBooking = bookingRepository.findById(bookingId);
		if (optionalofBooking.isEmpty()) {
			throw new ResourceNotFoundException("BookingId", "BookingId", Long.toString(bookingId));
		}
		
		Optional<Trip> optionaltriptoUpdate = tripRepository.findById(optionalofBooking.get().getTrip().getTripId());
		if(optionaltriptoUpdate.isEmpty()) {
			throw new ResourceNotFoundException(tripId, tripId, optionalofBooking.get().getTrip().getTripId());
		}
		else {
			Booking updatedbook = null;
			Booking book = optionalofBooking.get();
				
				if (bookingDto.getBookingStatus() == BookingStatus.Completed) {
					if (optionaltriptoUpdate.get().getRideStatus() == RideStatus.Completed) {
						book.setBookingStatus(bookingDto.getBookingStatus());
					} else {
						throw new StatusIsNotConsistent("Trip Status",
								optionaltriptoUpdate.get().getRideStatus().toString());
					}
				} else {
					if (optionaltriptoUpdate.get().getRideStatus() == RideStatus.Cancelled
							|| optionaltriptoUpdate.get().getRideStatus() == RideStatus.Planned) {
						book.setBookingStatus(bookingDto.getBookingStatus());
						
							Trip t = optionaltriptoUpdate.get();
							int newseats = t.getSeatsFilled() - book.getNumberOfSeat();
							t.setSeatsFilled(newseats);
							tripRepository.save(t);
					} else {
						throw new StatusIsNotConsistent("Trip Status",
								optionaltriptoUpdate.get().getRideStatus().toString());
					}
				}
				updatedbook = bookingRepository.save(book);
				if (updatedbook != null) {
					return "success";
				}

				else {
					return "fail";
				}

			
		}

	}

}
