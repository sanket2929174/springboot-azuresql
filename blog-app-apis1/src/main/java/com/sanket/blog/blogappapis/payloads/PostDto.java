package com.sanket.blog.blogappapis.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

  private Integer postId;
  private String title;
  private String content;
  private String inageName;
  private Date addedData;
  private CategoryDto category;
  private UserDto user;

  private Set<CommentDto> comments = new HashSet<>();
}
