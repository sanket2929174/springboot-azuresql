package com.sanket.blog.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.blog.blogappapis.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
