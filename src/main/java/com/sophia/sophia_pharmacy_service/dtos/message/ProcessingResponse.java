package com.sophia.sophia_pharmacy_service.dtos.message;

import lombok.Data;

@Data
public class ProcessingResponse {

    private String jobId;
    private String status;
}