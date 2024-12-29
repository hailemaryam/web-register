package com.hmmk.melkite.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmmk.melkite.DAO.WebRegisteredIdTableDao;
import com.hmmk.melkite.DTO.WebServiceItem;
import com.hmmk.melkite.DTO.WebServiceQueueItem;
import com.hmmk.melkite.Entity.WebRegisteredIdTable;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@ApplicationScoped
@Log
public class WebRegisterService {

    @Inject
    QueueItemToWebServiceConvertor queueItemToWebServiceConvertor;

    @Inject
    RegisterToWebsite registerToWebsite;

    @Inject
    WebRegisteredIdTableDao webRegisteredIdTableDao;

    @Incoming("sdp-notify-web-register")
    @Blocking
    public void registerWebServiceItem(WebServiceQueueItem webServiceQueueItem) {
        WebServiceItem webServiceItem = queueItemToWebServiceConvertor.convert(webServiceQueueItem);
        if (webServiceItem == null) return;
        if (webServiceQueueItem.getUpdateType().equalsIgnoreCase("ok")){
            try {
                HttpResponse response = registerToWebsite.registerUser(webServiceItem);
                handleWebRegistrationId(webServiceQueueItem, response);
            } catch (Exception e) {
                // todo handle this exception using logger and tenant problem notifier
                throw new RuntimeException(e);
            }
        } else {
            try {
                WebRegisteredIdTable byServiceIdAndProductId = webRegisteredIdTableDao.findByServiceIdAndProductId(webServiceQueueItem);
                if (byServiceIdAndProductId != null){
                    webServiceItem.setWebsiteRegisteredId(byServiceIdAndProductId.webRegisteredId);
                }
                registerToWebsite.deleteUser(webServiceItem);
            } catch (Exception e) {
                // todo handle this exception using logger and tenant problem notifier
                throw new RuntimeException(e);
            }
        }
    }

    @Incoming("success-notifier-web-register")
    @Blocking
    public void noticeCharging(WebServiceQueueItem webServiceQueueItem) {
        WebServiceItem webServiceItem = queueItemToWebServiceConvertor.convert(webServiceQueueItem);
        if (webServiceItem == null) return;
        try {
            registerToWebsite.successFullChargingNotice(webServiceItem);
        } catch (Exception e) {
            // todo handle this exception using logger and tenant problem notifier
            throw new RuntimeException(e);
        }
    }

    private void handleWebRegistrationId(WebServiceQueueItem webServiceQueueItem, HttpResponse response) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        HttpEntity entity = response.getEntity();
        Header encodingHeader = entity.getContentEncoding();
        Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 : Charsets.toCharset(encodingHeader.getValue());
        String json = EntityUtils.toString(entity, encoding);
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map
                    = mapper.readValue(json, new TypeReference<Map<String,Object>>(){});
            map.forEach((s, o) -> System.out.println(o));
            if (map.get("id") != null){
                webServiceQueueItem.setWebsiteRegisteredId(map.get("id").toString());
                webRegisteredIdTableDao.createOrUpdate(webServiceQueueItem);
            }
        } catch (JsonParseException ignored){

        }
    }
}
