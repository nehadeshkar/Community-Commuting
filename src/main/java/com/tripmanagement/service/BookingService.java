package com.tripmanagement.service;

import java.util.List;

import com.tripmanagement.model.BookingDTO;

public interface BookingService {
	String bookRide(BookingDTO bookingDto);

	String updateBooking(int bookingId, BookingDTO bookingDto);

	List<BookingDTO> getBookingList();
}
