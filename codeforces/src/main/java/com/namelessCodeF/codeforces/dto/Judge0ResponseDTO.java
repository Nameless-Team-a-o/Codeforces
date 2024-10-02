package com.namelessCodeF.codeforces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Judge0ResponseDTO {
    private  Object status;
    private String submissionId;
    private Float time;
    private Float memory;
    private String stdout;

    public void setStdoutFromEncoded(String encodedOutput) {
        if (encodedOutput != null) {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedOutput);
            this.stdout = new String(decodedBytes); // Convert bytes to String
        } else {
            this.stdout = null; // Handle null case
        }
    }
}
