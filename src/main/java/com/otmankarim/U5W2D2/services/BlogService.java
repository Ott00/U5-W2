package com.otmankarim.U5W2D2.services;

import com.otmankarim.U5W2D2.entities.Blog;
import com.otmankarim.U5W2D2.exceptions.BadRequestException;
import com.otmankarim.U5W2D2.exceptions.NotFoundException;
import com.otmankarim.U5W2D2.payloads.BlogPayload;
import com.otmankarim.U5W2D2.repositories.BlogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BlogService {
    @Autowired
    private BlogDAO blogDAO;
    @Autowired
    private AuthorService authorService;

    public Page<Blog> getBlogs(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return blogDAO.findAll(pageable);
    }

    public Blog save(BlogPayload blogPayload) {
        //LOGICA PAYLOAD
        blogDAO.findByTitle(blogPayload.getTitle()).ifPresent(blog -> {
            System.out.println(blog);
            throw new BadRequestException("Il blogpost '" + blogPayload.getTitle() + "' esiste già!");
        });
        Blog blog = new Blog(
                blogPayload.getCategory(),
                blogPayload.getTitle(),
                blogPayload.getCover(),
                blogPayload.getContent(),
                blogPayload.getTimeOfLecture()
        );
        blog.setAuthor(authorService.findById(blogPayload.getAuthorId()));
        return blogDAO.save(blog);
    }

    public Blog findById(int blogId) {
        return blogDAO.findById(blogId).orElseThrow(() -> new NotFoundException(blogId));
    }

    public Blog findByIdAndUpdate(int blogId, BlogPayload updatedBlog) {
        Blog found = this.findById(blogId);
        found.setCategory(updatedBlog.getCategory());
        found.setTitle(updatedBlog.getTitle());
        found.setCover(updatedBlog.getCover());
        found.setContent(updatedBlog.getContent());
        found.setTimeOfLecture(updatedBlog.getTimeOfLecture());
        found.setAuthor(authorService.findById(updatedBlog.getAuthorId()));
        return blogDAO.save(found);
    }

    public void findByIdAndDelete(int blogId) {
        Blog found = this.findById(blogId);
        blogDAO.delete(found);
    }
}
