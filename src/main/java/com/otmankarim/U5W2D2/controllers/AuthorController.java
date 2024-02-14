package com.otmankarim.U5W2D2.controllers;

import com.otmankarim.U5W2D2.entities.Author;
import com.otmankarim.U5W2D2.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public Page<Author> getAllAuthors(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String orderBy) {
        return this.authorService.getAuthors(page, size, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    public Author save(@RequestBody Author author) {
        return this.authorService.save(author);
    }

    @GetMapping("/{id}")
    public Author findById(@PathVariable int id) {
        return this.authorService.findById(id);
    }

    @PutMapping("/{id}")
    public Author findByIdAndUpdate(@PathVariable int id, @RequestBody Author updatedAuthor) {
        return this.authorService.findByIdAndUpdate(id, updatedAuthor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    public void findByIdAndDelete(@PathVariable int id) {
        this.authorService.findByIdAndDelete(id);
    }
}
