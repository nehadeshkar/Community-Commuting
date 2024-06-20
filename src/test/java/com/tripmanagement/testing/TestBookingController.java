package com.tripmanagement.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tripmanagement.controller.BookingController;
import com.tripmanagement.entities.Trip;
import com.tripmanagement.enums.BookingStatus;
import com.tripmanagement.model.BookingDTO;
import com.tripmanagement.service.BookingService;


import java.util.ArrayList;
import java.util.List;

public class TestBookingController {

	@Mock
	private BookingService bookingService;

	@InjectMocks
    private BookingController bookingController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //initializing mock objs
    }

    
    @Test
    void testGetBookingsWithValidData() {
        // Arrange
        List<BookingDTO> mockResponseList = new ArrayList<>();
        mockResponseList.add(new BookingDTO(1, new Trip(),10,"S01",BookingStatus.Booked));
        mockResponseList.add(new BookingDTO(2, new Trip(),10,"S01",BookingStatus.Completed));
        Mockito.when(bookingService.getBookingList()).thenReturn(mockResponseList);

        // Act
        ResponseEntity<List<BookingDTO>> response = (ResponseEntity<List<BookingDTO>>) bookingController.getBookings();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponseList, response.getBody());
    }

    @Test
    void testGetBookingsWithEmptyData() {
        // Arrange
        Mockito.when(bookingService.getBookingList()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<BookingDTO>> response = (ResponseEntity<List<BookingDTO>>) bookingController.getBookings();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    //======================================================================
    
        @Test
        void testBookRideWithSuccess() {
            // Arrange
            BookingDTO mockBookingDto = new BookingDTO();
            mockBookingDto.setBookingId(1);
            Mockito.when(bookingService.bookRide(mockBookingDto)).thenReturn("success");

            // Act
            ResponseEntity<?> response = bookingController.bookRide(mockBookingDto);

            // Assert
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals("Booking Id : 1", response.getBody());
        }

        @Test
        void testBookRideWithFailure() {
            // Arrange
            BookingDTO mockBookingDto = new BookingDTO();
            Mockito.when(bookingService.bookRide(mockBookingDto)).thenReturn("failure");

            // Act
            ResponseEntity<?> response = bookingController.bookRide(mockBookingDto);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
        
        //================================================================

            @Test
            void testUpdateBookingWithSuccess() {
                // Arrange
                int mockBookingId = 1;
                BookingDTO mockBookingDto = new BookingDTO();
                Mockito.when(bookingService.updateBooking(mockBookingId, mockBookingDto)).thenReturn("success");

                // Act
                ResponseEntity<?> response = bookingController.updateBooking(mockBookingId, mockBookingDto);

                // Assert
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertNotNull(response.getBody());
            }

            @Test
            void testUpdateBookingWithFailure() {
                // Arrange
                int mockBookingId = 2;
                BookingDTO mockBookingDto = new BookingDTO();
                Mockito.when(bookingService.updateBooking(mockBookingId, mockBookingDto)).thenReturn("failure");

                // Act
                ResponseEntity<?> response = bookingController.updateBooking(mockBookingId, mockBookingDto);

                // Assert
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertNull(response.getBody());
            }

}
