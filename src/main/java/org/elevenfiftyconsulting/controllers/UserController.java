package org.elevenfiftyconsulting.controllers;

import static org.elevenfiftyconsulting.security.Role.ROLE_USER;

import java.util.Optional;

import org.elevenfiftyconsulting.beans.User;
import org.elevenfiftyconsulting.beans.UserRole;

//import java.util.List;

import org.elevenfiftyconsulting.repositories.UserRepository;
import org.elevenfiftyconsulting.repositories.UserRoleRepository;
import org.elevenfiftyconsulting.security.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

//import com.users.beans.UserImage;

@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserRoleRepository userRoleRepo;

	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
		return new ModelAndView("login", "error", error);
	}
	
	@RequestMapping("/register")
	public String register(Model model) {
		return createUser(model);
	}
	
	@RequestMapping("/login")
	public String error(){
		return "error";
	}

	@RequestMapping("/users")
	public String listUsers(Model model) {
		model.addAttribute("users", userRepo.findAllByOrderByFirstNameAscLastNameAsc());
		return "listUsers";
	}

	@RequestMapping("/user/{userId}")
	public String profile(@PathVariable long userId, Model model) {
		model.addAttribute("user", userRepo.findOne(userId));

		if (!permissionService.canAccessUser(userId)) {
			log.warn("Cannot allow user to view " + userId);
			return "redirect:/";
		}

		model.addAttribute("permissions", permissionService);
		return "profile";
	}
	
	@RequestMapping(value = "/user/{userId}/edit", method = RequestMethod.GET)
	public String profileEdit(@PathVariable long userId, Model model) {
		model.addAttribute("user", userRepo.findOne(userId));
		if (!permissionService.canAccessUser(userId)) {
			log.warn("Cannot allow user to edit" + userId);
			return "profile";
		}
		
		return "profileEdit";
	}
	
	@RequestMapping(value = "/user/{userId}/edit", method = RequestMethod.POST)
	public String profileSave(@ModelAttribute User user, @PathVariable long userId, Model model) {

		if (!permissionService.canAccessUser(userId)) {
			log.warn("Cannot allow user to edit" + userId);
			return "profile";
		}
		log.debug("Saving user " + user);
		userRepo.save(user);
		model.addAttribute("message", "User " + user.getEmail() + " saved.");

		model.addAttribute("message", "User " + user.getEmail() + " saved.");

		return profile(userId, model);
	}

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public String createUser(Model model) {
		model.addAttribute("user", new User());

		return "users/userCreate";
	}

	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public String createUser(@ModelAttribute User user, Model model) {

		log.info(user.toString());
		User savedUser = userRepo.save(user);
		UserRole role = new UserRole(savedUser, ROLE_USER);
		userRoleRepo.save(role);
		

		return profile(savedUser.getId(), model);
	}

	@RequestMapping("/myprofile")
	public String myProfile(Model model) {
		return profile(permissionService.findCurrentUserId(), model);
	}
	
	
	

}
