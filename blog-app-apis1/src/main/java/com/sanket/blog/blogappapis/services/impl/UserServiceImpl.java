package com.sanket.blog.blogappapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanket.blog.blogappapis.config.AppConstants;
import com.sanket.blog.blogappapis.entities.Role;
import com.sanket.blog.blogappapis.entities.User;
import com.sanket.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.sanket.blog.blogappapis.payloads.UserDto;
import com.sanket.blog.blogappapis.repositories.RoleRepo;
import com.sanket.blog.blogappapis.repositories.UserRepo;
import com.sanket.blog.blogappapis.services.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleRepo roleRepo;

  @Override
  public UserDto createUser(UserDto userDto) {

    User user = this.dtoToUser(userDto);
    User savedUser = this.userRepo.save(user);

    return this.userToDto(savedUser);
  }

  @Override
  public UserDto updateUser(UserDto userDto, Integer userId) {

    User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassworrd(userDto.getPassworrd());
    user.setAbout(userDto.getAbout());

    User updateduser = this.userRepo.save(user);
    UserDto userDto1 = this.userToDto(updateduser);

    return userDto1;
  }

  @Override
  public UserDto getUserById(Integer userId) {
    User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    return this.userToDto(user);
  }

  @Override
  public List<UserDto> getAllUsers() {
    List<User> users = this.userRepo.findAll();
    List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
    return userDtos;
  }

  @Override
  public void deleteUser(Integer userId) {
    User user = this.userRepo.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
    this.userRepo.delete(user);
  }

  private User dtoToUser(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setAbout(userDto.getAbout());
    user.setPassworrd(userDto.getPassworrd());

    return user;
  }

  private UserDto userToDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setEmail(user.getEmail());
    userDto.setAbout(user.getAbout());
    userDto.setPassworrd(user.getPassworrd());

    return userDto;
  }

  @Override
  public UserDto registerNewUser(UserDto userDto) {
    User user = this.modelMapper.map(userDto, User.class);
    // encoded pass
    user.setPassworrd(this.passwordEncoder.encode(user.getPassworrd()));
    // roles
    Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
    user.getRoles().add(role);
    User newUser = this.userRepo.save(user);
    return this.modelMapper.map(newUser, UserDto.class);
  }

}
