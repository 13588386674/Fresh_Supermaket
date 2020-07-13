/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/12 17:41:01                           */
/*==============================================================*/


drop table if exists Address;

drop table if exists Coupon;

drop table if exists Customer;

drop table if exists FullAndGood;

drop table if exists FullInformation;

drop table if exists Good;

drop table if exists GoodAndRecipe;

drop table if exists GoodPurchase;

drop table if exists GoodType;

drop table if exists OrderDetai;

drop table if exists Orders;

drop table if exists Recipe;

drop table if exists SystemUser;

drop table if exists TimePromotion;

/*==============================================================*/
/* Table: Address                                               */
/*==============================================================*/
create table Address
(
   address_id           int not null,
   customer_id          varchar(20),
   province             varchar(20),
   city                 varchar(20),
   area                 varchar(50),
   address              varchar(50),
   connect_person       varchar(20),
   connect_number       varchar(50),
   primary key (address_id)
);

/*==============================================================*/
/* Table: Coupon                                                */
/*==============================================================*/
create table Coupon
(
   coupon_id            int not null,
   content              varchar(200),
   suitable_amount      real,
   credit_amount        real,
   start_date           timestamp,
   end_date             timestamp,
   primary key (coupon_id)
);

/*==============================================================*/
/* Table: Customer                                              */
/*==============================================================*/
create table Customer
(
   customer_id          varchar(20) not null,
   customer_name        varchar(50),
   customer_sex         varchar(10),
   password             varchar(20),
   customer_number      varchar(50),
   email                varchar(50),
   city                 varchar(50),
   create_date          timestamp,
   member_finish_date   timestamp,
   member               bool,
   primary key (customer_id)
);

/*==============================================================*/
/* Table: FullAndGood                                           */
/*==============================================================*/
create table FullAndGood
(
   good_id              int not null,
   full_reduction_id    int not null,
   start_date           timestamp,
   end_date             timestamp,
   primary key (good_id, full_reduction_id)
);

/*==============================================================*/
/* Table: FullInformation                                       */
/*==============================================================*/
create table FullInformation
(
   full_reduction_id    int not null,
   content              varchar(100),
   suitable_good_quantity int,
   discount             real,
   start_date           timestamp,
   end_date             timestamp,
   primary key (full_reduction_id)
);

/*==============================================================*/
/* Table: Good                                                  */
/*==============================================================*/
create table Good
(
   good_id              int not null,
   type_id              int,
   good_name            varchar(30),
   price                real,
   member_price         real,
   quantity             int,
   specification        varchar(200),
   detail               varchar(200),
   primary key (good_id)
);

/*==============================================================*/
/* Table: GoodAndRecipe                                         */
/*==============================================================*/
create table GoodAndRecipe
(
   good_id              int not null,
   recipe_id            int not null,
   content              varchar(200),
   primary key (good_id, recipe_id)
);

/*==============================================================*/
/* Table: GoodPurchase                                          */
/*==============================================================*/
create table GoodPurchase
(
   purchase_id          int not null,
   good_id              int,
   quantity             int,
   state                varchar(20),
   primary key (purchase_id)
);

/*==============================================================*/
/* Table: GoodType                                              */
/*==============================================================*/
create table GoodType
(
   type_id              int not null,
   type_name            varchar(30),
   detail               varchar(200),
   primary key (type_id)
);

/*==============================================================*/
/* Table: OrderDetai                                            */
/*==============================================================*/
create table OrderDetai
(
   order_id             int not null,
   good_id              int not null,
   quantity             int,
   price                real,
   discount             real,
   primary key (order_id, good_id)
);

/*==============================================================*/
/* Table: Orders                                                */
/*==============================================================*/
create table Orders
(
   order_id             int not null,
   coupon_id            int,
   customer_id          varchar(20),
   original_price       real,
   settlement_price     real,
   receive_time         timestamp,
   order_state          varchar(30),
   content              varchar(200),
   evaluate_date        timestamp,
   star                 varchar(10),
   primary key (order_id)
);

/*==============================================================*/
/* Table: Recipe                                                */
/*==============================================================*/
create table Recipe
(
   recipe_id            int not null,
   recipe_name          varchar(30),
   recipe_material      varchar(200),
   steps                varchar(200),
   detail               varchar(200),
   primary key (recipe_id)
);

/*==============================================================*/
/* Table: SystemUser                                            */
/*==============================================================*/
create table SystemUser
(
   system_user_id       varchar(20) not null,
   system_user_name     varchar(20),
   password             varchar(20),
   primary key (system_user_id)
);

/*==============================================================*/
/* Table: TimePromotion                                         */
/*==============================================================*/
create table TimePromotion
(
   promotion_id         int not null,
   good_id              int,
   price                real,
   quantity             int,
   start_date           timestamp,
   end_date             timestamp,
   primary key (promotion_id)
);

alter table Address add constraint FK_juzhu foreign key (customer_id)
      references Customer (customer_id) on delete restrict on update restrict;

alter table FullAndGood add constraint FK_FullAndGood foreign key (good_id)
      references Good (good_id) on delete restrict on update restrict;

alter table FullAndGood add constraint FK_FullAndGood2 foreign key (full_reduction_id)
      references FullInformation (full_reduction_id) on delete restrict on update restrict;

alter table Good add constraint FK_fenlei foreign key (type_id)
      references GoodType (type_id) on delete restrict on update restrict;

alter table GoodAndRecipe add constraint FK_GoodAndRecipe foreign key (good_id)
      references Good (good_id) on delete restrict on update restrict;

alter table GoodAndRecipe add constraint FK_GoodAndRecipe2 foreign key (recipe_id)
      references Recipe (recipe_id) on delete restrict on update restrict;

alter table GoodPurchase add constraint FK_goumai foreign key (good_id)
      references Good (good_id) on delete restrict on update restrict;

alter table OrderDetai add constraint FK_OrderDetai foreign key (order_id)
      references Orders (order_id) on delete restrict on update restrict;

alter table OrderDetai add constraint FK_OrderDetai2 foreign key (good_id)
      references Good (good_id) on delete restrict on update restrict;

alter table Orders add constraint FK_shiyong foreign key (coupon_id)
      references Coupon (coupon_id) on delete restrict on update restrict;

alter table Orders add constraint FK_xiadan foreign key (customer_id)
      references Customer (customer_id) on delete restrict on update restrict;

alter table TimePromotion add constraint FK_canyu foreign key (good_id)
      references Good (good_id) on delete restrict on update restrict;

