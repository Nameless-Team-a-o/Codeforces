package com.nameless.dto.judge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeResponseDTO {
    private Status status;
    private String submissionId;
    private Float time;
    private Float memory;
    private String stdout;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Status {
        private int id;
        private String description;
    }
}