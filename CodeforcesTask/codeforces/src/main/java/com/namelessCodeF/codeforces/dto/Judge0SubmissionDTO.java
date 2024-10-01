package com.namelessCodeF.codeforces.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
public class Judge0SubmissionDTO {
    @JsonProperty("source_code")
    private String source_code;
    @JsonProperty("language_id")
    private int language_id;
    @JsonProperty("stdin")
    private String stdin;
    @JsonProperty("expected_output")
    private String expected_output;
    @JsonProperty("callback_url")
    private String callback_url;
    public Judge0SubmissionDTO(String callbackUrl, String expectedOutput, String testCase, String sourceCode, int languageId) {
        this.callback_url = callbackUrl;
        this.expected_output = expectedOutput;
        this.stdin = testCase;
        this.source_code = sourceCode;
        this.language_id = languageId;
    }


}
