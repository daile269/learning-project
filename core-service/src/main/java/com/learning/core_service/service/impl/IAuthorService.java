package com.learning.core_service.service.impl;

import com.learning.core_service.dto.AuthorDTO;
import com.learning.core_service.entity.Author;
import com.learning.core_service.exception.AppException;
import com.learning.core_service.exception.ErrorCode;
import com.learning.core_service.exception.ResourceNotFoundException;
import com.learning.core_service.mapper.AuthorMapper;
import com.learning.core_service.repository.AuthorRepository;
import com.learning.core_service.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IAuthorService implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;
    @Override
    public AuthorDTO findAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Author is not exist"));
        return authorMapper.toDTO(author);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorRepository.save(author);
        return authorMapper.toDTO(author);
    }

    @Override
    public AuthorDTO updateAuthor(AuthorDTO authorDTO, Long id) {
        Author author = authorMapper.toEntity(authorDTO);
        author.setId(id);
        Author author1 = authorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
        author.setPosts(author1.getPosts());
        author.setEmail(author1.getEmail());
        authorRepository.save(author);
        return authorMapper.toDTO(author);
    }

    @Override
    public List<AuthorDTO> getAuthors() {
        return authorRepository.findAll().stream().map(author ->
                authorMapper.toDTO(author)).collect(Collectors.toList());
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
        authorRepository.deleteById(id);
    }
}
