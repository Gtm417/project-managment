create table interested_in_project (project_id bigint not null, user_id bigint not null, notification_enabled bit, primary key (project_id, user_id)) engine=InnoDB;
create table project_members (id bigint not null auto_increment, added_at datetime(6), project_role varchar(255), project_id bigint, user_id bigint, primary key (id)) engine=InnoDB;
create table projects (id bigint not null auto_increment, category varchar(255), create_date datetime(6), description varchar(255), commercial bit, private bit, name varchar(255), final_planned_date datetime(6), start_date datetime(6), project_status varchar(255), primary key (id)) engine=InnoDB;
create table skills (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table user_skills (skill_id bigint not null, user_id bigint not null, expertise varchar(255), primary key (skill_id, user_id)) engine=InnoDB;
create table users (id bigint not null auto_increment, cv tinyblob, description varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255), picture tinyblob, role varchar(255), user_status varchar(255), user_type varchar(255), primary key (id)) engine=InnoDB;
create table vacancies (id bigint not null auto_increment, about_project varchar(255), contact varchar(255), description varchar(255), expected varchar(255), job_position varchar(255), project_id bigint, primary key (id)) engine=InnoDB;
create table vacancy_subscribers (vacancy_id bigint not null, user_id bigint not null, primary key (vacancy_id, user_id)) engine=InnoDB;
alter table project_members add constraint UKaydweb1re2g5786xaugww4u0 unique (user_id, project_id);
alter table projects add constraint UK_1e447b96pedrvtxw44ot4qxem unique (name);
alter table skills add constraint UK_85woe63nu9klkk9fa73vf0jd0 unique (name);
alter table interested_in_project add constraint FK8m4u5m2mkwnexf9drlvr0oucb foreign key (project_id) references projects (id);
alter table interested_in_project add constraint FKjch954gttj0pjrl6sf0t1tu5w foreign key (user_id) references users (id);
alter table project_members add constraint FKdki1sp2homqsdcvqm9yrix31g foreign key (project_id) references projects (id);
alter table project_members add constraint FKgul2el0qjk5lsvig3wgajwm77 foreign key (user_id) references users (id);
alter table user_skills add constraint FKh223y61gwijpgqt6nlsuti07g foreign key (skill_id) references skills (id);
alter table user_skills add constraint FKro13if9r7fwkr5115715127ai foreign key (user_id) references users (id);
alter table vacancies add constraint FKakmyq19bnp78hk4vuv4snwlp4 foreign key (project_id) references projects (id);
alter table vacancy_subscribers add constraint FKwsgqxf9t3acs33rfwonhghke foreign key (user_id) references users (id);
alter table vacancy_subscribers add constraint FK4wy6687wlgvvg4hod32qw2qs8 foreign key (vacancy_id) references vacancies (id);
