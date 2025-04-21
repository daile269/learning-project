package com.learning.core_service.service;

import com.learning.core_service.entity.Author;

import java.util.List;

public interface AuthorService {
    Author findAuthorById(Long id);

    Author createAuthor(Author author);

    Author updateAuthor(Author author,Long id);

    List<Author> getAuthors();

    void deleteAuthor(Long id);
    
}
