package com.hmmk.melkite.DAO;

import com.hmmk.melkite.DTO.WebServiceQueueItem;
import com.hmmk.melkite.Entity.WebRegisteredIdTable;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WebRegisteredIdTableDao {

    public WebRegisteredIdTable createOrUpdate(WebServiceQueueItem webServiceQueueItem){
        WebRegisteredIdTable existing = findByServiceIdAndProductId(webServiceQueueItem);
        if (existing != null){
            existing.serviceId = webServiceQueueItem.getServiceId();
            existing.productId = webServiceQueueItem.getProductId();
            existing.phone = webServiceQueueItem.getPhone();
            existing.webRegisteredId = webServiceQueueItem.getWebsiteRegisteredId();
            existing.persistAndFlush();
            return existing;
        } else {
            WebRegisteredIdTable webRegisteredIdTable = new WebRegisteredIdTable();
            webRegisteredIdTable.serviceId = webServiceQueueItem.getServiceId();
            webRegisteredIdTable.productId = webServiceQueueItem.getProductId();
            webRegisteredIdTable.phone = webServiceQueueItem.getPhone();
            webRegisteredIdTable.webRegisteredId = webServiceQueueItem.getWebsiteRegisteredId();
            webRegisteredIdTable.persistAndFlush();
            return webRegisteredIdTable;
        }
    }
    public WebRegisteredIdTable findByServiceIdAndProductId(WebServiceQueueItem webServiceQueueItem) {
        return WebRegisteredIdTable.find("serviceId = ?1 and productId = ?2 and phone = ?3", webServiceQueueItem.getServiceId(), webServiceQueueItem.getProductId(), webServiceQueueItem.getPhone()).firstResult();
    }

}
