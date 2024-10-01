package com.namelessCodeF.codeforces.service;

import com.namelessCodeF.codeforces.client.Judge0Client;
import com.namelessCodeF.codeforces.dto.Judge0SubmissionDTO;
import com.namelessCodeF.codeforces.exception.Judge0SubmissionException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@RequiredArgsConstructor
@Service
public class Judge0SubmissionService {
    private final Judge0Client judge0Client;
    private static final Logger logger = LoggerFactory.getLogger(Judge0SubmissionService.class);

    public void sendSubmissionToJudge0(Judge0SubmissionDTO judge0SubmissionDTO) {
        try {
            judge0Client.sendSubmission(judge0SubmissionDTO);
            logger.info("Submission sent to Judge0: {}", judge0SubmissionDTO);
        } catch (RestClientException e) {
            logger.error("Failed to send submission to Judge0: {}", e.getMessage());
            throw new Judge0SubmissionException("Failed to send submission to Judge0", e);
        }
    }
}
