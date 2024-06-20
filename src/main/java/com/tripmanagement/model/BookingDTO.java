package com.tripmanagement.model;

import com.tripmanagement.entities.Trip;
import com.tripmanagement.enums.BookingStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

	private int bookingId;

	private Trip trip;
	
	@NotNull
	private int numberOfSeat;

	private String seekerId;

	private BookingStatus bookingStatus;

}
