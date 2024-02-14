package com.otmankarim.U5W2D2.services;

import com.otmankarim.U5W2D2.entities.Author;
import com.otmankarim.U5W2D2.exceptions.BadRequestException;
import com.otmankarim.U5W2D2.exceptions.NotFoundException;
import com.otmankarim.U5W2D2.repositories.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorDAO authorDAO;

    public Page<Author> getAuthors(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return authorDAO.findAll(pageable);
    }

    public Author save(Author newAuthor) {
        authorDAO.findByEmail(newAuthor.getEmail()).ifPresent(author -> {
            throw new BadRequestException("L'email " + author.getEmail() + " è già in uso!");
        });
        newAuthor.setAvatar(createAvatarUrl(newAuthor));
        return authorDAO.save(newAuthor);
    }

    public Author findById(int authorId) {
        return authorDAO.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));
    }

    public Author findByIdAndUpdate(int authorId, Author updatedAuthor) {
        Author found = this.findById(authorId);
        found.setName(updatedAuthor.getName());
        found.setSurname(updatedAuthor.getSurname());
        found.setEmail(updatedAuthor.getEmail());
        found.setDateOfBirth(updatedAuthor.getDateOfBirth());
        found.setAvatar(createAvatarUrl(found));
        return authorDAO.save(found);
    }

    public void findByIdAndDelete(int authorId) {
        Author found = this.findById(authorId);
        authorDAO.delete(found);
    }

    public String createAvatarUrl(Author author) {
        return "https://ui-avatars.com/api/?name=" + author.getName() + "+" + author.getSurname();
    }
}
