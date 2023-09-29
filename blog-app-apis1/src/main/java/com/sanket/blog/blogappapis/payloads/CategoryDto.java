package com.sanket.blog.blogappapis.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

  private Integer categoryId;
  @NotBlank
  @Size(min = 5, message = "minimun size of title should be 5")
  private String categoryTitle;

  @NotBlank
  @Size(min = 10, message = "minimun size of title should be 10")
  private String categoryDescription;

}
