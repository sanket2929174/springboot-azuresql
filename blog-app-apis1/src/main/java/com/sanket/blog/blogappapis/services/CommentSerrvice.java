package com.sanket.blog.blogappapis.services;

import com.sanket.blog.blogappapis.payloads.CommentDto;

public interface CommentSerrvice {

  CommentDto createComment(CommentDto commentDto, Integer postId);

  void deleteComment(Integer commentId);
}
