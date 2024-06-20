package com.tripmanagement.entities;

import com.tripmanagement.enums.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data

@Table(name = "booking")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Booking_Id_Booking")
	private int bookingId;

	@ManyToOne
	@JoinColumn(name = "trip_Id_trip")
	private Trip trip;
	
	@Column(name = "number_Of_Seat")
	private int numberOfSeat;

	@Column(name = "seeker_Id")
	private String seekerId;

	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private BookingStatus bookingStatus;

}
