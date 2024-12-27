package com.onlinebooking.library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import com.onlinebooking.library.model.Item;
import com.onlinebooking.library.model.User;
import com.onlinebooking.library.service.LibraryService;
import com.onlinebooking.library.service.UserService;

@SpringBootTest
public class LibraryApplicationTests {

    @Mock
    private UserService userService;

    @Mock
    private LibraryService libraryService;

    @InjectMocks
    private LibraryController libraryController;

    @Test
    public void testOrderItemSuccess() {
        // Mock input data
        String userId = "1";
        String title = "Crime Novel";

        User mockUser = new User();
        mockUser.setUserId("1");
        mockUser.setSubscriptionPlan("Gold");
        mockUser.setAge(25);

        Item mockItem = new Item();
        mockItem.setTitle("Crime Novel");
        mockItem.setType("BOOK");
        mockItem.setAvailable(true);

        // Mock behavior
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(libraryService.getItemByTitle(title)).thenReturn(mockItem);
        when(libraryService.borrowItem(mockUser, mockItem)).thenReturn("Item borrowed successfully.");

        // Call API
        ResponseEntity<String> response = libraryController.orderItem(userId, title);

        // Verify and Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Item borrowed successfully.", response.getBody());
    }

    @Test
    public void testOrderItemUserNotFound() {
        // Mock input data
        String userId = "2";
        String title = "Crime Novel";

        // Mock behavior
        when(userService.getUserById(userId)).thenReturn(null);

        // Call API
        ResponseEntity<String> response = libraryController.orderItem(userId, title);

        // Verify and Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(null , response.getBody());
    }

    @SuppressWarnings("deprecation")
	@Test
    public void testOrderItemItemNotFound() {
        // Mock input data
        String userId = "1";
        String title = "Unknown Title";

        User mockUser = new User();
        mockUser.setUserId("1");

        // Mock behavior
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(libraryService.getItemByTitle(title)).thenReturn(null);

        // Call API
        ResponseEntity<String> response = libraryController.orderItem(userId, title);

        // Verify and Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testOrderItemBorrowingLimitExceeded() {
        // Mock input data
        String userId = "1";
        String title = "Crime Novel";

        User mockUser = new User();
        mockUser.setUserId("1");
        mockUser.setSubscriptionPlan("Silver");
        mockUser.setAge(25);

        Item mockItem = new Item();
        mockItem.setTitle("Crime Novel");
        mockItem.setType("BOOK");
        mockItem.setAvailable(true);

        // Mock behavior
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(libraryService.getItemByTitle(title)).thenReturn(mockItem);
        when(libraryService.borrowItem(mockUser, mockItem)).thenReturn("Borrowing limit exceeded for books for silver.");

        // Call API
        ResponseEntity<String> response = libraryController.orderItem(userId, title);

        // Verify and Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Borrowing limit exceeded for books for silver.", response.getBody());
    }

    @Test
    public void testOrderItemCrimeGenreAgeRestriction() {
        // Mock input data
        String userId = "1";
        String title = "Crime Novel";

        User mockUser = new User();
        mockUser.setUserId("1");
        mockUser.setSubscriptionPlan("Gold");
        mockUser.setAge(16); // Under 18

        Item mockItem = new Item();
        mockItem.setTitle("Crime Novel");
        mockItem.setGenre("Crime");
        mockItem.setType("BOOK");
        mockItem.setAvailable(true);

        // Mock behavior
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(libraryService.getItemByTitle(title)).thenReturn(mockItem);
        when(libraryService.borrowItem(mockUser, mockItem)).thenReturn("Crime genre is restricted for users under 18.");

        // Call API
        ResponseEntity<String> response = libraryController.orderItem(userId, title);

        // Verify and Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Crime genre is restricted for users under 18.", response.getBody());
    }
}

