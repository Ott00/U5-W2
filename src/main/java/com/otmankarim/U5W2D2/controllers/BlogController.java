package com.otmankarim.U5W2D2.controllers;

import com.otmankarim.U5W2D2.entities.Blog;
import com.otmankarim.U5W2D2.payloads.BlogPayload;
import com.otmankarim.U5W2D2.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public Page<Blog> getAllBlogs(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
        return this.blogService.getBlogs(page, size, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    public Blog save(@RequestBody BlogPayload blogPayload) {
        return this.blogService.save(blogPayload);
    }

    @GetMapping("/{id}")
    public Blog findById(@PathVariable int id) {
        return this.blogService.findById(id);
    }

    @PutMapping("/{id}")
    public Blog findByIdAndUpdate(@PathVariable int id, @RequestBody BlogPayload updatedBlog) {
        return this.blogService.findByIdAndUpdate(id, updatedBlog);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    public void findByIdAndDelete(@PathVariable int id) {
        this.blogService.findByIdAndDelete(id);
    }
}
