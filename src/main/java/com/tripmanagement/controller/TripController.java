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

import com.tripmanagement.model.StatusDTO;
import com.tripmanagement.model.TripDTO;
import com.tripmanagement.service.TripService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/tripmanager")
@Tag(name="CCA",description = "Trip Management Rest API")
@Slf4j
public class TripController {
	
	@Autowired
	private TripService tripService;

	/**
	 * Handler - Retrieving all trips details
	 * @return
	 */
	@Operation(description = "Get All the trips to display")
	@GetMapping("/trip")
	public ResponseEntity<?> fetchAllTrips() {
		List<TripDTO> responseList = tripService.getTripList();
		ResponseEntity<List<TripDTO>> responseEntity = null;
		if (!responseList.isEmpty()) {
			log.info("All trips details retrieved");
			responseEntity = new ResponseEntity<List<TripDTO>>(responseList, HttpStatus.OK);
		} else {
			log.error("Not able to retrieve trip details");
			responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}

	/**
	 * Handler - Adding a new Ride
	 * @param tripDto
	 * @return
	 */
	@Operation(description = "To Add a New Trip in Database")
	@PostMapping("/new") // Return code and trip id 
	public ResponseEntity<?> insertNewRide(@RequestBody TripDTO tripDto) {
		String result = tripService.insertNewRide(tripDto);
		if (result.equals("success")) {
			log.info("New ride inserted");
			StatusDTO statusDto = new StatusDTO();
			statusDto.setStatus("Approved");
			return new ResponseEntity<>(statusDto, HttpStatus.CREATED);
		} else {
			log.error("Not able to insert new ride");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Handler - Update Trip details when ride is cancelled
	 * @param tripId
	 * @param tripDto
	 * @return
	 */
	@Operation(description = "To Update the Trips")
	@PutMapping("/{tripId}/updatetrip")
	public ResponseEntity<?> updateTrip(@PathVariable("tripId") String tripId, @RequestBody TripDTO tripDto) {
		String result = tripService.updateTrip(tripId, tripDto);
		if (result.equals("success")) {
			log.info("Trip updated");
			StatusDTO statusDto = new StatusDTO();
			statusDto.setStatus("Approved");
			return new ResponseEntity<>(statusDto,HttpStatus.CREATED);
		} else {
			log.error("Not able to update trip");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
