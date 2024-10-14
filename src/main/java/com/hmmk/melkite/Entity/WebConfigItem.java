package com.hmmk.melkite.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WebConfigItem")
@Cacheable
public class WebConfigItem  extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @JsonIgnore
    public String id;
    public String serviceId;
    public String productId;
    public String webUrlRegister;
    public String webQueryParamRegister;
    public String webMethodRegister;
    public String webPayloadRegister;
    public String webUrlDelete;
    public String webQueryParamDelete;
    public String webMethodDelete;
    public String webPayloadDelete;
    public String webUrlNoticeCharging;
    public String webQueryParamNoticeCharging;
    public String webMethodNoticeCharging;
    public String webPayloadNoticeCharging;
    public String webHeaderName;
    public String webHeaderValue;
}
