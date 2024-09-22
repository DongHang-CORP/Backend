package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.dto.UserResDto;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<UserResDto> getUserById(@PathVariable Long id) {
        UserResDto userResDto = userService.getUserById(id);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody UserReqDto userReqDto) {
        Long userId = userService.createUser(userReqDto);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResDto> updateUser(@PathVariable Long id, @RequestBody UserReqDto user) {
        UserResDto userResDto = userService.updateUser(id, user);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
