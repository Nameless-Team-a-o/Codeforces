package com.namelessCodeF.codeforces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionRequestDTO {
    private Long submissionId;
    private int languageId;
    private String code;
    private String expectedOutput;
    private String testCase;
}