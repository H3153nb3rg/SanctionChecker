alter table analysis_result drop constraint if exists fk_analysis_result_msg_id;

alter table hit_result drop constraint if exists fk_hit_result_ar_id;
drop index if exists ix_hit_result_ar_id;

alter table process_step drop constraint if exists fk_process_step_ar_id;
drop index if exists ix_process_step_ar_id;

alter table word_hit_info drop constraint if exists fk_word_hit_info_ar_id;
drop index if exists ix_word_hit_info_ar_id;

drop table if exists analysis_result;
drop sequence if exists analysis_result_seq;

drop table if exists hit_result;
drop sequence if exists hit_result_seq;

drop table if exists message;
drop sequence if exists message_seq;

drop table if exists message_content;
drop sequence if exists message_content_seq;

drop table if exists process_step;
drop sequence if exists process_step_seq;

drop table if exists wl_address;
drop sequence if exists wl_address_seq;

drop table if exists wl_entity;

drop table if exists wl_name;
drop sequence if exists wl_name_seq;

drop table if exists wl_passport;
drop sequence if exists wl_passport_seq;

drop table if exists word_hit_info;
drop sequence if exists word_hit_info_seq;

