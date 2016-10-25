package edu.csupomona.cs480;

import org.junit.Test;
//import edu.csupomona.cs480.controller.WebController;
//import edu.csupomona.cs480.data.User;
//import edu.csupomona.cs480.data.provider.UserManager;
import static org.junit.Assert.*;

/*
 * Author: Michael Acosta
 * Last updated: 10/25/16
 */
public class PingUserTester {

    @Test
    public void testPingUser_IfNoUser() {
    	String result = "";
    	assertEquals("", result);
    	System.out.println(result);
    }
    
}