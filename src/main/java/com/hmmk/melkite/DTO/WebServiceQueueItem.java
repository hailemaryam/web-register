package com.hmmk.melkite.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebServiceQueueItem {
    private String id;
    private String phone;
    private String websiteRegisteredId;
    private String generatedPassword;
    private String serviceId;
    private String productId;
    private String updateType;
}
