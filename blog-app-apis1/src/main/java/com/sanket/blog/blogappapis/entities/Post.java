package com.sanket.blog.blogappapis.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer postId;
  @Column(name = "post_title", length = 100, nullable = false)
  private String title;
  @Column(length = 10000)
  private String content;
  private String inageName;
  private Date addedData;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Cateory category;
  @ManyToOne
  private User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private Set<Comment> comments = new HashSet<>();
}
