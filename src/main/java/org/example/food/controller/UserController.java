package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.user.dto.LoginDto;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.dto.UserResDto;
import org.example.food.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResDto> loginOrSignUp(@RequestBody LoginDto loginDto) {
        UserResDto userResDto = userService.signUpOrLogin(loginDto);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResDto> getUserById(@PathVariable Long id) {
        UserResDto userResDto = userService.getUserById(id);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
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
