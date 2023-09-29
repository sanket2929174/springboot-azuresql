package com.sanket.blog.blogappapis.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.blog.blogappapis.entities.User;
import com.sanket.blog.blogappapis.entities.Cateory;
import com.sanket.blog.blogappapis.entities.Post;

public interface PostRepo extends JpaRepository<Post, Integer> {

  // for gettiong all post of users this is custom finder method
  List<Post> findByUser(User user);

  List<Post> findByCategory(Cateory category);

  List<Post> findByTitleContaining(String title);
}
