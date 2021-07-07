package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.User;
import com.nnk.springboot.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;
    private User user;
    private User userUpdated;
    private User user2;
    private List<User> users;

    @Mock
    UserService userService;

    @BeforeEach
    public void setup() {
        UserController userController = new UserController(userService);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user = new User(1, "amale", passwordEncoder.encode("Password1@"), "Amale Idrissi", "ADMIN");
        userUpdated = new User(1, "amale33", passwordEncoder.encode("Password1@"), "Amale Idrissi", "ADMIN");
        user2 = new User(2, "johndoe", passwordEncoder.encode("Password1$"), "John Doe", "USER");
        users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("Checking that the user management page is returned when the user makes a GET request to the /user/list URL")
    public void shouldReturnUserListView() throws Exception {
        when(userService.findAllUsers()).thenReturn(users);

        this.mockMvc.perform(get("/user/list").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", users))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("username", is("amale")),
                                hasProperty("password", is(user.getPassword())),
                                hasProperty("fullname", is("Amale Idrissi")),
                                hasProperty("role", is("ADMIN"))
                        )
                )))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("username", is("johndoe")),
                                hasProperty("password", is(user2.getPassword())),
                                hasProperty("fullname", is("John Doe")),
                                hasProperty("role", is("USER"))
                        )
                )));

        verify(userService).findAllUsers();
    }

    @Test
    @DisplayName("Checking that the user/add page is returned when the user makes a GET request to the /user/add URL")
    public void shouldReturnUserAddPageView() throws Exception {
        this.mockMvc.perform(get("/user/add").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @DisplayName("Checking that the user is redirected to the user management page when its data are correctly saved")
    public void shouldReturnUserManagementPageViewWhenUserDataAreCorrectlySaved() throws Exception {
        when(userService.save(any(User.class))).thenReturn(user);

        this.mockMvc.perform(post("/user/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "amale")
                .param("password", user.getPassword())
                .param("fullname", "Amale Idrissi")
                .param("role", "ADMIN")
                .sessionAttr("user", user)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(userService).save(any(User.class));
        assertEquals("Amale Idrissi", user.getFullname());
    }

    @Test
    @DisplayName("Checking that the 'Add New User' form (user/add page) is returned with error message when there are errors on the form fields")
    public void shouldReturnAddNewUserFormViewWhenErrorsOnPasswordAndUsernameFields() throws Exception {
        String password = "pass";
        String username = " ";

        this.mockMvc.perform(post("/user/validate")
                .param("username", username)
                .param("password", password)
                .param("fullname", "Amale Idrissi")
                .param("role", "ADMIN")
                .sessionAttr("user", user)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(model().attribute("user", hasProperty("username", is(username))))
                .andExpect(model().attribute("user", hasProperty("password", is(password))))
                .andDo(print());

        verifyZeroInteractions(userService);
    }

    @Test
    @DisplayName("Checking that the 'Update User' form (user/update page) is returned when the user makes a GET request to the /user/update/{id} URL")
    public void shouldReturnUpdateUserFormView() throws Exception {
        when(userService.findById(1)).thenReturn(user);

        this.mockMvc.perform(get("/user/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", user))
                .andDo(print());

        verify(userService).findById(1);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the user management page when its data are correctly updated")
    public void shouldReturnUserManagementPageViewWhenUserDataAreCorrectlyUpdated() throws Exception {
        when(userService.update(anyInt(), any(User.class))).thenReturn(userUpdated);

        this.mockMvc.perform(post("/user/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", userUpdated.getUsername())
                .param("password", userUpdated.getPassword())
                .param("fullname", "Amale Idrissi")
                .param("role", "ADMIN")
                .sessionAttr("user", userUpdated)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(userService).update(anyInt(), any(User.class));
        assertEquals("amale33", userUpdated.getUsername());
    }

    @Test
    @DisplayName("Checking that the 'Update User' form (user/update page) is returned with error message when there are errors on the form fields")
    public void shouldReturnUpdateUserFormViewWhenErrorsOnPasswordAndUsernameFields() throws Exception {
        String password = "pass";
        String username = " ";

        this.mockMvc.perform(post("/user/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", username)
                .param("password", password)
                .param("fullname", "Amale Idrissi")
                .param("role", "ADMIN")
                .sessionAttr("user", userUpdated)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(model().attribute("user", hasProperty("username", is(username))))
                .andExpect(model().attribute("user", hasProperty("password", is(password))))
                .andDo(print());

        verifyZeroInteractions(userService);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the user management page when its data are correctly deleted")
    public void shouldReturnUserManagementPageViewWhenUserDataAreCorrectlyDeleted() throws Exception {
        doNothing().when(userService).delete(1);

        this.mockMvc.perform(get("/user/delete/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().attribute("user", nullValue()))
                .andDo(print());

        verify(userService).delete(1);
    }
}
