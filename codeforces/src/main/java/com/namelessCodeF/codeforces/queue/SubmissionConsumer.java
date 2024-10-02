package com.namelessCodeF.codeforces.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namelessCodeF.codeforces.config.RabbitMQConfig;
import com.namelessCodeF.codeforces.dto.SubmissionRequestDTO;
import com.namelessCodeF.codeforces.service.SubmissionProcessingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubmissionConsumer {

    private final SubmissionProcessingService submissionProcessingService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(SubmissionConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveSubmissionFromQueue(String message) {
        try {
            SubmissionRequestDTO submissionRequestDTO = objectMapper.readValue(message, SubmissionRequestDTO.class);
            logger.info("Received submission: {}", submissionRequestDTO);
            // Delegate the entire processing to the service
            submissionProcessingService.processSubmission(submissionRequestDTO);
        } catch (Exception e) {
            logger.error("Failed to process submission: {}", e.getMessage(), e);
        }
    }
}
