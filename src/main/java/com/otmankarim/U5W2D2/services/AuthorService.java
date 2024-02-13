package com.otmankarim.U5W2D2.services;

import com.otmankarim.U5W2D2.entities.Author;
import com.otmankarim.U5W2D2.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AuthorService {
    private List<Author> authors = new ArrayList<>();

    public List<Author> getUsers() {
        return this.authors;
    }

    public Author save(Author author) {
        Random random = new Random();
        author.setId(random.nextInt(1, 10000));
        this.authors.add(author);
        return author;
    }

    public Author findById(int id) {
        Author found = null;
        for (Author author : this.authors) {
            if (author.getId() == id) {
                found = author;
            }
        }
        if (found == null) throw new NotFoundException(id);
        else return found;
    }

    public Author findByIdAndUpdate(int id, Author updatedAuthor) {
        Author found = null;
        for (Author author : this.authors) {
            if (author.getId() == id) {
                found = author;
                found.setName(updatedAuthor.getName());
                found.setSurname(updatedAuthor.getSurname());
                found.setEmail(updatedAuthor.getEmail());
                found.setDateOfBirth(updatedAuthor.getDateOfBirth());
                found.setAvatar(updatedAuthor.getAvatar());
            }
        }
        if (found == null) throw new NotFoundException(id);
        else return found;
    }

    public boolean findByIdAndDelete(int id) {
        return this.authors.removeIf(current -> current.getId() == id);
    }
}
