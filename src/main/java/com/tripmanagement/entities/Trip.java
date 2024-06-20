package com.tripmanagement.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import com.tripmanagement.enums.RideStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "trip")
public class Trip {

	@Id
	@Column(name="trip_Id_trip")
	private String tripId;

	@Column(name = "creator_User_Id")
	private String creatorUserId;

	@Column(name = "vehicle_Id")
	private String vehicleId;

	@Column(name = "ride_Date")
	private LocalDate rideDate;

	@Column(name = "ride_Time")
	private LocalTime rideTime;

	@Column(name = "ride_Status")
	@Enumerated(value = EnumType.STRING)
	private RideStatus rideStatus;
	
	@Column(name = "no_Of_Seat")
	private int noOfSeat;

	@Column(name = "seats_filled")
	private int seatsFilled;

	@Column(name = "from_Loc")
	private String fromLoc;

	@Column(name = "to_Loc")
	private String toLoc;

	public Trip(String id) {
		this.tripId = id;
	}
}
