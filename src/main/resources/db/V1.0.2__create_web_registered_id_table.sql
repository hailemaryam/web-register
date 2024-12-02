create table WebRegisteredIdTable (
    id varchar(255) not null,
    productId varchar(255),
    serviceId varchar(255),
    dateCreated datetime(6),
    dateUpdated datetime(6),
    webRegisteredId varchar(255),
    phone varchar(255),
    primary key (id)
) engine=InnoDB;