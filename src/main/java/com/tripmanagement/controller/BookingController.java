package com.tripmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripmanagement.model.BookingDTO;
import com.tripmanagement.model.StatusDTO;
import com.tripmanagement.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:4200")
@RestController //Request Handler
@RequestMapping("/api/tripmanager")
@Tag(name="CCA",description = "Trip Management Rest API")
@Slf4j
public class BookingController {
	@Autowired
	private BookingService bookingService;
	
	/**
	 * Handler - Retrieving booking list 
	 * @return
	 */
	
	@Operation(description = "Get All the Bookings to display")
	@GetMapping("/allbooking")
	public ResponseEntity<?> getBookings() {
		List<BookingDTO> responseList = bookingService.getBookingList();
		if (!responseList.isEmpty()) {
			log.info("Retrieving list of booking");
			return new ResponseEntity<List<BookingDTO>>(responseList, HttpStatus.OK);
		} else {
			log.error("Not able to retrieve booking list");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Handler - Book a Ride
	 * @param bookingDto
	 * @return
	 */
	@Operation(description = "To Add a New Booking in Database")
	@PostMapping("/bookRide") // code and trip id
	public ResponseEntity<?> bookRide(@RequestBody BookingDTO bookingDto) {
		System.out.println(bookingDto);
		String result = bookingService.bookRide(bookingDto);
		if (result.equals("success")) {
			log.info("Ride Booked");
			StatusDTO statusDto = new StatusDTO();
			statusDto.setStatus("Approved");
			return new ResponseEntity<>("Booking Id : " + bookingDto.getBookingId(), HttpStatus.CREATED);
		} else {
			log.error("Not able to book a ride");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Handler - Update Booking details when booking is cancelled
	 * @param bookingId
	 * @param bookingDto
	 * @return
	 */
	@Operation(description = "To Update the Bookings")
	@PutMapping("/{booking_Id}/update") 
	public ResponseEntity<?> updateBooking(@PathVariable("booking_Id") int bookingId,
			@RequestBody BookingDTO bookingDto) {
		String result = bookingService.updateBooking(bookingId, bookingDto);
		if (result.equals("success")) {
			log.info("Booking details updated");
			StatusDTO statusDto = new StatusDTO();
			statusDto.setStatus("Approved");
			return new ResponseEntity<>(statusDto, HttpStatus.CREATED);
		} else {
			log.error("Not able to update booking details");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
