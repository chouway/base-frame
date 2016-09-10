/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     2016/9/10 11:45:36                           */
/*==============================================================*/


drop table base_consume_info;

drop table base_server_consume_rel;

drop table base_server_info;

/*==============================================================*/
/* Table: base_consume_info                                     */
/*==============================================================*/
create table base_consume_info (
   id                   VARCHAR(32)          not null,
   consume_name         VARCHAR(64)          null,
   create_user          VARCHAR(32)          null,
   create_time          TIMESTAMP            null,
   update_time          TIMESTAMP            null,
   constraint PK_BASE_CONSUME_INFO primary key (id)
);

comment on table base_consume_info is
'base 消费信息表';

/*==============================================================*/
/* Table: base_server_consume_rel                               */
/*==============================================================*/
create table base_server_consume_rel (
   id                   VARCHAR(32)          not null,
   server_id            VARCHAR(32)          null,
   consume_id           VARCHAR(32)          null,
   constraint PK_BASE_SERVER_CONSUME_REL primary key (id)
);

comment on table base_server_consume_rel is
'base 服务-消费关系表';

/*==============================================================*/
/* Table: base_server_info                                      */
/*==============================================================*/
create table base_server_info (
   id                   VARCHAR(32)          not null,
   server_name          VARCHAR(64)          null,
   create_user          VARCHAR(32)          null,
   create_time          TIMESTAMP            null,
   update_time          TIMESTAMP            null,
   constraint PK_BASE_SERVER_INFO primary key (id)
);

comment on table base_server_info is
'base 服务信息表';

alter table base_server_consume_rel
   add constraint FK_BASE_SER_REFERENCE_BASE_SER foreign key (server_id)
      references base_server_info (id)
      on delete restrict on update restrict;

alter table base_server_consume_rel
   add constraint FK_BASE_SER_REFERENCE_BASE_CON foreign key (consume_id)
      references base_consume_info (id)
      on delete restrict on update restrict;

