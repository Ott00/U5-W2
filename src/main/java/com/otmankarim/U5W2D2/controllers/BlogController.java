package com.otmankarim.U5W2D2.controllers;

import com.otmankarim.U5W2D2.entities.Blog;
import com.otmankarim.U5W2D2.exceptions.BadRequestException;
import com.otmankarim.U5W2D2.payloads.NewBlogDTO;
import com.otmankarim.U5W2D2.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public Blog save(@RequestBody @Validated NewBlogDTO newBlogDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.blogService.save(newBlogDTO);
    }

    //Aggiorniamo l'immagine con questo endpoint
    @PatchMapping("/{id}/uploadCover")
    @ResponseStatus(HttpStatus.OK) // Status Code 200
    public String uploadCover(@PathVariable int id, @RequestParam("cover") MultipartFile image) throws IOException {
        return this.blogService.uploadImageAndGetUrl(image, id);
    }

    @GetMapping("/{id}")
    public Blog findById(@PathVariable int id) {
        return this.blogService.findById(id);
    }

    @PutMapping("/{id}")
    public Blog findByIdAndUpdate(@PathVariable int id, @RequestBody NewBlogDTO updatedBlog) {
        return this.blogService.findByIdAndUpdate(id, updatedBlog);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    public void findByIdAndDelete(@PathVariable int id) {
        this.blogService.findByIdAndDelete(id);
    }
}
