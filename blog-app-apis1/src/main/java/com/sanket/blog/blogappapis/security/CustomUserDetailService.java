package com.sanket.blog.blogappapis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sanket.blog.blogappapis.entities.User;
import com.sanket.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.sanket.blog.blogappapis.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  private UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // loding user from database from username
    User user = this.userRepo.findByEmail(username)
        .orElseThrow(() -> new ResourceNotFoundException("user", "email:" + username,
            0));
    return user;
  }

}
