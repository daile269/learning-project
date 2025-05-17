package com.learning.core_service.temporal.postWorkflow.impl;

import com.learning.core_service.temporal.postWorkflow.PostDeletionActivities;
import com.learning.core_service.temporal.postWorkflow.PostDeletionWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;


public class PostDeletionWorkflowImpl implements PostDeletionWorkflow {

    private final PostDeletionActivities activities;

    public PostDeletionWorkflowImpl(){
        this.activities = Workflow.newActivityStub(
                PostDeletionActivities.class,
                ActivityOptions.newBuilder()
                        .setStartToCloseTimeout(Duration.ofMinutes(3))
                        .build()
        );
    }

    @Override
    public void scheduleDeletion(Long postId) {

        // Mark article "PENDING_DELETE"
        activities.markPostPendingDelete(postId);

        Workflow.sleep(Duration.ofMinutes(1));

        //Check restored
        boolean restored = activities.isPostRestored(postId);

        if(!restored){
            activities.deletePostPermanently(postId);
        }else {
            Workflow.getLogger(PostDeletionWorkflowImpl.class)
                    .info("Post {} was restored. Skipping deletion.", postId);
        }
    }
}
