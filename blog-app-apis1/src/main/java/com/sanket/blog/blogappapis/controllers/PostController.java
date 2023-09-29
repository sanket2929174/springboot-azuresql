package com.sanket.blog.blogappapis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sanket.blog.blogappapis.config.AppConstants;
import com.sanket.blog.blogappapis.payloads.ApiResponse;
import com.sanket.blog.blogappapis.payloads.PostDto;
import com.sanket.blog.blogappapis.payloads.PostResponse;
import com.sanket.blog.blogappapis.services.FileService;
import com.sanket.blog.blogappapis.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

  @Autowired
  private PostService postService;

  @Autowired
  private FileService fileService;

  @Value("${project.image}")
  private String path;

  // create
  @PostMapping("/user/{userId}/category/{categoryId}/posts")
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
      @PathVariable Integer userId,
      @PathVariable Integer categoryId) {
    PostDto createPost = this.postService.createPost(postDto, userId,
        categoryId);
    return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
  }

  // get by user id
  @GetMapping("/user/{userId}/posts")
  public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
    List<PostDto> posts = this.postService.getPostByUser(userId);
    return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
  }

  // get by category
  @GetMapping("/category/{categoryId}/posts")
  public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
    List<PostDto> posts = this.postService.getPostByCategory(categoryId);
    return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
  }

  // get all posts
  @GetMapping("/posts")
  public ResponseEntity<PostResponse> getAllPost(
      @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
      @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
    PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize,
        sortBy, sortDir);
    return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
  }

  // get post by id
  @GetMapping("/posts/{postId}")
  public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
    PostDto postDtos = this.postService.getPostById(postId);
    return new ResponseEntity<PostDto>(postDtos, HttpStatus.OK);
  }

  // delete post
  @DeleteMapping("/posts/{postId}")
  public ApiResponse deletePost(@PathVariable Integer postId) {
    this.postService.deletePost(postId);
    return new ApiResponse("post is successfully deleated !!", true);
  }

  // update post
  @PutMapping("/posts/{postId}")
  public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
      @PathVariable Integer postId) {
    PostDto updatedPost = this.postService.updatePost(postDto, postId);
    return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
  }

  // search
  @GetMapping("/posts/search/{keywords}")
  public ResponseEntity<List<PostDto>> searchPostByTitle(
      @PathVariable("keywords") String keywords) {
    List<PostDto> result = this.postService.searchPosts(keywords);
    return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
  }

  // // post image upload
  @PostMapping("/post/image/upload/{postId}")
  public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
      @PathVariable Integer postId) throws IOException {
    PostDto postDto = this.postService.getPostById(postId);
    String fileName = this.fileService.uploadImage(path, image);
    postDto.setInageName(fileName);
    PostDto updatePost = this.postService.updatePost(postDto, postId);
    return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
  }

  // method to serve file
  @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
  public void downloadImage(@PathVariable("imagename") String imageName,
      HttpServletResponse response)
      throws IOException {
    InputStream rsource = this.fileService.getResourve(path, imageName);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(rsource, response.getOutputStream());
  }
}
