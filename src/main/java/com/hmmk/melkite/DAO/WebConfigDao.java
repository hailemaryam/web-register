package com.hmmk.melkite.DAO;

import com.hmmk.melkite.Entity.WebConfigItem;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WebConfigDao {

    public WebConfigItem createOrUpdate(WebConfigItem webConfigItem) {
        WebConfigItem existing = findByServiceIdAndProductId(webConfigItem.serviceId, webConfigItem.productId);
        if (existing != null){
            existing.serviceId = webConfigItem.serviceId;
            existing.productId = webConfigItem.productId;
            existing.webUrlRegister = webConfigItem.webUrlRegister;
            existing.webQueryParamRegister = webConfigItem.webQueryParamRegister;
            existing.webMethodRegister = webConfigItem.webMethodRegister;
            existing.webPayloadRegister = webConfigItem.webPayloadRegister;
            existing.webUrlDelete = webConfigItem.webUrlDelete;
            existing.webQueryParamDelete = webConfigItem.webQueryParamDelete;
            existing.webMethodDelete = webConfigItem.webMethodDelete;
            existing.webPayloadDelete = webConfigItem.webPayloadDelete;
            existing.webUrlNoticeCharging = webConfigItem.webUrlNoticeCharging;
            existing.webQueryParamNoticeCharging = webConfigItem.webQueryParamNoticeCharging;
            existing.webMethodNoticeCharging = webConfigItem.webMethodNoticeCharging;
            existing.webPayloadNoticeCharging = webConfigItem.webPayloadNoticeCharging;
            existing.webHeaderName = webConfigItem.webHeaderName;
            existing.webHeaderValue = webConfigItem.webHeaderValue;
            existing.persistAndFlush();
            return existing;
        } else {
            webConfigItem.persistAndFlush();
            return webConfigItem;
        }
    }

    public WebConfigItem findByServiceIdAndProductId(String serviceId, String productId) {
        return WebConfigItem.find("serviceId = ?1 and productId = ?2", serviceId, productId).firstResult();
    }
}
