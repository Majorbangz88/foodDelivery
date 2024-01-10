package com.joel.foodDelivery.services;

import com.joel.foodDelivery.dtos.requests.AddDriverRequest;
import com.joel.foodDelivery.dtos.requests.AddDriverResponse;
import com.joel.foodDelivery.exceptions.DriverAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DriverServiceTest {

    @Autowired
    private DriverService driverService;

    @BeforeEach
    public void setup() {
        driverService.deleteAll();
    }

    @Test
    public void testAddDrivers() {
        AddDriverRequest driverRequest = new AddDriverRequest();
        driverRequest.setName("Jake");
        driverRequest.setPhoneNumber("07023536754");

        AddDriverResponse driverResponse = driverService.addDriver(driverRequest);

        assertNotNull(driverResponse);

        AddDriverRequest driverRequest1 = new AddDriverRequest();
        driverRequest1.setName("Jade");
        driverRequest1.setPhoneNumber("08034239899");

        AddDriverResponse driverResponse1 = driverService.addDriver(driverRequest1);

        assertNotNull(driverResponse1);

        AddDriverRequest driverRequest3 = new AddDriverRequest();
        driverRequest3.setName("Jibola");
        driverRequest3.setPhoneNumber("08034657465");

        AddDriverResponse driverResponse2 = driverService.addDriver(driverRequest3);

        assertNotNull(driverResponse2);

        assertThat(driverService.count(), is(3L));
    }

    @Test
    public void testDriverUniqueness() {
        AddDriverRequest driverRequest = new AddDriverRequest();
        driverRequest.setName("Jake");
        driverRequest.setPhoneNumber("07023536754");

        AddDriverResponse driverResponse = driverService.addDriver(driverRequest);

        assertNotNull(driverResponse);

        AddDriverRequest driverRequest1 = new AddDriverRequest();
        driverRequest1.setName("Jake");
        driverRequest1.setPhoneNumber("07023536754");

        assertThrows(DriverAlreadyExistsException.class, ()-> driverService.addDriver(driverRequest1));
    }
}