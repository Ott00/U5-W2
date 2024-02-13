package com.otmankarim.U5W2D2.services;

import com.otmankarim.U5W2D2.entities.Blog;
import com.otmankarim.U5W2D2.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
public class BlogService {
    private List<Blog> blogs = new ArrayList<>();

    public List<Blog> getUsers() {
        return this.blogs;
    }

    public Blog save(Blog blog) {
        Random random = new Random();
        blog.setId(random.nextInt(1, 10000));
        this.blogs.add(blog);
        return blog;
    }

    public Blog findById(int id) {
        Blog found = null;
        for (Blog blog : this.blogs) {
            if (blog.getId() == id) {
                found = blog;
            }
        }
        if (found == null) throw new NotFoundException(id);
        else return found;
    }

    public Blog findByIdAndUpdate(int id, Blog updatedBlog) {
        Blog found = null;
        for (Blog blog : this.blogs) {
            if (blog.getId() == id) {
                found = blog;
                found.setCategory(updatedBlog.getCategory());
                found.setTitle(updatedBlog.getTitle());
                found.setCover(updatedBlog.getCover());
                found.setContent(updatedBlog.getContent());
                found.setTimeOfLecture(updatedBlog.getTimeOfLecture());
            }
        }
        if (found == null) throw new NotFoundException(id);
        else return found;
    }

    public void findByIdAndDelete(int id) {
        Iterator<Blog> iterator = this.blogs.iterator();
        while (iterator.hasNext()) {
            Blog current = iterator.next();
            if (current.getId() == id) {
                iterator.remove();
            }
        }
    }
}
