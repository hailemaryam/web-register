package com.hmmk.melkite.web.rest;


import com.hmmk.melkite.DAO.WebConfigDao;
import com.hmmk.melkite.Entity.WebConfigItem;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@Path("web-config")
@AllArgsConstructor
public class WebConfig {
    @Inject
    WebConfigDao webConfigDao;

    @GET
    @Path("get-web-config")
    @Produces("application/json")
    @Consumes("application/json")
    public RestResponse<WebConfigItem> getWebConfigItem(
            @QueryParam("service-id") String serviceId,
            @QueryParam("product-id") String productId
            ){
        WebConfigItem byServiceIdAndProductId = webConfigDao.findByServiceIdAndProductId(serviceId, productId);
        return RestResponse.ok(byServiceIdAndProductId);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public RestResponse<WebConfigItem> createOrUpdate( WebConfigItem webConfigItem){
        WebConfigItem webConfigUpdated = webConfigDao.createOrUpdate(webConfigItem);
        return RestResponse.ResponseBuilder.create(RestResponse.Status.CREATED, webConfigUpdated).build();
    }
}
