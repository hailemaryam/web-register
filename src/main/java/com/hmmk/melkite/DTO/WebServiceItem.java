package com.hmmk.melkite.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebServiceItem {
    private String id;
    private String phone;
    private String websiteRegisteredId;
    private String generatedPassword;
    private String serviceId;
    private String productId;
    private String webUrlRegister;
    private String webQueryParamRegister;
    private String webMethodRegister;
    private String webPayloadRegister;
    private String webUrlDelete;
    private String webQueryParamDelete;
    private String webMethodDelete;
    private String webPayloadDelete;
    private String webUrlNoticeCharging;
    private String webQueryParamNoticeCharging;
    private String webMethodNoticeCharging;
    private String webPayloadNoticeCharging;
    private String webHeaderName;
    private String webHeaderValue;
}
