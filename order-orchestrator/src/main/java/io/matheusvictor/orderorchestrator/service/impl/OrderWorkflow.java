package io.matheusvictor.orderorchestrator.service.impl;

import io.matheusvictor.orderorchestrator.service.WorkFlowStep;
import io.matheusvictor.orderorchestrator.service.Workflow;

import java.util.List;

public class OrderWorkflow implements Workflow {

    private final List<WorkFlowStep> steps;

    public OrderWorkflow(List<WorkFlowStep> steps) {
        this.steps = steps;
    }

    @Override
    public List<WorkFlowStep> getSteps() {
        return steps;
    }
}
