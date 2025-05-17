package com.learning.core_service.config;

import com.learning.core_service.temporal.postWorkflow.PostDeletionWorkflow;
import com.learning.core_service.temporal.postWorkflow.impl.PostDeletionActivitiesImpl;
import com.learning.core_service.temporal.postWorkflow.impl.PostDeletionWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {
    private static final String TEMPORAL_SERVER_URL = "localhost:7233";

    @Bean
    public WorkflowClient workflowClient() {
        // Tạo WorkflowServiceStubs connect Temporal server
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();

        // Tạo WorkflowClient
        return WorkflowClient.newInstance(service);
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient client) {
        return WorkerFactory.newInstance(client);
    }

    @Bean
    public Worker userWorker(WorkerFactory factory, PostDeletionActivitiesImpl activitiesImpl) {
        Worker worker = factory.newWorker("core-service-task-queue");
        worker.registerWorkflowImplementationTypes(PostDeletionWorkflowImpl.class);
        worker.registerActivitiesImplementations(activitiesImpl);
        factory.start();
        return worker;
    }
}
