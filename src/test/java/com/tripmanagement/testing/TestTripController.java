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

import com.tripmanagement.controller.TripController;
import com.tripmanagement.enums.RideStatus;
import com.tripmanagement.model.StatusDTO;
import com.tripmanagement.model.TripDTO;
import com.tripmanagement.service.TripService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TestTripController {

	    @InjectMocks
	    private TripController tripController;

	    @Mock
	    private TripService tripService;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testFetchAllTripsWithValidData() {
	        // Arrange
	        List<TripDTO> mockResponseList = new ArrayList<>();
	        mockResponseList.add(new TripDTO("0230PUNA","RPDE03","NA30PU02",LocalDate.of(2024,03,20),LocalTime.of(2,30,00) ,RideStatus.Planned,10,5,"Nagpur","Bhopal"));
	        mockResponseList.add(new TripDTO("0930MUPU", "RPDE01", "PU30MU09", LocalDate.of(2024,04,20), LocalTime.of(9,30,00), RideStatus.Planned, 6, 1, "MUMBAI", "Pune"));
	        Mockito.when(tripService.getTripList()).thenReturn(mockResponseList);

	        // Act
	        ResponseEntity<List<TripDTO>> response = (ResponseEntity<List<TripDTO>>) tripController.fetchAllTrips();

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(mockResponseList, response.getBody());
	    }

	    @Test
	    void testFetchAllTripsWithEmptyData() {
	        // Arrange
	        Mockito.when(tripService.getTripList()).thenReturn(new ArrayList<>());

	        // Act
	        ResponseEntity<List<TripDTO>> response = (ResponseEntity<List<TripDTO>>) tripController.fetchAllTrips();

	        // Assert
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertNull(response.getBody());
	    }
	    
	    //====================================================================

	        @Test
	        void testInsertNewRideWithSuccess() {
	            // Arrange
	            TripDTO mockTripDto = new TripDTO();
	            Mockito.when(tripService.insertNewRide(mockTripDto)).thenReturn("success");

	            // Act
	            ResponseEntity<?> response = tripController.insertNewRide(mockTripDto);

	            // Assert
	            assertEquals(HttpStatus.CREATED, response.getStatusCode());
	            assertNotNull(response.getBody());
	            assertEquals("Approved", ((StatusDTO) response.getBody()).getStatus());
	        }

	        @Test
	        void testInsertNewRideWithFailure() {
	            // Arrange
	            TripDTO mockTripDto = new TripDTO();
	            Mockito.when(tripService.insertNewRide(mockTripDto)).thenReturn("failure");

	            // Act
	            ResponseEntity<?> response = tripController.insertNewRide(mockTripDto);

	            // Assert
	            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	            assertNull(response.getBody());
	        }
	        
//	        =================================================================
	        
	        
	        
	            @Test
	            void testUpdateTripWithSuccess() {
	                // Arrange
	                String mockTripId = "123";
	                TripDTO mockTripDto = new TripDTO();
	                Mockito.when(tripService.updateTrip(mockTripId, mockTripDto)).thenReturn("success");

	                // Act
	                ResponseEntity<?> response = tripController.updateTrip(mockTripId, mockTripDto);

	                // Assert
	                assertEquals(HttpStatus.CREATED, response.getStatusCode());
	                assertNotNull(response.getBody()); // No specific response body for success
	            }

	            @Test
	            void testUpdateTripWithFailure() {
	                // Arrange
	                String mockTripId = "456";
	                TripDTO mockTripDto = new TripDTO();
	                Mockito.when(tripService.updateTrip(mockTripId, mockTripDto)).thenReturn("failure");

	                // Act
	                ResponseEntity<?> response = tripController.updateTrip(mockTripId, mockTripDto);

	                // Assert
	                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	                assertNull(response.getBody());
	            }
}
