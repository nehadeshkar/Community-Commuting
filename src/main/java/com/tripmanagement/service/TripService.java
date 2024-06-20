package com.tripmanagement.service;

import java.util.List;

import com.tripmanagement.model.BillMasterDTO;
import com.tripmanagement.model.TripDTO;

public interface TripService {
	
	String insertNewRide(TripDTO tripDto);
	double generateBill(String tripId, BillMasterDTO billMasterDto);
	String updateTrip (String tripId, TripDTO tripDto);
	List<TripDTO> getTripList();
}