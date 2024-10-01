package com.namelessCodeF.codeforces.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namelessCodeF.codeforces.config.RabbitMQConfig;
import com.namelessCodeF.codeforces.dto.Judge0SubmissionDTO;
import com.namelessCodeF.codeforces.dto.SubmissionDTO;
import com.namelessCodeF.codeforces.exception.SubmissionProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubmissionConsumerService {

    private final Judge0SubmissionService judge0SubmissionService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(SubmissionConsumerService.class);
    private static final String CALLBACK_URL_TEMPLATE = "https://8933-2a01-9700-5173-2100-35e5-d85-da08-320e.ngrok-free.app/api/v1/results/callback?submissionId=%s";

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveSubmissionFromQueue(String message) {
        try {
            SubmissionDTO submissionDTO = objectMapper.readValue(message, SubmissionDTO.class);
            Judge0SubmissionDTO judge0SubmissionDTO = buildJudge0SubmissionDTO(submissionDTO);
            logger.info("Received submission: {}", submissionDTO);
            judge0SubmissionService.sendSubmissionToJudge0(judge0SubmissionDTO);
        } catch (Exception e) {
            logger.error("Failed to process submission: {}", e.getMessage(), e);
            throw new SubmissionProcessingException("Failed to process submission from the queue", e);
        }
    }

    private Judge0SubmissionDTO buildJudge0SubmissionDTO(SubmissionDTO submissionDTO) {
        String callbackUrl = String.format(CALLBACK_URL_TEMPLATE, submissionDTO.getSubmissionId());
        return new Judge0SubmissionDTO(
                callbackUrl,
                submissionDTO.getExpectedOutput(),
                submissionDTO.getTestCase(),
                submissionDTO.getCode(),
                submissionDTO.getLanguageId()
        );
    }
}
