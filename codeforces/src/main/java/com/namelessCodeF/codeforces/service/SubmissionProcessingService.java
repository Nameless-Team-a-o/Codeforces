package com.namelessCodeF.codeforces.service;

import com.namelessCodeF.codeforces.client.Judge0SubmissionClient;
import com.namelessCodeF.codeforces.dto.Judge0RequestDTO;
import com.namelessCodeF.codeforces.dto.SubmissionRequestDTO;
import com.namelessCodeF.codeforces.exception.Judge0SubmissionException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@RequiredArgsConstructor
@Service
public class SubmissionProcessingService {
    private final Judge0SubmissionClient judge0SubmissionClient;
    private static final Logger logger = LoggerFactory.getLogger(SubmissionProcessingService.class);
    private static final String CALLBACK_URL_TEMPLATE = "https://6951-2a01-9700-51ae-cf00-b8f1-3511-f824-ec1.ngrok-free.app/api/v1/results/callback?submissionId=%s";

    public void processSubmission(SubmissionRequestDTO submissionRequestDTO) {
        try {
            Judge0RequestDTO judge0RequestDTO = buildJudge0SubmissionDTO(submissionRequestDTO);
            sendSubmissionToJudge0(judge0RequestDTO);
        } catch (Exception e) {
            logger.error("Failed to process submission: {}", e.getMessage());
            throw new Judge0SubmissionException("Failed to process submission", e);
        }
    }

    private Judge0RequestDTO buildJudge0SubmissionDTO(SubmissionRequestDTO submissionRequestDTO) {
        String callbackUrl = String.format(CALLBACK_URL_TEMPLATE, submissionRequestDTO.getSubmissionId());
        return Judge0RequestDTO.builder()
                .callback_url(callbackUrl)
                .expected_output(submissionRequestDTO.getExpectedOutput())
                .stdin(submissionRequestDTO.getTestCase())
                .source_code(submissionRequestDTO.getCode())
                .language_id(submissionRequestDTO.getLanguageId())
                .build();
    }

    private void sendSubmissionToJudge0(Judge0RequestDTO judge0RequestDTO) {
        try {
            judge0SubmissionClient.sendSubmission(judge0RequestDTO);
            logger.info("Submission sent to Judge0: {}", judge0RequestDTO);
        } catch (RestClientException e) {
            logger.error("Failed to send submission to Judge0: {}", e.getMessage());
            throw new Judge0SubmissionException("Failed to send submission to Judge0", e);
        }
    }
}
