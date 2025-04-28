package com.learning.core_service.handler;

import com.learning.core_service.dto.AuthorDTO;
import com.learning.core_service.exception.ResourceNotFoundException;
import com.learning.core_service.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorHandler {
    @DubboReference
    private final AuthorService authorService;


    public List<AuthorDTO> getALlAuthors() {
        return authorService.getAuthors();
    }

    public AuthorDTO getAuthorById(Long id) {
        try {

            return authorService.findAuthorById(id);
        }catch (ResourceNotFoundException ex){

            throw new ResourceNotFoundException("Author not found");
        }

    }

    public AuthorDTO createAuthor(AuthorDTO AuthorDTO) {
        return authorService.createAuthor(AuthorDTO);
    }

    public AuthorDTO updateAuthor(AuthorDTO AuthorDTO, Long id) {
        return authorService.updateAuthor(AuthorDTO,id);
    }
    public void deleteAuthor(Long id){
        authorService.deleteAuthor(id);
    }
}
