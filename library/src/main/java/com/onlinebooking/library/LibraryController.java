package com.onlinebooking.library;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.onlinebooking.library.model.Item;
import com.onlinebooking.library.model.User;
import com.onlinebooking.library.service.LibraryService;
import com.onlinebooking.library.service.UserService;

@RestController
public class LibraryController {

	@Autowired
	private UserService userService;
	@Autowired
	private LibraryService libraryService;

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "Testing";
	}
	
	@PostMapping("/order")
	public ResponseEntity<String> orderItem(@RequestParam String userId, @RequestParam String title) {
	    User user = userService.getUserById(userId);
	    Item item = libraryService.getItemByTitle(title);

	    // Process order
	    String response = libraryService.borrowItem(user, item);

	    return ResponseEntity.ok(response);
	}

	@PostMapping("/return")
	public ResponseEntity<String> returnItems(@RequestParam String userId, @RequestParam List<String> titles) {
	    // Fetch the user by ID
	    User user = userService.getUserById(userId);

	    if (user == null) {
	        return ResponseEntity.badRequest().body("User not found.");
	    }

	    // Process return logic
	    String response = libraryService.returnItems(user, titles);

	    return ResponseEntity.ok(response);
	}
	
}