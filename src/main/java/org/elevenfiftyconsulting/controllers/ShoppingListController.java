package org.elevenfiftyconsulting.controllers;

import javax.validation.Valid;

import org.elevenfiftyconsulting.beans.ShoppingListItem;
//import org.elevenfiftyconsulting.repositories.NoteRepository;
import org.elevenfiftyconsulting.repositories.ShoppingListItemRepository;
import org.elevenfiftyconsulting.repositories.ShoppingListRepository;
//import org.elevenfiftyconsulting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShoppingListController {

//	@Autowired
//	private NoteRepository noteRepo;

	@Autowired
	private ShoppingListItemRepository shoppingListItemRepo;

	@Autowired
	private ShoppingListRepository shoppingListRepo;

//	@Autowired
//	private UserRepository userRepo;

	@RequestMapping("/")
	public String home(Model model) {
		return "shoppingList";
	}

	@RequestMapping("shoppingList/{shoppingListId}")
	public String listShoppingListItems(@PathVariable(name="shoppingListId") int shoppingListId, Model model) {
		model.addAttribute("shoppingListItems", shoppingListRepo.findOne(shoppingListId));
		return "shoppingListItems";
	}
	
	//create view shoppingListItem
	@GetMapping("/shoppingListItem/create")
	public String shoppingListItemCreate(Model model) {
		model.addAttribute(new ShoppingListItem());
		return "shoppingListItem/shoppingListItemCreate";
	}
	
	//save created shoppingListItem
	@PostMapping("/shoppingListItem/create")
	public String shoppingListItemCreate(@ModelAttribute @Valid ShoppingListItem shoppingListItem, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("shoppingListItem", shoppingListItem);
			return "shoppingListItem/shoppingListItemCreate";
		} else {
			shoppingListItemRepo.save(shoppingListItem);
			return "redirect:/shoppingListItems";
		}

	}

}
