package com.sophia.sophia_pharmacy_service.dtos.message;

import lombok.Data;

@Data
public class ProcessingRequest {

    private String jobId;
    private String fileUrl;
    private String type;
}