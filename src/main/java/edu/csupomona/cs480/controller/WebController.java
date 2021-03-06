package edu.csupomona.cs480.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.csupomona.cs480.App;
import edu.csupomona.cs480.data.User;
import edu.csupomona.cs480.data.provider.UserManager;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import org.apache.commons.math3.stat.regression.SimpleRegression;


/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 *
 */

@RestController
public class WebController {

	/**
	 * When the class instance is annotated with
	 * {@link Autowired}, it will be looking for the actual
	 * instance from the defined beans.
	 * <p>
	 * In our project, all the beans are defined in
	 * the {@link App} class.
	 */
	@Autowired
	private UserManager userManager;
	
	/**
	 *  Added Commons Math method that applies the ordinary
	 *  linear regression to two series and displays the slope
	 *  and intercept.
	 */
//	@RequestMapping(value = "/cs480/commonsmath", method = RequestMethod.GET)
//	void commonsMathMethod() {
//		// Create regression object, with true as parameter (for the intercept)
//		SimpleRegression simple new SimpleRegression(true);
//		
//		// Pass series data to the model
//		simple.addData(new double[] [] {
//			{1, 2},
//            {2, 3},
//            {3, 4},
//            {4, 5},
//            {5, 6}
//		});
		
		// Print the slope and intercept
//		System.out.println("the slope = " + simple.getSlope());
//		System.out.println("the intercept = " + simple.getIntercept());
//	}
	
	
	/**
	 * junit test for string concatenation
	 * @test
	 */
	public String concatenate(String one, String two){
        return one + two;
    }
	
	/**
	 *  Added jsoup method which gets all of the hyperlinks
	 *  from google and displays them
	 * 	http://localhost:8080/cs480/ping
	 */
	@RequestMapping(value = "/cs480/jsoup", method = RequestMethod.GET)
	String jsoupMethod() {
		String printAll = null;
		Document doc;
		try {
			doc = Jsoup.connect("http://google.com").get();

			// get page title
			String title = doc.title();
			printAll = (title);

			// get all links
			Elements links = doc.select("a[href]");
			for (Element link : links) {
			//	Appends to string
				printAll = (printAll + "<br/><br/>Google " + link.text());
				printAll = (printAll + "<br/>Link : " + link.attr("href"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return printAll;
	}

	/**
	 * This is a simple example of how the HTTP API works.
	 * It returns a String "OK" in the HTTP response.
	 * To try it, run the web application locally,
	 * in your web browser, type the link:
	 * 	http://localhost:8080/cs480/ping
	 */
	@RequestMapping(value = "/cs480/ping", method = RequestMethod.GET)
	String healthCheck() {
		// You can replace this with other string,
		// and run the application locally to check your changes
		// with the URL: http://localhost:8080/
		return "OK";
	}

	/**
	 * This is a simple example of how the HTTP API works.
	 * It returns a String "test" in the HTTP response.
	 * To try it, run the web application locally,
	 * in your web browser, type the link:
	 * 	http://localhost:8080/cs480/ping
	 */
	@RequestMapping(value = "/cs480/pingUser", method = RequestMethod.GET)
	String pingUser(@PathVariable("userId") String userId) {
		if (!(userId.length() > 0)) {
			return "";
		}
		else {
			return userId;
		}
	}
	
	/**
	 * This is a simple example of how to use a data manager
	 * to retrieve the data and return it as an HTTP response.
	 * <p>
	 * Note, when it returns from the Spring, it will be
	 * automatically converted to JSON format.
	 * <p>
	 * Try it in your web browser:
	 * 	http://localhost:8080/cs480/user/user101
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.GET) 
	User getUser(@PathVariable("userId") String userId) {
		User user = userManager.getUser(userId);
		return user;
	}

	/**
	 * This is an example of sending an HTTP POST request to
	 * update a user's information (or create the user if not
	 * exists before).
	 *
	 * You can test this with a HTTP client by sending
	 *  http://localhost:8080/cs480/user/user101
	 *  	name=John major=CS
	 *
	 * Note, the URL will not work directly in browser, because
	 * it is not a GET request. You need to use a tool such as
	 * curl.
	 *
	 * @param id
	 * @param name
	 * @param major
	 * @return
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.POST)
	User updateUser(
			@PathVariable("userId") String id,
			@RequestParam("name") String name,
			@RequestParam(value = "major", required = false) String major) {
		User user = new User();
		user.setId(id);
		user.setMajor(major);
		user.setName(name);
		userManager.updateUser(user);
		return user;
	}

	/**
	 * This API deletes the user. It uses HTTP DELETE method.
	 *
	 * @param userId
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.DELETE)
	void deleteUser(
			@PathVariable("userId") String userId) {
		userManager.deleteUser(userId);
	}

	/**
	 * This API lists all the users in the current database.
	 *
	 * @return
	 */
	@RequestMapping(value = "/cs480/users/list", method = RequestMethod.GET)
	List<User> listAllUsers() {
		return userManager.listAllUsers();
	}

	/*********** Web UI Test Utility **********/
	/**
	 * This method provide a simple web UI for you to test the different
	 * functionalities used in this web service.
	 */
	@RequestMapping(value = "/cs480/home", method = RequestMethod.GET)
	ModelAndView getUserHomepage() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("users", listAllUsers());
		return modelAndView;
	}

}
