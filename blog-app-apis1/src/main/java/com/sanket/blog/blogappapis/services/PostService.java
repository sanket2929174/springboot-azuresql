package com.sanket.blog.blogappapis.services;

import java.util.List;

import com.sanket.blog.blogappapis.payloads.PostDto;
import com.sanket.blog.blogappapis.payloads.PostResponse;

public interface PostService {
  // creat
  PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

  // update
  PostDto updatePost(PostDto postDto, Integer postId);

  // delete
  void deletePost(Integer postId);

  // get all posts
  PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

  // get single post
  PostDto getPostById(Integer postId);

  // get all post by category
  List<PostDto> getPostByCategory(Integer categoryId);

  // get all posts by user
  List<PostDto> getPostByUser(Integer userId);

  // search posts
  List<PostDto> searchPosts(String keyword);
}
