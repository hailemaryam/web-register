package com.hmmk.melkite.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import com.hmmk.melkite.DTO.WebServiceItem;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

@ApplicationScoped
public class RegisterToWebsite {

    public HttpResponse registerUser(WebServiceItem webServiceItem) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        String urlRaw = webServiceItem.getWebUrlRegister() + webServiceItem.getWebQueryParamRegister();
        String methodName = webServiceItem.getWebMethodRegister();
        String payload = webServiceItem.getWebPayloadRegister();
        return executeHttpRequest(webServiceItem, urlRaw, methodName, payload);
    }
    public HttpResponse deleteUser(WebServiceItem webServiceItem) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        String urlRaw = webServiceItem.getWebUrlDelete() + webServiceItem.getWebQueryParamDelete();
        String methodName = webServiceItem.getWebMethodDelete();
        String payload = webServiceItem.getWebPayloadDelete();
        return executeHttpRequest(webServiceItem, urlRaw, methodName, payload);
    }
    public HttpResponse successFullChargingNotice(WebServiceItem webServiceItem) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        String urlRaw = webServiceItem.getWebUrlNoticeCharging() + webServiceItem.getWebQueryParamNoticeCharging();
        String methodName = webServiceItem.getWebMethodNoticeCharging();
        String payload = webServiceItem.getWebPayloadNoticeCharging();
        return executeHttpRequest(webServiceItem, urlRaw, methodName, payload);
    }

    private HttpResponse executeHttpRequest(WebServiceItem webServiceItem, String urlRaw, String methodName, String payload) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        HttpClient client = HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        HttpUriRequest method = getRequestMethod(webServiceItem, methodName ,urlRaw);
        method.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        if(webServiceItem.getWebHeaderName() != null){
            if (!webServiceItem.getWebHeaderName().isEmpty()){
                method.addHeader(webServiceItem.getWebHeaderName(), webServiceItem.getWebHeaderValue());
            }
        }
        String jsonString = getPayload(webServiceItem, payload);
        HttpResponse response = null;
        if(jsonString != null && !jsonString.isEmpty()){
            HttpEntityEnclosingRequestBase entityMethod = (HttpEntityEnclosingRequestBase) method;
            entityMethod.setEntity(new StringEntity(jsonString));
            response = client.execute(entityMethod);
        } else {
            response = client.execute(method);
        }
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.toString());
        return response;
    }
    private HttpUriRequest getRequestMethod(WebServiceItem webServiceItem, String methodName, String url){
        HttpUriRequest method = null;
        if (methodName.equalsIgnoreCase("post")){
            method = new HttpPost(getFullURL(webServiceItem, url));
        } else if (methodName.equalsIgnoreCase("get")){
            method = new HttpGet(getFullURL(webServiceItem, url));
        } else if (methodName.equalsIgnoreCase("delete")){
            method = new HttpDelete(getFullURL(webServiceItem, url));
        }
        return method;
    }
    private String getFullURL(WebServiceItem webServiceItem, String url){
        if (url.contains("{ID}")){
            url = url.replace( "{ID}", webServiceItem.getWebsiteRegisteredId());
        }
        if (url.contains("{PHONE}")){
            url = url.replace( "{PHONE}", webServiceItem.getPhone().substring(3,12));
        }
        if (url.contains("{PASSWORD}")){
            url = url.replace( "{PASSWORD}", webServiceItem.getGeneratedPassword());
        }
        return url;
    }
    private String getPayload(WebServiceItem webServiceItem, String payload){
        if (payload.contains("$emi")){
            payload = payload.replace("$emi", ":");
        }
        if (payload.contains("{ID}")){
            payload = payload.replace( "{ID}", webServiceItem.getWebsiteRegisteredId());
        }
        if (payload.contains("{PHONE}")){
            payload = payload.replace( "{PHONE}", webServiceItem.getPhone().substring(3,12));
        }
        if (payload.contains("{PASSWORD}")){
            payload = payload.replace( "{PASSWORD}", webServiceItem.getGeneratedPassword());
        }
        if (payload.contains("{EMAIL}")){
            payload = payload.replace( "{EMAIL}", webServiceItem.getPhone()+ "@fake.com");
        }
        return payload;
    }
}
