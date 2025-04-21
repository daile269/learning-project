package com.learning.core_service.service.impl;

import com.learning.core_service.entity.Author;
import com.learning.core_service.exception.ResourceNotFoundException;
import com.learning.core_service.repository.AuthorRepository;
import com.learning.core_service.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IAuthorService implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Author is not exist"));
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Author author, Long id) {
        author.setId(id);
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
