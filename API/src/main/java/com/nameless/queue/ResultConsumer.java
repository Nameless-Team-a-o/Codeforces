package com.nameless.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nameless.config.RabbitMQConfig;
import com.nameless.dto.judge.JudgeResponseDTO;
import com.nameless.service.judge.ResultProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Component
public class ResultConsumer {

    private final ObjectMapper objectMapper;
    private final ResultProcessingService resultProcessingService;
    private static final Logger logger = LoggerFactory.getLogger(ResultConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.RESULT_QUEUE_NAME)
    public void receiveResult(String resultJson) {
        try {
            JudgeResponseDTO resultResponse = objectMapper.readValue(resultJson, JudgeResponseDTO.class);
            logger.info("Received result: {}", resultResponse);
            resultProcessingService.processResult(resultResponse);
        } catch (Exception e) {
            logger.error("Error processing result: {}", e.getMessage());
        }
    }
}
