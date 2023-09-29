package com.sanket.blog.blogappapis.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.blog.blogappapis.payloads.ApiResponse;
import com.sanket.blog.blogappapis.payloads.CategoryDto;
import com.sanket.blog.blogappapis.services.CategoryService;

// import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CateoryController {

  @Autowired
  private CategoryService categoryService;

  // POST-create categorie
  @PostMapping("/")
  public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
    CategoryDto createUserDto = this.categoryService.createCategory(categoryDto);
    return new ResponseEntity<CategoryDto>(createUserDto, HttpStatus.CREATED);
  }

  // put-create user
  @PutMapping("/{catId}")
  public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
      @PathVariable Integer catId) {
    CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, catId);
    return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
  }

  // delete category
  @DeleteMapping("/{catId}")
  public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
    this.categoryService.deleteCategory(catId);
    return new ResponseEntity<ApiResponse>(new ApiResponse("category deleated successfully", true), HttpStatus.OK);
  }

  // get category by id
  @GetMapping("/{catId}")
  public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
    CategoryDto categoryDto = this.categoryService.getCategory(catId);
    return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
  }

  // get all categories
  @GetMapping("/")
  public ResponseEntity<List<CategoryDto>> getCategories() {
    List<CategoryDto> categeory = this.categoryService.getCategories();
    return ResponseEntity.ok(categeory);
  }

}
