package com.learning.core_service.temporal.postWorkflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface PostDeletionWorkflow {

    @WorkflowMethod
    void scheduleDeletion(Long postId);
}
