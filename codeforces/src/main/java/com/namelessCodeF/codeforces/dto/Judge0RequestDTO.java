package com.namelessCodeF.codeforces.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Judge0RequestDTO {
    @JsonProperty("callback_url")
    private String callback_url;
    @JsonProperty("expected_output")
    private String expected_output;
    @JsonProperty("stdin")
    private String stdin;
    @JsonProperty("source_code")
    private String source_code;
    @JsonProperty("language_id")
    private int language_id;

}
