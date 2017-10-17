# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                            integer auto_increment not null,
  name                          varchar(255),
  email                         varchar(255),
  address1                      varchar(255),
  address2                      varchar(255),
  town                          varchar(255),
  postcode                      varchar(255),
  country                       varchar(255),
  phone                         varchar(255),
  constraint pk_user primary key (id)
);


# --- !Downs

drop table if exists user;

