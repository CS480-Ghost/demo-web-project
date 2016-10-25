package edu.csupomona.cs480;

import org.junit.Test;

import edu.csupomona.cs480.controller.WebController;

import static org.junit.Assert.*;

public class JUnitTest {

    @Test
    public void testConcatenate() {
        WebController junitTest = new WebController();
        String result = junitTest.concatenate("concatenate", "together");
        assertEquals("concatenatetogether", result);
        System.out.println(result);

    }
    
}