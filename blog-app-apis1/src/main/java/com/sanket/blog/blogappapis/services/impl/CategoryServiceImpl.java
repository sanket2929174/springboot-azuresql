package com.sanket.blog.blogappapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.blog.blogappapis.entities.Cateory;
import com.sanket.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.sanket.blog.blogappapis.payloads.CategoryDto;
import com.sanket.blog.blogappapis.repositories.CateoryRepo;
import com.sanket.blog.blogappapis.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CateoryRepo categoryRepo;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public CategoryDto createCategory(CategoryDto categoryDto) {

    Cateory cat = this.modelMapper.map(categoryDto, Cateory.class);
    Cateory addedcat = this.categoryRepo.save(cat);

    return this.modelMapper.map(addedcat, CategoryDto.class);
  }

  @Override
  public CategoryDto updateCategory(CategoryDto categoryDto, Integer categeoryId) {

    Cateory cat = this.categoryRepo.findById(categeoryId)
        .orElseThrow(() -> new ResourceNotFoundException("category", "category Id", categeoryId));
    cat.setCategoryTitle(categoryDto.getCategoryTitle());
    cat.setCategoryDescription(categoryDto.getCategoryDescription());

    Cateory updatedcat = this.categoryRepo.save(cat);

    return this.modelMapper.map(updatedcat, CategoryDto.class);
  }

  @Override
  public void deleteCategory(Integer categoryId) {

    Cateory cat = this.categoryRepo.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
    this.categoryRepo.delete(cat);

  }

  @Override
  public CategoryDto getCategory(Integer cateoryId) {
    Cateory cat = this.categoryRepo.findById(cateoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", cateoryId));

    return this.modelMapper.map(cat, CategoryDto.class);
  }

  @Override
  public List<CategoryDto> getCategories() {

    List<Cateory> cateories = this.categoryRepo.findAll();
    List<CategoryDto> catDtos = cateories.stream().map(cat -> this.modelMapper.map(cat, CategoryDto.class))
        .collect(Collectors.toList());
    return catDtos;
  }

}
