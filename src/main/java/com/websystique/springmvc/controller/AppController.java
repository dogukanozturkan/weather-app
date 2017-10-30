package com.websystique.springmvc.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.websystique.springmvc.domain.Message;
import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Report;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserProfile;
import com.websystique.springmvc.service.LocationService;
import com.websystique.springmvc.service.ReportService;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	LocationService locationService;

	@Autowired
	ReportService reportService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	Report reportUser;

	Message message;

	@RequestMapping(value = "/")
	public String showMainPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());

		return "index";
	}

	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		return "userslist";
	}

	@RequestMapping(value = "/weather-{localId}-{localName}")
	public String getWeather(HttpServletRequest request, @PathVariable String localName, @PathVariable int localId, ModelMap model) throws Exception {

		long startTime = System.currentTimeMillis();

		RestTemplate restTemplate = new RestTemplate();
		message = restTemplate.getForObject("http://api.wunderground.com/api/02691c595cf741f3/conditions/q/TR/" + localName + ".json", Message.class);

		System.out.println("message: " + message.getCurrentObservation());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		User u = userService.findBySSO(userDetail.getUsername());
		request.getSession().setAttribute("userId", u.getId());
		int userId = (int) request.getSession().getAttribute("userId");
		System.out.println("USERID: " + userId);
		reportUser = new Report();
		reportUser.setReportUserId(userId);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp);

		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

		String ip = in.readLine();
		System.out.println(ip);
		reportUser.setReportUserIpAddress(ip);
		System.out.println("ip: " + ip);

		reportUser.setReportTime(timestamp);

		System.out.println("local id: " + localId);
		reportUser.setReportLocationId(localId);

		if (message.getCurrentObservation() != null) {
			String reportResult = (message.getCurrentObservation().getDisplayLocation().getCity() + " " + message.getCurrentObservation().getTempC()).trim();
			System.out.println(reportResult);
			reportUser.setReportResult(reportResult);
			reportUser.setReportStatus("BASARILI");

		} else {
			reportUser.setReportStatus("BASARISIZ");
		}

		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("estimatedtime: " + estimatedTime);
		reportUser.setReportPassingTime(estimatedTime);

		reportService.saveReport(reportUser);

		return "redirect:/weather";
	}

	@RequestMapping(value = "/weather")
	public String getWeather(ModelMap model) {
		List<Location> location = locationService.findAllLocations();
		model.addAttribute("location", location);
		model.addAttribute("message", message);

		model.addAttribute("loggedinuser", getPrincipal());
		return "weather";
	}

	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public String editLocation(ModelMap model) {

		List<Location> location = locationService.findAllLocations();
		model.addAttribute("location", location);
		model.addAttribute("loggedinuser", getPrincipal());
		return "location";
	}

	@RequestMapping(value = "/newlocation", method = RequestMethod.GET)
	public String newLocation(ModelMap model) {
		Location location = new Location();
		model.addAttribute("location", location);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "regloc";
	}

	@RequestMapping(value = "/newlocation", method = RequestMethod.POST)
	public String saveLocation(@Valid Location location, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "regloc";
		}
		locationService.saveLocation(location);

		model.addAttribute("success", "Location is the " + location.getLocationName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());

		return "reglocsuccess";
	}

	@RequestMapping(value = { "/edit-location-{locationId}" }, method = RequestMethod.GET)
	public String editLocation(@PathVariable int locationId, ModelMap model) {
		Location location = locationService.findByLocationId(locationId);
		model.addAttribute("location", location);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "regloc";
	}

	@RequestMapping(value = { "/edit-location-{locationId}" }, method = RequestMethod.POST)
	public String updateUser(@Valid Location location, BindingResult result, ModelMap model, @PathVariable int locationId) {

		if (result.hasErrors()) {
			return "regloc";
		}

		locationService.updateLocation(location);

		model.addAttribute("success", "Location is the " + location.getLocationName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "reglocsuccess";
	}

	@RequestMapping(value = { "/delete-location-{locationId}" }, method = RequestMethod.GET)
	public String deleteLocation(@PathVariable int locationId) {
		locationService.deleteLocation(locationId);
		return "redirect:/location";
	}

	@RequestMapping(value = "/report")
	public String report(ModelMap model) {
		List<Report> report = reportService.findAllReports();
		List<Integer> locationIds = reportService.getDistinctAllLocationId();

		model.addAttribute("allLocationId", locationIds);
		model.addAttribute("report", report);
		model.addAttribute("loggedinuser", getPrincipal());
		return "report";
	}

	@RequestMapping(value = "/report&{locationId}&{queryStatus}&{fromDate}&{toDate}")
	public String report(ModelMap model, @PathVariable String locationId, @PathVariable String queryStatus, @PathVariable String fromDate, @PathVariable String toDate) {
		List<Report> report = reportService.filterReports(locationId, queryStatus, fromDate, toDate);
		model.addAttribute("report", report);
		List<Integer> locationIds = reportService.getDistinctAllLocationId();
		model.addAttribute("allLocationId", locationIds);
		model.addAttribute("loggedinuser", getPrincipal());
		return "/report";
	}

	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		if (!userService.isUserSSOUnique(user.getId(), user.getSsoId())) {
			FieldError ssoError = new FieldError("user", "ssoId", messageSource.getMessage("non.unique.ssoId", new String[] { user.getSsoId() }, Locale.getDefault()));
			result.addError(ssoError);
			return "registration";
		}

		userService.saveUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());

		return "registrationsuccess";
	}

	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET, produces = "text/plain;charset=ISO-8859-9")
	public String editUser(@PathVariable String ssoId, ModelMap model) {
		User user = userService.findBySSO(ssoId);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST, produces = "text/plain;charset=ISO-8859-9")
	public String updateUser(@Valid User user, BindingResult result, ModelMap model, @PathVariable String ssoId) {

		if (result.hasErrors()) {
			return "registration";
		}

		userService.updateUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registrationsuccess";
	}

	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String ssoId) {
		userService.deleteUserBySSO(ssoId);
		return "redirect:/list";
	}

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}

	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}

	/**
	 * This method handles login GET requests. If users is already logged-in and
	 * tries to goto login page again, will be redirected to list page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
		} else {

			return "redirect:/index";
		}
	}

	/**
	 * This method handles logout requests. Toggle the handlers if you are
	 * RememberMe functionality is useless in your app.
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			// new SecurityContextLogoutHandler().logout(request, response,
			// auth);
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	/**
	 * This method returns true if users is already authenticated [logged-in],
	 * else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

}