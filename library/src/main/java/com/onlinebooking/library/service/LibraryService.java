package com.onlinebooking.library.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onlinebooking.library.model.Item;
import com.onlinebooking.library.model.User;
import com.onlinebooking.library.repository.LibraryItemRepository;
import com.onlinebooking.library.repository.UserRepository;

@Service
public class LibraryService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LibraryItemRepository itemRepository;

	public Item getItemByTitle(String title) {
	    return itemRepository.findByTitle(title).orElse(new Item()); // or some default Item
	}

	public Item findByTitle(String title) {
	    return itemRepository.findByTitle(title).orElse(new Item()); // Or any default Item object
	}
	
	public String borrowItem(User user, Item item) {
	    if (user == null) {
	        return "User not found.";
	    }
	    if (item == null) {
	        return "Item not found.";
	    }

	    // Validate the user's subscription plan and borrowing rules
	    String validationResponse = validateItem(user, item);

	    if (!"Success".equals(validationResponse)) {
	        return validationResponse;
	    }

	    // Borrow item logic
	    if (item.isAvailable()) {
	        user.getBorrowedItems().add(item);
	        item.setAvailable(false); // Mark item as borrowed
	        user.setTransactionsThisMonth(user.getTransactionsThisMonth() + 1);

	        // Update counts for books or magazines
	        if (item.getType().equalsIgnoreCase("BOOK")) {
	            user.setBorrowedBooks(user.getBorrowedBooks() + 1);
	        } else if (item.getType().equalsIgnoreCase("MAGAZINE")) {
	            user.setBorrowedMagazines(user.getBorrowedMagazines() + 1);
	        }

	        // Save the updates
	        userRepository.save(user);
	        itemRepository.save(item);

	        return "Item '" + item.getTitle() + "' borrowed successfully.";
	    } else {
	        return "Item is not available for borrowing.";
	    }
	}
	
	public static String validateItem(User user, Item item) {
	    // Check transaction limit
	    if (user.getTransactionsThisMonth() >= 10) {
	        return "Monthly transaction limit exceeded.";
	    }

	    // Check age restriction for Crime genre
	    if (item.getGenre().equalsIgnoreCase("Crime") && user.getAge() < 18) {
	        return "Crime genre is restricted for users under 18.";
	    }

	    // Calculate the current borrowed counts
	    int borrowedBooks = user.getBorrowedBooks();
	    int borrowedMagazines = user.getBorrowedMagazines();
	    // Validate based on subscription plan
	    switch (user.getSubscriptionPlan().toLowerCase()) {
	        case "silver":
	            if (item.getType().equalsIgnoreCase("MAGAZINE")) {
	                return "Magazines are not allowed in Silver plan.";
	            }
	            if (borrowedBooks >= 2) {
	                return "Borrowing limit exceeded for books in Silver plan.";
	            }
	            break;

	        case "gold":
	            if (item.getType().equalsIgnoreCase("BOOK") && borrowedBooks >= 3) {
	                return "Borrowing limit exceeded for books in Gold plan.";
	            }
	            if (item.getType().equalsIgnoreCase("MAGAZINE") && borrowedMagazines >= 1) {
	                return "Borrowing limit exceeded for magazines in Gold plan.";
	            }
	            break;

	        case "platinum":
	            if (item.getType().equalsIgnoreCase("BOOK") && borrowedBooks >= 4) {
	                return "Borrowing limit exceeded for books in Platinum plan.";
	            }
	            if (item.getType().equalsIgnoreCase("MAGAZINE") && borrowedMagazines >= 2) {
	                return "Borrowing limit exceeded for magazines in Platinum plan.";
	            }
	            break;

	        default:
	            return "Invalid subscription plan.";
	    }

	    return "Success";
	}
	
	public String returnItems(User user, List<String> titles) {
	    // Fetch the user's borrowed items
	    List<Item> borrowedItems = user.getBorrowedItems();

	    for (String title : titles) {
	        // Find the item by title in the user's borrowed items
	        Item item = borrowedItems.stream()
	                .filter(borrowedItem -> borrowedItem.getTitle().equalsIgnoreCase(title))
	                .findFirst()
	                .orElse(null);

	        if (item != null) {
	            // Mark the item as available
	            item.setAvailable(true);

	            // Remove the item from user's borrowed list
	            borrowedItems.remove(item);

	            // Decrement counts for books or magazines
	            if (item.getType().equalsIgnoreCase("BOOK")) {
	                user.setBorrowedBooks(user.getBorrowedBooks() - 1);
	            } else if (item.getType().equalsIgnoreCase("MAGAZINE")) {
	                user.setBorrowedMagazines(user.getBorrowedMagazines() - 1);
	            }

	            // Save changes
	            itemRepository.save(item);
	        } else {
	            return "Item not found in borrowed items: " + title;
	        }
	    }

	    userRepository.save(user);
	    return "Items returned successfully.";
	}
}