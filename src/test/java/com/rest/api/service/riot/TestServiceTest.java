package com.rest.api.service.riot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


//@SpringBootTest(properties = "spring.profiles.active:local")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestServiceTest {

    private final TestService testService;
    public TestServiceTest(TestService testService){
        this.testService = testService;
    }

    @Test
    public void printTest() {
        testService.printTest();
    }
}