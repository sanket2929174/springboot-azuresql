package com.sanket.blog.blogappapis.entities;

// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Role {

  @Id
  private int id;
  private String name;
}
