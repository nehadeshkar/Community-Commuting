package com.tripmanagement.service;

import com.tripmanagement.model.BillMasterDTO;

public interface BillService {
	double generateBillbyBillId(String billId, BillMasterDTO billMasterDto);
}
