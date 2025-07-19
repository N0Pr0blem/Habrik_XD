package com.example.user_service.controller;

import com.example.user_service.dto.UserFilter;
import com.example.user_service.dto.UserRequestDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.dto.UserUpdateDto;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.UserEntity;
import com.example.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final MessageSource messageSource;

    @PostMapping(consumes = "application/json")
    @Operation(description = "Add new user")
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserRequestDto userRequestDto,
                                                   UriComponentsBuilder uriComponentsBuilder
    ) {
        UserResponseDto userResponseDto = userMapper.toDto(userService.addUser(userRequestDto));
        return ResponseEntity
                .created(uriComponentsBuilder
                        .pathSegment("{userId}")
                        .build(Map.of("userId",userResponseDto.getId())))
                .body(userResponseDto);
    }

    @GetMapping("/{userId}")
    @Operation(description = "Get user by id")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name = "userId") Long userId) {
        UserResponseDto userResponseDto = userMapper.toDto(userService.getUserById(userId));
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping()
    @Operation(description = "Get all users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        UserFilter filter = new UserFilter(username,email);
        Pageable pageable = PageRequest.of(page,size, pageSort(sort));
        List<UserResponseDto> users = userMapper.toDtos(userService.getAllUsers(filter, pageable));

        return ResponseEntity.ok(users);
    }

    private Sort pageSort(String[] sort) {
        if(sort.length>=2){
            String property = sort[0];
            String direction = sort[1];

            return Sort.by(Sort.Direction.fromString(direction), property);
        }

        return Sort.unsorted();
    }

    @DeleteMapping("/{userId}")
    @Operation(description = "Delete user by id")
    public ResponseEntity<String> deleteUserById(@PathVariable(name = "userId") Long userId, Locale locale) {
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(messageSource.getMessage("message.user.successfully_delete",new Object[]{userId},locale));
    }

    @PatchMapping("/{userId}")
    @Operation(description = "Delete user by id")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable(name = "userId") Long userId,
                                                 @Valid @RequestBody UserUpdateDto userUpdateDto,
                                                 Locale locale) {
        UserEntity userEntity = userService.updateUser(userId,userUpdateDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.toDto(userEntity));
    }
}
