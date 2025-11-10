package com.emobile.springtodo.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class TaskMetrics {

    private final MeterRegistry meterRegistry;

    public TaskMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incrementCompletedTasks() {
        meterRegistry.counter("tasks.completed").increment();
    }
}