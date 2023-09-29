package com.sanket.blog.blogappapis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException  extends RuntimeException{

  String resourceName;
  String fieldName;
  long fieldValue;
  
  public ResourceNotFoundException(String resourceNmae,String fieldName,long fieldValue){
    super(String.format("%s not found with %s :%s",resourceNmae,fieldName,fieldValue));
    this.resourceName=resourceNmae;
    this.fieldName=fieldName;
    this.fieldValue=fieldValue;

  }
}
