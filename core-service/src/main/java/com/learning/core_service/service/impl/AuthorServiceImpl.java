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
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@DubboService
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;
    @Override
    @Cacheable(value = "author",key = "#id")
    public AuthorDTO findAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Author is not exist"));
        return authorMapper.toDTO(author);
    }

    @Override
    @CachePut(value = "author")
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        if (authorRepository.existsByEmail(author.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_IS_EXISTS);
        } else {
            authorRepository.save(author);
        }
        return authorMapper.toDTO(author);
    }

    @Override
    @CachePut(value = "author",key = "#id")
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
    @Cacheable(value = "authors")
    public List<AuthorDTO> getAuthors() {
        return authorRepository.findAll().stream().map(author ->
                authorMapper.toDTO(author)).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "author",key = "#id")
    public void deleteAuthor(Long id) {
        authorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
        authorRepository.deleteById(id);
    }
}
