package com.namelessCodeF.codeforces.controller;

import com.namelessCodeF.codeforces.dto.ResultResponseDTO;
import com.namelessCodeF.codeforces.service.Judge0CallbackService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/results/callback")
public class Judge0CallbackController {

    private final Judge0CallbackService judge0CallbackService;
    private static final Logger logger = LoggerFactory.getLogger(Judge0CallbackController.class);

    @PutMapping
    public ResponseEntity<String> receiveJudgeResult(
            @RequestBody ResultResponseDTO resultResponse,
            @RequestParam(required = false) String submissionId) { // Optional parameter for submission ID

        try {
            logger.info("Received result from Judge0: {}", resultResponse);
            if (submissionId != null) {
                resultResponse.setSubmissionId(submissionId); // Associate submission ID
            }
            judge0CallbackService.processResult(resultResponse);
            return ResponseEntity.ok("Result processed successfully.");
        } catch (Exception e) {
            logger.error("Error processing Judge result: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error processing the result.");
        }
    }
}
