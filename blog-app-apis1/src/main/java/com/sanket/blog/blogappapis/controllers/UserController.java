package com.sanket.blog.blogappapis.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.blog.blogappapis.payloads.ApiResponse;
import com.sanket.blog.blogappapis.payloads.UserDto;
import com.sanket.blog.blogappapis.services.UserService;

// import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  // POST-create user
  @PostMapping("/")
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
    UserDto createUserDto = this.userService.createUser(userDto);
    return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
  }

  // for updating the user
  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updatedUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {
    UserDto updatUser = this.userService.updateUser(userDto, uid);
    return ResponseEntity.ok(updatUser);
  }

  // for deleting user
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
    this.userService.deleteUser(uid);
    return new ResponseEntity<ApiResponse>(new ApiResponse("user deleated successfully", true), HttpStatus.OK);
  }

  // to get user by id
  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getSingleuser(@PathVariable("userId") Integer uid) {
    return ResponseEntity.ok(this.userService.getUserById(uid));
  }

  // to get all user
  @GetMapping("/")
  public ResponseEntity<List<UserDto>> getAllUser() {
    return ResponseEntity.ok(this.userService.getAllUsers());
  }

}
