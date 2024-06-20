package com.tripmanagement.testing;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tripmanagement.controller.TripController;
import com.tripmanagement.tm.TripmanagementApplication;

@SpringBootTest(classes=TripmanagementApplication.class)
class TripmanagementApplicationTests {
	@Autowired
	private TripController tripController;
	@Test
	void contextLoads() {
		assertNotNull(tripController);
	}

}
