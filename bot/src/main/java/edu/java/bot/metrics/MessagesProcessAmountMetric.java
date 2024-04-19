package edu.java.bot.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagesProcessAmountMetric {
    private final Counter messagesProcessed;
    private final MeterRegistry registry;

    @Autowired
    public MessagesProcessAmountMetric(MeterRegistry meterRegistry) {
        registry = meterRegistry;
        messagesProcessed = registry.counter("messages_processed_amount");
    }

    public void increment() {
        messagesProcessed.increment();
    }
}
