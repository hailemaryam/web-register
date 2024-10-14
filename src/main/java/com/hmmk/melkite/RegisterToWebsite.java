package com.hmmk.melkite;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

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

    public HttpResponse registerUser(SubUnSubItem subUnSubItem) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        String urlRaw = subUnSubItem.getWebUrlRegister() + subUnSubItem.getWebQueryParamRegister();
        String methodName = subUnSubItem.getWebMethodRegister();
        String payload = subUnSubItem.getWebPayloadRegister();
        return executeHttpRequest(subUnSubItem, urlRaw, methodName, payload);
    }
    public HttpResponse deleteUser(SubUnSubItem subUnSubItem) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        String urlRaw = subUnSubItem.getWebUrlDelete() + subUnSubItem.getWebQueryParamDelete();
        String methodName = subUnSubItem.getWebMethodDelete();
        String payload = subUnSubItem.getWebPayloadDelete();
        return executeHttpRequest(subUnSubItem, urlRaw, methodName, payload);
    }
    public HttpResponse successFullChargingNotice(SubUnSubItem subUnSubItem) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        String urlRaw = subUnSubItem.getWebUrlNoticeCharging() + subUnSubItem.getWebQueryParamNoticeCharging();
        String methodName = subUnSubItem.getWebMethodNoticeCharging();
        String payload = subUnSubItem.getWebPayloadNoticeCharging();
        return executeHttpRequest(subUnSubItem, urlRaw, methodName, payload);
    }

    private HttpResponse executeHttpRequest(SubUnSubItem subUnSubItem, String urlRaw, String methodName, String payload) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        HttpClient client = HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        HttpUriRequest method = getRequestMethod(subUnSubItem, methodName ,urlRaw);
        method.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        if(subUnSubItem.getWebHeaderName() != null){
            if (!subUnSubItem.getWebHeaderName().isEmpty()){
                method.addHeader(subUnSubItem.getWebHeaderName(), subUnSubItem.getWebHeaderValue());
            }
        }
        String jsonString = getPayload(subUnSubItem, payload);
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
    private HttpUriRequest getRequestMethod(SubUnSubItem subUnSubItem, String methodName, String url){
        HttpUriRequest method = null;
        if (methodName.equalsIgnoreCase("post")){
            method = new HttpPost(getFullURL(subUnSubItem, url));
        } else if (methodName.equalsIgnoreCase("get")){
            method = new HttpGet(getFullURL(subUnSubItem, url));
        } else if (methodName.equalsIgnoreCase("delete")){
            method = new HttpDelete(getFullURL(subUnSubItem, url));
        }
        return method;
    }
    private String getFullURL(SubUnSubItem subUnSubItem, String url){
        if (url.contains("{ID}")){
            url = url.replace( "{ID}", subUnSubItem.getWebsiteUserId());
        }
        if (url.contains("{PHONE}")){
            url = url.replace( "{PHONE}", subUnSubItem.getPhone().substring(3,12));
        }
        if (url.contains("{PASSWORD}")){
            url = url.replace( "{PASSWORD}", subUnSubItem.getGeneratedPassword());
        }
        return url;
    }
    private String getPayload(SubUnSubItem subUnSubItem, String payload){
        if (payload.contains("$emi")){
            payload = payload.replace("$emi", ":");
        }
        if (payload.contains("{ID}")){
            payload = payload.replace( "{ID}", subUnSubItem.getWebsiteUserId());
        }
        if (payload.contains("{PHONE}")){
            payload = payload.replace( "{PHONE}", subUnSubItem.getPhone().substring(3,12));
        }
        if (payload.contains("{PASSWORD}")){
            payload = payload.replace( "{PASSWORD}", subUnSubItem.getGeneratedPassword());
        }
        if (payload.contains("{EMAIL}")){
            payload = payload.replace( "{EMAIL}", subUnSubItem.getPhone()+ "@fake.com");
        }
        return payload;
    }
}
