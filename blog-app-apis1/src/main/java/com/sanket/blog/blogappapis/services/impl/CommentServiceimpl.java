package com.sanket.blog.blogappapis.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.blog.blogappapis.entities.Comment;
import com.sanket.blog.blogappapis.entities.Post;
import com.sanket.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.sanket.blog.blogappapis.payloads.CommentDto;
import com.sanket.blog.blogappapis.repositories.CommentRepo;
import com.sanket.blog.blogappapis.repositories.PostRepo;
import com.sanket.blog.blogappapis.services.CommentSerrvice;

@Service
public class CommentServiceimpl implements CommentSerrvice {

  @Autowired
  private PostRepo postRepo;
  @Autowired
  private CommentRepo commentRepo;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public CommentDto createComment(CommentDto commentDto, Integer postId) {
    Post post = this.postRepo.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
    Comment comment = this.modelMapper.map(commentDto, Comment.class);
    comment.setPost(post);
    Comment savedComment = this.commentRepo.save(comment);

    return this.modelMapper.map(savedComment, CommentDto.class);
  }

  @Override
  public void deleteComment(Integer commentId) {
    Comment com = this.commentRepo.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("comment", "comment id", commentId));
    this.commentRepo.delete(com);
  }

}
