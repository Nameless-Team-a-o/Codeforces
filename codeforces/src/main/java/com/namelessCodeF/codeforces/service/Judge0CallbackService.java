package com.namelessCodeF.codeforces.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.namelessCodeF.codeforces.dto.ResultResponseDTO;
import com.namelessCodeF.codeforces.exception.ResultProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.namelessCodeF.codeforces.config.RabbitMQConfig;

@RequiredArgsConstructor
@Service
public class Judge0CallbackService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(Judge0CallbackService.class);

    public void processResult(ResultResponseDTO resultResponse) {
        try {

            resultResponse.setStdoutFromEncoded(resultResponse.getStdout()); // Decode and set stdout

            // Serialize the ResultResponseDTO to JSON
            String resultJson = objectMapper.writeValueAsString(resultResponse);
            rabbitTemplate.convertAndSend(RabbitMQConfig.RESULT_QUEUE_NAME, resultJson);
            logger.info("Result processed and sent to first server: {}", resultResponse);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize result response: {}", e.getMessage());
            throw new ResultProcessingException("Failed to process result", e);
        }
    }
}
