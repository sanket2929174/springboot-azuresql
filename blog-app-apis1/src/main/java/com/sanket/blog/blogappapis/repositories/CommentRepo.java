package com.sanket.blog.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.blog.blogappapis.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
