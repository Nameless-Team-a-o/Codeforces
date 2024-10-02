package com.nameless.dto.judge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JudgeRequestDTO {
    private Long submissionId;
    private String languageId;
    private String code;
    private String expectedOutput;
    private String testCase;
}
