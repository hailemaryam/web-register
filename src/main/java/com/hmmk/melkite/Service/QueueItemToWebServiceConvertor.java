package com.hmmk.melkite.Service;

import com.hmmk.melkite.DAO.WebConfigDao;
import com.hmmk.melkite.DTO.WebServiceItem;
import com.hmmk.melkite.DTO.WebServiceQueueItem;
import com.hmmk.melkite.Entity.WebConfigItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class QueueItemToWebServiceConvertor {

        @Inject
        WebConfigDao webConfigDao;

        public WebServiceItem convert(WebServiceQueueItem webServiceQueueItem) {
            WebConfigItem webConfigItem = webConfigDao.findByServiceIdAndProductId(webServiceQueueItem.getServiceId(), webServiceQueueItem.getProductId());
            if (webConfigItem != null) {
                return new WebServiceItem(
                        webServiceQueueItem.getId(),
                        webServiceQueueItem.getPhone(),
                        webServiceQueueItem.getWebsiteRegisteredId(),
                        webServiceQueueItem.getGeneratedPassword(),
                        webServiceQueueItem.getServiceId(),
                        webServiceQueueItem.getProductId(),
                        webConfigItem.webUrlRegister,
                        webConfigItem.webQueryParamRegister,
                        webConfigItem.webMethodRegister,
                        webConfigItem.webPayloadRegister,
                        webConfigItem.webUrlDelete,
                        webConfigItem.webQueryParamDelete,
                        webConfigItem.webMethodDelete,
                        webConfigItem.webPayloadDelete,
                        webConfigItem.webUrlNoticeCharging,
                        webConfigItem.webQueryParamNoticeCharging,
                        webConfigItem.webMethodNoticeCharging,
                        webConfigItem.webPayloadNoticeCharging,
                        webConfigItem.webHeaderName,
                        webConfigItem.webHeaderValue
                );
            } else {
                return null;
            }
        }
}
