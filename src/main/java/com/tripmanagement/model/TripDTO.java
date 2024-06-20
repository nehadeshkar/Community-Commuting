package com.tripmanagement.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.tripmanagement.enums.RideStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TripDTO {
	private String tripId;

	private String creatorUserId;

	private String vehicleId;
	
	@NotNull
	private LocalDate rideDate;

	@NotNull
	private LocalTime rideTime;

	private RideStatus rideStatus= RideStatus.Planned;
	
	@NotNull
	private int noOfSeat;

	private int seatsFilled;
	
	@NotNull
	private String fromLoc;
	
	@NotNull
	private String toLoc;

}
