package com.otmankarim.U5W2D2.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.otmankarim.U5W2D2.entities.Blog;
import com.otmankarim.U5W2D2.exceptions.BadRequestException;
import com.otmankarim.U5W2D2.exceptions.NotFoundException;
import com.otmankarim.U5W2D2.payloads.NewBlogDTO;
import com.otmankarim.U5W2D2.repositories.BlogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BlogService {
    @Autowired
    private BlogDAO blogDAO;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Blog> getBlogs(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return blogDAO.findAll(pageable);
    }

    public Blog save(NewBlogDTO newBlogPayload) {
        //LOGICA PAYLOAD
        blogDAO.findByTitle(newBlogPayload.title()).ifPresent(blog -> {
            throw new BadRequestException("Il blogpost '" + newBlogPayload.title() + "' esiste già!");
        });
        Blog blog = new Blog(
                newBlogPayload.category(),
                newBlogPayload.title(),
                newBlogPayload.content(),
                newBlogPayload.timeOfLecture()
        );
        blog.setAuthor(authorService.findById(newBlogPayload.authorId()));
        return blogDAO.save(blog);
    }

    public Blog findById(int blogId) {
        return blogDAO.findById(blogId).orElseThrow(() -> new NotFoundException(blogId));
    }

    public Blog findByIdAndUpdate(int blogId, NewBlogDTO updatedBlog) {
        Blog found = this.findById(blogId);
        found.setCategory(updatedBlog.category());
        found.setTitle(updatedBlog.title());
        found.setContent(updatedBlog.content());
        found.setTimeOfLecture(updatedBlog.timeOfLecture());
        found.setAuthor(authorService.findById(updatedBlog.authorId()));
        return blogDAO.save(found);
    }

    public void findByIdAndDelete(int blogId) {
        Blog found = this.findById(blogId);
        blogDAO.delete(found);
    }

    public String uploadImageAndGetUrl(MultipartFile cover, int blogId) throws IOException {
        String urlCover = (String) cloudinaryUploader.uploader().upload(cover.getBytes(), ObjectUtils.emptyMap()).get("url");
        Blog found = findById(blogId);
        found.setCover(urlCover);
        blogDAO.save(found);
        return urlCover;
    }
}
