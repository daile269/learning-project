package com.learning.core_service.service;

import com.learning.core_service.dto.AuthorDTO;
import com.learning.core_service.entity.Author;

import java.util.List;

public interface AuthorService {
    AuthorDTO findAuthorById(Long id);

    AuthorDTO createAuthor(AuthorDTO authorDTO);

    AuthorDTO updateAuthor(AuthorDTO authorDTO,Long id);

    List<AuthorDTO> getAuthors();

    void deleteAuthor(Long id);
    
}
