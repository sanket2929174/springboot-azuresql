package com.sanket.blog.blogappapis.services;

import com.sanket.blog.blogappapis.payloads.CategoryDto;
import java.util.List;

public interface CategoryService {

  // create
  CategoryDto createCategory(CategoryDto categoryDto);

  // update
  CategoryDto updateCategory(CategoryDto categoryDto, Integer categeoryId);

  // delete
  public void deleteCategory(Integer categoryId);

  // get
  CategoryDto getCategory(Integer categoryId);

  // getAll
  List<CategoryDto> getCategories();

}
