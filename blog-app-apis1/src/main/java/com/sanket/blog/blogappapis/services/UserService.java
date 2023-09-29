package com.sanket.blog.blogappapis.services;

import java.util.List;

import com.sanket.blog.blogappapis.payloads.UserDto;

public interface UserService {
   UserDto registerNewUser(UserDto user);

   UserDto createUser(UserDto user);

   UserDto updateUser(UserDto user, Integer userId);

   UserDto getUserById(Integer userId);

   List<UserDto> getAllUsers();

   void deleteUser(Integer userId);

}
