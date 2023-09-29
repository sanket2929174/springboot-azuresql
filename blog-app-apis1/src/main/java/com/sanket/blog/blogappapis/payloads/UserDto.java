package com.sanket.blog.blogappapis.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto { // this is using for data transfer

  private Integer id;

  @NotEmpty
  @Size(min = 4, message = "username must be minimum 4character!!")
  private String name;

  @Email(message = "email address not valid")
  @NotEmpty
  private String email;

  @NotEmpty
  @Size(min = 4, max = 10, message = "pasword must be minimum 3 char and max 10 characters !!")
  // @Pattern(regx="")
  private String passworrd;

  @NotEmpty
  private String about;

  private Set<RoleDto> roles = new HashSet<>();

  // @JsonIgnore
  // public String getPassworrd() {
  // return this.passworrd;
  // }
}
