package com.hmmk.melkite.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "WebRegisteredIdTable")
@Cacheable
public class WebRegisteredIdTable extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String id;
    public String serviceId;
    public String productId;
    public String phone;
    public String webRegisteredId;
    @CreationTimestamp
    public Date dateCreated;
    @UpdateTimestamp
    public Date dateUpdated;

}
