package com.learning.core_service.temporal.postWorkflow;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface PostDeletionActivities {
    void markPostPendingDelete(Long postId);
    boolean isPostRestored(Long postId);
    void deletePostPermanently(Long postId);
    void restorePost(Long id);
}
