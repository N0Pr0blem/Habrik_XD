package com.example.user_service.controller;

import com.example.user_service.dto.UserRequestDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.exception.UserAddException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.UserEntity;
import com.example.user_service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private UserRequestDto userRequestDto;
    private UserEntity userEntity;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        userEntity = mock(UserEntity.class);
        userResponseDto = mock(UserResponseDto.class);
    }

    @Test
    void saveValidUserExpectedIsCreated() throws Exception {
        userRequestDto = UserRequestDto.builder()
                .username("test")
                .password("test")
                .email("test@gmail.com")
                .role("USER")
                .build();
        when(userService.addUser(userRequestDto)).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userResponseDto);

        String userRequestDtoJson = new ObjectMapper().writeValueAsString(userRequestDto);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void saveNoValidUserExpectedBadRequest() throws Exception {
        userRequestDto = UserRequestDto.builder()
                .username("test")
                .password("test")
                .build();
        when(userService.addUser(userRequestDto)).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userResponseDto);

        String userRequestDtoJson = new ObjectMapper().writeValueAsString(userRequestDto);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestDtoJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    void saveValidUserWithSameUsernameExpectedUserAddException() throws Exception {
        userRequestDto = UserRequestDto.builder()
                .username("test")
                .password("test")
                .email("test@gmail.com")
                .role("USER")
                .build();

        when(userService.addUser(any(UserRequestDto.class)))
                .thenThrow(new UserAddException("User with username already exists"));

        String userRequestDtoJson = new ObjectMapper().writeValueAsString(userRequestDto);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestDtoJson))
                .andExpect(status().isBadRequest());
    }

}