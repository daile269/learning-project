    package com.learning.core_service.service.impl;


    import com.learning.core_service.dto.PostDTO;
    import com.learning.core_service.entity.Author;
    import com.learning.core_service.entity.Post;
    import com.learning.core_service.exception.AppException;
    import com.learning.core_service.exception.ErrorCode;
    import com.learning.core_service.exception.ResourceNotFoundException;
    import com.learning.core_service.mapper.PostMapper;
    import com.learning.core_service.repository.AuthorRepository;
    import com.learning.core_service.repository.PostRepository;
    import com.learning.core_service.service.PostService;
    import com.learning.core_service.untils.S3Service;
    import io.micrometer.core.instrument.Counter;
    import io.micrometer.core.instrument.MeterRegistry;
    import io.micrometer.core.instrument.Timer;
    import lombok.RequiredArgsConstructor;
    import org.apache.dubbo.config.annotation.DubboReference;
    import org.apache.dubbo.config.annotation.DubboService;
    import org.springframework.cache.annotation.CacheEvict;
    import org.springframework.cache.annotation.CachePut;
    import org.springframework.cache.annotation.Cacheable;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @DubboService
    public class PostServiceImpl implements PostService {

        private final PostRepository postRepository;
        private final AuthorRepository authorRepository;
        private final PostMapper postMapper;

        private final Counter createCounter;

        private final Counter deleteCounter;
        private final S3Service s3Service;

        private final MeterRegistry meterRegistry;
        public PostServiceImpl(
                PostRepository postRepository,
                AuthorRepository authorRepository,
                PostMapper postMapper,
                S3Service s3Service,
                MeterRegistry meterRegistry
        ) {
            this.postRepository = postRepository;
            this.authorRepository = authorRepository;
            this.postMapper = postMapper;
            this.s3Service = s3Service;
            this.meterRegistry = meterRegistry;

            this.createCounter = meterRegistry.counter("post.create.count");
            this.deleteCounter = Counter.builder("post.delete.count")
                    .description("Number of posts deleted")
                    .register(meterRegistry);
            ;
        }
    //    @DubboReference
    //    private final AuthService authService;

        @Override
        @Cacheable(value = "post",key = "#id")
        public PostDTO findPostById(Long id) {
            Post post = postRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Post is not exits"));
            return postMapper.toDTO(post);
        }

        @Override
        @CachePut(value = "post")
        public PostDTO createPost(PostDTO postDTO) {
            Post post =postMapper.toEntity(postDTO);
            Author author =authorRepository.findById(postDTO.getAuthorId()).orElseThrow(() ->
                    new ResourceNotFoundException("Author is not exits"));
            post.setAuthor(author);
            postRepository.save(post);
            createCounter.increment();
            return postMapper.toDTO(post);
        }

        @Override
        @CachePut(value = "post",key = "#id")
        public PostDTO updatePost(PostDTO postDTO, Long id) {
            Post post = postMapper.toEntity(postDTO);
            post.setId(id);
            Post post1 = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
            Author author =authorRepository.findById(postDTO.getAuthorId()).orElseThrow(() ->
                    new ResourceNotFoundException("Author is not exits"));
            post.setAuthor(author);
            post.setCreateTime(post1.getCreateTime());
            post.setImageUrl(post1.getImageUrl());
            postRepository.save(post);
            return postMapper.toDTO(post);
        }

        @Override
        @Cacheable(value = "posts")
        public List<PostDTO> getPosts() {
            return postRepository.findAll().stream().map(post -> postMapper.toDTO(post)).collect(Collectors.toList());
        }

        @Override
        @CacheEvict(value = "post",key = "#id")
        public void deletePost(Long id) {
            deleteCounter.increment();
            Timer.builder("post.delete.timer")
                    .description("Time taken to delete a post")
                    .register(meterRegistry)
                    .record(() -> {
                        postRepository.findById(id)
                                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
                        postRepository.deleteById(id);
                    });
        }

        @Override
        public PostDTO updateImage(Long id, MultipartFile image) throws IOException {
            Post post = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
            String imageUrl = s3Service.uploadFile(image);
            post.setImageUrl(imageUrl);
            postRepository.save(post);
            return postMapper.toDTO(post);
        }
    }
