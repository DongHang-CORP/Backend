package org.example.food.controller;

import org.example.food.user.dto.UserReqDto;
import org.example.food.user.dto.UserResDto;
import org.example.food.user.service.UserService;
import org.example.food.testbase.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UserControllerTest extends IntegrationTest {

    @MockBean
    private UserService userService;

//    @Test
//    void findUserByEmail() throws Exception {
//        // Given
//        LoginDto loginDto = new LoginDto();
//        loginDto.setEmail("test@example.com");
//        UserResDto userResDto = new UserResDto();
//        userResDto.setEmail("test@example.com");
//
//        // When
//        when(userService.findUserByEmail("test@example.com")).thenReturn(userResDto);
//
//        // Then
//        mockMvc.perform(post("/api/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("test@example.com"));
//    }

    @Test
    void getUserById() throws Exception {
        // Given
        UserResDto userResDto = new UserResDto();
        userResDto.setEmail("user@example.com");
        userResDto.setUsername("user123");

        // When
        when(userService.getUserById(1L)).thenReturn(userResDto);

        // Then
        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.username").value("user123"));
    }

    @Test
    void createUser() throws Exception {
        // Given
        UserReqDto userReqDto = new UserReqDto();
        userReqDto.setEmail("newuser@example.com");
        userReqDto.setUsername("newuser");

        // When
        when(userService.createUser(any(UserReqDto.class))).thenReturn(1L);

        // Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userReqDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void updateUser() throws Exception {
        // Given
        UserReqDto userReqDto = new UserReqDto();
        userReqDto.setEmail("updateduser@example.com");
        userReqDto.setUsername("updateduser");

        UserResDto userResDto = new UserResDto();
        userResDto.setEmail("updateduser@example.com");
        userResDto.setUsername("updateduser");

        // When
        when(userService.updateUser(any(Long.class), any(UserReqDto.class))).thenReturn(userResDto);

        // Then
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updateduser@example.com"))
                .andExpect(jsonPath("$.username").value("updateduser"));
    }

    @Test
    void deleteUser() throws Exception {
        // Given - When - Then
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
        Mockito.verify(userService).deleteUser(1L);
    }
}