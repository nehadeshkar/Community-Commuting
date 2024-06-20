package com.tripmanagement.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripmanagement.entities.Booking;
import com.tripmanagement.entities.Trip;
import com.tripmanagement.enums.BookingStatus;
import com.tripmanagement.enums.RideStatus;
import com.tripmanagement.exception.DateNotMatched;
import com.tripmanagement.exception.ResourceNotFoundException;
import com.tripmanagement.exception.StatusIsNotConsistent;
import com.tripmanagement.model.BillMasterDTO;
import com.tripmanagement.model.TripDTO;
import com.tripmanagement.repositories.BookingRepository;
import com.tripmanagement.repositories.TripRepository;
import com.tripmanagement.service.TripService;

@Service("tripServiceImpl")
public class TripServiceImpl implements TripService {
	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private BookingRepository bookingRepository;

	/**
	 * Display Rides
	 */
	public List<TripDTO> getTripList() {
		Iterable<Trip> tripList = tripRepository.findAll();
		List<TripDTO> tripDTOList = new ArrayList<TripDTO>();
		for (Trip t : tripList) {
			TripDTO tripDto = new TripDTO();
			tripDto.setCreatorUserId(t.getCreatorUserId());
			tripDto.setFromLoc(t.getFromLoc());
			tripDto.setNoOfSeat(t.getNoOfSeat());
			tripDto.setRideDate(t.getRideDate());
			tripDto.setRideStatus(t.getRideStatus());
			tripDto.setRideTime(t.getRideTime());
			tripDto.setSeatsFilled(t.getSeatsFilled());
			tripDto.setToLoc(t.getToLoc());
			tripDto.setTripId(t.getTripId());
			tripDto.setVehicleId(t.getVehicleId());
			tripDTOList.add(tripDto);
		}
		return tripDTOList;
	}

	/**
	 *  Insert New Ride
	 */
	public String insertNewRide(TripDTO tripDto) {
		System.out.println(tripDto);
		Trip trip = new Trip();
		trip.setCreatorUserId(tripDto.getCreatorUserId());
		trip.setVehicleId(tripDto.getVehicleId());
		trip.setRideDate(tripDto.getRideDate());
		if (tripDto.getRideDate().isBefore(LocalDate.now())) {
			throw new DateNotMatched("Past Date not allowed", tripDto.getRideDate());
		}
		trip.setRideTime(tripDto.getRideTime());
		if (tripDto.getRideStatus() != null) {
			trip.setRideStatus(tripDto.getRideStatus());
		}
		trip.setNoOfSeat(tripDto.getNoOfSeat());
		trip.setSeatsFilled(tripDto.getSeatsFilled());
		if (tripDto.getSeatsFilled() > tripDto.getNoOfSeat()) {
			throw new StatusIsNotConsistent("Seats filled can't be more than total no. of Seats", String.valueOf(tripDto.getNoOfSeat()));
		}
		
		trip.setFromLoc(tripDto.getFromLoc());
		if(tripDto.getFromLoc().equals(tripDto.getToLoc())) {
			throw new StatusIsNotConsistent("Source & Destination can't be same", tripDto.getToLoc().toString());
		}
		
		trip.setToLoc(tripDto.getToLoc());

		// Creating TripID
		DateTimeFormatter Hoursformatter = DateTimeFormatter.ofPattern("HH");
		String hours = trip.getRideTime().format(Hoursformatter);
		DateTimeFormatter Minutesformatter = DateTimeFormatter.ofPattern("mm");
		String minutes = trip.getRideTime().format(Minutesformatter);
		String ftcOfDest = trip.getToLoc().substring(0, 2);
		String ftcOfSource = trip.getFromLoc().substring(0, 2);
		String tripId = hours + minutes + ftcOfSource + ftcOfDest;
		trip.setTripId(tripId.toUpperCase());

		// CreatingVehicleID
		String vehicleId = ftcOfDest + minutes + ftcOfSource + hours;
		trip.setVehicleId(vehicleId);

		Trip newRideInserted = tripRepository.save(trip);
		if (newRideInserted != null)
			return "success";
		else
			return "fail";
	}

	/**
	 * Generate New Bill on the basis of TripId
	 */

	@Override
	public double generateBill(String tripId, BillMasterDTO billMasterDto) {

		Optional<Trip> trip = tripRepository.findById(tripId);
		if (trip.isPresent()) {
			return billMasterDto.getTotalBill();
		} else {
			return 0.0;
		}
	}

	/**
	 * Cancel planned trip 
	 */

	@Override
	public String updateTrip(String tripId, TripDTO tripDto) {
		System.out.println(tripDto);
		Optional<Trip> optionalOfTrip = tripRepository.findById(tripId);
		if (optionalOfTrip.isEmpty()) {
			throw new ResourceNotFoundException("TripId", "TripId", tripId);
		}
		else {
			Trip updatedtrip = null;
			Trip trip = optionalOfTrip.get();	
			List<Booking> tripToUpdate = bookingRepository.findByTrip(trip);
				if (!tripToUpdate.isEmpty()) {
					for (Booking b : tripToUpdate) {
						if (tripDto.getRideStatus() == RideStatus.Cancelled) {
							b.setBookingStatus(BookingStatus.Cancelled);
							bookingRepository.save(b);
						}
						if(tripDto.getRideStatus()==RideStatus.Completed) {
							b.setBookingStatus(BookingStatus.Completed);
							bookingRepository.save(b);
						}
						if(tripDto.getRideStatus()==RideStatus.Started) {
							b.setBookingStatus(BookingStatus.Booked);
							bookingRepository.save(b);
						}
					}
				}
					trip.setRideStatus(tripDto.getRideStatus());
					updatedtrip = tripRepository.save(trip);
					
			if (updatedtrip != null) {
				return "success";
			} else {
				return "fail";
			}		

		}
	}
	
	
}