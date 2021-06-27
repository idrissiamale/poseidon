package com.nnk.springboot.services.user;

import com.nnk.springboot.domains.MyUserDetails;
import com.nnk.springboot.domains.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private MyUserDetails myUserDetails;
    private User user;


    @BeforeEach
    public void setUp() {
        userDetailsServiceImpl = new UserDetailsServiceImpl(userRepository);
        user = new User(1, "johndoe", "Password1$", "John Doe", "USER");
        myUserDetails = new MyUserDetails(user);
    }

    @Test
    @DisplayName("Checking that the username is correctly load when user exists in the database")
    public void shouldLoadUsernameWhenUserExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUsername());

        verify(userRepository).findByUsername(user.getUsername());
        assertEquals(myUserDetails.getUser().getUsername(), userDetails.getUsername());
    }

    @Test
    @DisplayName("Checking that UsernameNotFoundException is thrown when the username is not found")
    public void shouldThrowExceptionWhenUsernameNotFound() {
        String username = "lili";
        when(userRepository.findByUsername(username)).thenThrow(new UsernameNotFoundException(username + " Not Found"));

        assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceImpl.loadUserByUsername(username));
        verify(userRepository).findByUsername(username);
    }
}