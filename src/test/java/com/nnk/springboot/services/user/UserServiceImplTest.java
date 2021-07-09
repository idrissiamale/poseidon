package com.nnk.springboot.services.user;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    private UserServiceImpl userServiceImpl;
    private User user;
    private List<User> users;


    @BeforeEach
    public void setUp() {
        userServiceImpl = new UserServiceImpl(userRepository, passwordEncoder);
        user = new User(1, "amale", passwordEncoder.encode("Password1@"), "Amale Idrissi", "ADMIN");
        User user2 = new User(2, "johndoe", passwordEncoder.encode("Password1$"), "John Doe", "USER");
        users = new ArrayList<>();
        users.add(user);
        users.add(user2);
    }

    @Test
    @DisplayName("Checking that the new user is correctly saved")
    public void shouldReturnNewUserWhenSaved() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userToSave = userServiceImpl.save(user);

        verify(userRepository).save(any(User.class));
        assertNotNull(userToSave);
    }

    @Test
    @DisplayName("Comparing expected and actual username to check that the new user is correctly saved ")
    public void shouldGetTheSameRoleWhenNewUserIsCorrectlySaved() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userToSave = userServiceImpl.save(user);

        verify(userRepository).save(any(User.class));
        assertEquals(user.getUsername(), userToSave.getUsername());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when username already exists")
    public void shouldThrowExceptionWhenUserAlreadyExists() {
        when(userRepository.findByUsername("amale")).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.save(user));
        verify(userRepository).findByUsername("amale");
    }

    @Test
    @DisplayName("Checking that the user is updated with the new username")
    public void shouldReturnUserWithNewUsernameWhenUserUpdated() {
        user.setUsername("amale33");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userUpdated = userServiceImpl.update(1, user);

        verify(userRepository).save(any(User.class));
        assertNotNull(userUpdated);
    }

    @Test
    @DisplayName("Comparing expected username and actual username to check that the user was correctly updated ")
    public void shouldGetTheSameUsernameWhenUserUpdated() {
        user.setUsername("amale33");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userUpdated = userServiceImpl.update(1, user);

        verify(userRepository).save(any(User.class));
        assertEquals("amale33", userUpdated.getUsername());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the user we want to update is not found")
    public void shouldThrowExceptionWhenUserToUpdateIsNotFound() {
        doThrow(new IllegalArgumentException()).when(userRepository).findById(7);

        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.update(7, user));
        verify(userRepository).findById(7);
    }

    @Test
    @DisplayName("Checking that the user is correctly fetched by its id")
    public void shouldFindUserByItsId() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));

        User userToFind = userServiceImpl.findById(1);

        assertEquals("Amale Idrissi", userToFind.getFullname());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when user's id is null")
    public void shouldThrowExceptionWhenUserIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.findById(null));
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when user's id does not exist")
    public void shouldThrowExceptionWhenUserIdDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.findById(7));
    }

    @Test
    @DisplayName("Checking that the user is correctly fetched by its username")
    public void shouldFindUserByItsUsername() {
        when(userRepository.findByUsername("amale")).thenReturn(Optional.of(user));

        User userToFind = userServiceImpl.findByUsername("amale");

        verify(userRepository).findByUsername("amale");
        assertEquals("Amale Idrissi", userToFind.getFullname());
    }

    @Test
    @DisplayName("Checking that UsernameNotFoundException is thrown when username is null")
    public void shouldThrowExceptionWhenUsernameIsNull() {
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.findByUsername(null));
    }

    @Test
    @DisplayName("Checking that UsernameNotFoundException is thrown when username is empty")
    public void shouldThrowExceptionWhenUsernameIsEmpty() {
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.findByUsername(""));
    }

    @Test
    @DisplayName("Checking that users are correctly fetched")
    public void shouldGetAllUsers() {
        when(userRepository.findAll()).thenReturn(users);

        List<User> userList = userServiceImpl.findAllUsers();

        verify(userRepository).findAll();
        assertEquals(users, userList);
    }

    @Test
    @DisplayName("Checking that user, if found by its id, is correctly deleted")
    public void shouldDeleteUserWhenFoundByItsId() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        doNothing().when(userRepository).delete(user);

        userServiceImpl.delete(user.getId());

        verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the user we want to delete is not found")
    public void shouldThrowExceptionWhenUserToDeleteIsNotFound() {
        doThrow(new IllegalArgumentException()).when(userRepository).findById(7);

        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.delete(7));
        verify(userRepository).findById(7);
    }
}
