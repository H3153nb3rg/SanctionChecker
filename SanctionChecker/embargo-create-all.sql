create table analysis_result (
  id                            bigint not null,
  msg_id                        bigint,
  exception_info                varchar(255),
  comment                       varchar(255),
  category                      varchar(255),
  analysis_start_time           bigint,
  analysis_stop_time            bigint,
  analysis_status               varchar(1),
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint ck_analysis_result_analysis_status check (analysis_status in ('N','H','B','C','R','D','I')),
  constraint uq_analysis_result_msg_id unique (msg_id),
  constraint pk_analysis_result primary key (id)
);
create sequence analysis_result_seq;

create table hit_result (
  hitres_type                   varchar(31) not null,
  id                            bigint not null,
  ar_id                         bigint not null,
  hit_field                     varchar(255),
  hit_descripton                varchar(255),
  absolut_hit                   integer,
  relative_hit                  integer,
  phrase_hit                    integer,
  hit_type                      varchar(255),
  entity_type                   varchar(255),
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  hit_id                        varchar(255),
  hit_external_url              varchar(255),
  hit_legal_basis               varchar(255),
  hit_list_name                 varchar(255),
  hit_remark                    varchar(255),
  hit_optimized                 varchar(255),
  constraint pk_hit_result primary key (id)
);
create sequence hit_result_seq;

create table message (
  msg_type                      varchar(5) not null,
  id                            bigint not null,
  raw_content                   clob,
  uuid                          varchar(255),
  in_time                       bigint,
  out_time                      bigint,
  message_processing_status     varchar(1),
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  business_id                   varchar(255),
  constraint ck_message_message_processing_status check (message_processing_status in ('N','H','B','C','R','D','I')),
  constraint pk_message primary key (id)
);
create sequence message_seq;

create table message_content (
  id                            bigint not null,
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint pk_message_content primary key (id)
);
create sequence message_content_seq;

create table process_step (
  id                            bigint not null,
  ar_id                         bigint not null,
  timestamp                     bigint,
  actor                         varchar(255),
  remark                        varchar(255),
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint pk_process_step primary key (id)
);
create sequence process_step_seq;

create table wl_address (
  id                            bigint not null,
  place                         varchar(255),
  line                          varchar(255),
  country                       varchar(255),
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint pk_wl_address primary key (id)
);
create sequence wl_address_seq;

create table wl_entity (
  comment                       varchar(255),
  issue_date                    varchar(255),
  type                          varchar(255),
  wl_id                         varchar(255),
  entry_type                    varchar(255)
);

create table wl_name (
  id                            bigint not null,
  first_name                    varchar(255),
  middle_name                   varchar(255),
  last_name                     varchar(255),
  whole_name                    varchar(255),
  description                   varchar(255),
  aka                           boolean,
  waka                          boolean,
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint pk_wl_name primary key (id)
);
create sequence wl_name_seq;

create table wl_passport (
  id                            bigint not null,
  number                        varchar(255),
  issue_date                    varchar(255),
  type                          varchar(255),
  country                       varchar(255),
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint pk_wl_passport primary key (id)
);
create sequence wl_passport_seq;

create table word_hit_info (
  hitres_type                   varchar(31) not null,
  id                            bigint not null,
  ar_id                         bigint not null,
  token                         varchar(255),
  field_name                    varchar(255),
  field_text                    varchar(255),
  value                         integer,
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  sanction_list_name            varchar(255),
  sanction_list_id              varchar(255),
  constraint pk_word_hit_info primary key (id)
);
create sequence word_hit_info_seq;

alter table analysis_result add constraint fk_analysis_result_msg_id foreign key (msg_id) references message (id) on delete restrict on update restrict;

alter table hit_result add constraint fk_hit_result_ar_id foreign key (ar_id) references analysis_result (id) on delete restrict on update restrict;
create index ix_hit_result_ar_id on hit_result (ar_id);

alter table process_step add constraint fk_process_step_ar_id foreign key (ar_id) references analysis_result (id) on delete restrict on update restrict;
create index ix_process_step_ar_id on process_step (ar_id);

alter table word_hit_info add constraint fk_word_hit_info_ar_id foreign key (ar_id) references analysis_result (id) on delete restrict on update restrict;
create index ix_word_hit_info_ar_id on word_hit_info (ar_id);

