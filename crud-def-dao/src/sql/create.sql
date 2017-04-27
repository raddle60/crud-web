create table CRUD_WEB.CRUD_DATASOURCE
(
  id                NUMBER(18) not null,
  name              VARCHAR2(64) not null,
  code              VARCHAR2(64) not null,
  env_code          VARCHAR2(32) not null,  
  dirver_class_name VARCHAR2(64) not null,
  url               VARCHAR2(256) not null,
  username          VARCHAR2(64) not null,
  password          VARCHAR2(64) not null,
  deleted           NUMBER(1) not null,
  created_at        TIMESTAMP(6) not null,
  updated_at        TIMESTAMP(6) not null
)
;
comment on table CRUD_WEB.CRUD_DATASOURCE
  is '数据源';
alter table CRUD_WEB.CRUD_DATASOURCE
  add constraint PK_CRUD_DATASOURCE primary key (ID);

create table CRUD_WEB.CRUD_DEFINITION
(
  id                 NUMBER(18) not null,
  name               VARCHAR2(64) not null,
  def_type           VARCHAR2(32) not null,
  table_name         VARCHAR2(64),
  table_schema       VARCHAR2(64),
  crud_ds_id         NUMBER(18) not null,
  crud_ds_code       VARCHAR2(64) not null,
  pre_sql            VARCHAR2(4000),
  read_sql           VARCHAR2(4000),
  key_select_sql     VARCHAR2(4000),
  update_sql         VARCHAR2(4000),
  add_href           VARCHAR2(512),
  is_paging          NUMBER(1),
  auto_match_btn     NUMBER(1),
  create_tm_col      VARCHAR2(64),
  update_tm_col      VARCHAR2(64),
  deleted            NUMBER(1) not null,
  created_at         TIMESTAMP(6) not null,
  updated_at         TIMESTAMP(6) not null,
  composite_template VARCHAR2(4000)
)
;
comment on table CRUD_WEB.CRUD_DEFINITION
  is '表单定义';
alter table CRUD_WEB.CRUD_DEFINITION
  add constraint PK_CRUD_DEFINITION primary key (ID);

create table CRUD_WEB.CRUD_ITEM
(
  id              NUMBER(18) not null,
  crud_def_id     NUMBER(18) not null,
  fk_type         VARCHAR2(32) not null,
  title           VARCHAR2(64) not null,
  var_name        VARCHAR2(32),
  item_type       VARCHAR2(32) not null,
  input_type      VARCHAR2(32),
  input_size      VARCHAR2(10),
  format          VARCHAR2(32),
  action_type     VARCHAR2(32),
  href            VARCHAR2(256),
  web_chk_rule    VARCHAR2(4000),
  server_chk_rule VARCHAR2(4000),
  option_type     VARCHAR2(32),
  option_value    VARCHAR2(4000),
  crud_ds_id      NUMBER(18),
  item_order      NUMBER(4),
  descript        VARCHAR2(4000),
  deleted         NUMBER(1) not null,
  created_at      TIMESTAMP(6) not null,
  updated_at      TIMESTAMP(6) not null
)
;
comment on table CRUD_WEB.CRUD_ITEM
  is '表单字段';
alter table CRUD_WEB.CRUD_ITEM
  add constraint PK_CRUD_ITEM primary key (ID);

create sequence CRUD_WEB.SEQ_CRUD_DATASOURCE;

create sequence CRUD_WEB.SEQ_CRUD_DEFINITION;

create sequence CRUD_WEB.SEQ_CRUD_ITEM;
