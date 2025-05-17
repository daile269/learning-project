package com.learning.core_service.temporal.postWorkflow.impl;

import com.learning.core_service.entity.Post;
import com.learning.core_service.enums.PostStatus;
import com.learning.core_service.exception.AppException;
import com.learning.core_service.exception.ErrorCode;
import com.learning.core_service.repository.PostRepository;
import com.learning.core_service.temporal.postWorkflow.PostDeletionActivities;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@ActivityImpl(taskQueues = "core-service-task-queue")
@RequiredArgsConstructor
@Component
public class PostDeletionActivitiesImpl implements PostDeletionActivities {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public void markPostPendingDelete(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            post.setStatus(PostStatus.PENDING_DELETE);
            postRepository.save(post);
        }else {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }
    }

    @Override
    public boolean isPostRestored(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            return post.getStatus()!=PostStatus.PENDING_DELETE;
        }
        return false;
    }

    @Override
    @Transactional
    public void deletePostPermanently(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            if (post.getStatus()== PostStatus.PENDING_DELETE){
                postRepository.deleteById(postId);
                System.out.println("Delete Success");
            }else {
                throw new AppException(ErrorCode.CANNOT_DELETE_POST);
            }
        }else {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }
    }

    @Override
    public void restorePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        if (!PostStatus.PENDING_DELETE.equals(post.getStatus())) {
            throw new AppException(ErrorCode.CANNOT_RESTORE_POST);
        }
        post.setStatus(PostStatus.PUBLISHED);
        postRepository.save(post);
    }
}
