package com.namelessCodeF.codeforces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDTO {
    private Long submissionId;
    private int languageId;
    private String code;
    private String expectedOutput;
    private String testCase;
}