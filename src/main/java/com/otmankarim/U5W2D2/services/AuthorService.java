package com.otmankarim.U5W2D2.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.otmankarim.U5W2D2.entities.Author;
import com.otmankarim.U5W2D2.exceptions.BadRequestException;
import com.otmankarim.U5W2D2.exceptions.NotFoundException;
import com.otmankarim.U5W2D2.payloads.NewAuthorDTO;
import com.otmankarim.U5W2D2.repositories.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AuthorService {
    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Author> getAuthors(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return authorDAO.findAll(pageable);
    }

    public Author save(NewAuthorDTO newAuthorPayload) {
        authorDAO.findByEmail(newAuthorPayload.email()).ifPresent(author -> {
            throw new BadRequestException("L'email " + newAuthorPayload.email() + " è già in uso!");
        });
        Author author = new Author(
                newAuthorPayload.name(),
                newAuthorPayload.surname(),
                newAuthorPayload.email(),
                newAuthorPayload.dateOfBirth()
        );
        author.setAvatar(createAvatarUrl(newAuthorPayload));
        return authorDAO.save(author);
    }

    public Author findById(int authorId) {
        return authorDAO.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));
    }

    public Author findByIdAndUpdate(int authorId, NewAuthorDTO updatedAuthor) {
        Author found = this.findById(authorId);
        found.setName(updatedAuthor.name());
        found.setSurname(updatedAuthor.surname());
        found.setEmail(updatedAuthor.email());
        found.setDateOfBirth(updatedAuthor.dateOfBirth());
        return authorDAO.save(found);
    }

    public void findByIdAndDelete(int authorId) {
        Author found = this.findById(authorId);
        authorDAO.delete(found);
    }

    public String createAvatarUrl(NewAuthorDTO author) {
        return "https://ui-avatars.com/api/?name=" + author.name() + "+" + author.surname();
    }

    public String uploadImageAndGetUrl(MultipartFile cover, int authorId) throws IOException {
        String urlCover = (String) cloudinaryUploader.uploader().upload(cover.getBytes(), ObjectUtils.emptyMap()).get("url");
        Author found = findById(authorId);
        found.setAvatar(urlCover);
        authorDAO.save(found);
        return urlCover;
    }
}
