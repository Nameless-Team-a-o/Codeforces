package com.nameless.dto.judge;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubmissionResponseDTO {
    private Long id;
    private String code;
    private String expectedOutput;
    private String result;
    private String status;
    private LocalDateTime createdAt;
}