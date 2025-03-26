package com.fooddelivery.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fooddelivery.exception.UserNotFoundException;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.UserRepository;

/**
 * Unit tests for {@link UserService}.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    /**
     * Sets up test data before each test.
     */
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPhoneNumber("1234567890");
        user.setAddress("123 Street, City");
    }

    /**
     * Tests creating a new user.
     */
    @Test
    void testCreateUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Tests retrieving a user by ID successfully.
     */
    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(1L, foundUser.get().getId());
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Tests retrieving a user by ID when the user does not exist.
     */
    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(1L);

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Tests retrieving a user by email successfully.
     */
    @Test
    void testGetUserByEmail_Success() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByEmail("john@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("john@example.com", foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail("john@example.com");
    }

    /**
     * Tests retrieving all users.
     */
    @Test
    void testGetAllUsers_Success() {
        List<User> users = Arrays.asList(user, new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> foundUsers = userService.getAllUsers();

        assertEquals(2, foundUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    /**
     * Tests updating a user successfully.
     */
    @Test
    void testUpdateUser_Success() {
        User updatedUser = new User();
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setPhoneNumber("9876543210");
        updatedUser.setAddress("New Address");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        Optional<User> result = userService.updateUser(1L, updatedUser);

        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getName());
        assertEquals("updated@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Tests updating a user when the user is not found.
     */
    @Test
    void testUpdateUser_NotFound() {
        User updatedUser = new User();
        updatedUser.setName("Updated Name");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.updateUser(1L, updatedUser);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Tests deleting a user successfully.
     */
    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    /**
     * Tests deleting a user when the user is not found.
     */
    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));

        assertEquals("User not found with ID: 1", exception.getMessage());
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
