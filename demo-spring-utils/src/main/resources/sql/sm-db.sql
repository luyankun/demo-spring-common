/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/3/1 10:54:57                            */
/*==============================================================*/


drop table if exists merchant_info;

drop table if exists merchant_info_log;

drop table if exists merchant_users_info;

drop table if exists product_category;

drop table if exists product_goods;

drop table if exists product_goods_images;

drop table if exists product_goods_log;

drop table if exists purchase_order;

drop table if exists purchase_order_goods;

drop table if exists purchase_order_goods_log;

drop table if exists purchase_order_log;

drop table if exists purchase_put_real;

drop table if exists purchase_request;

drop table if exists purchase_request_goods;

drop table if exists purchase_request_goods_log;

drop table if exists purchase_request_log;

drop table if exists put_warehouse;

drop table if exists put_warehouse_goods;

drop table if exists put_warehouse_goods_log;

drop table if exists put_warehouse_log;

drop table if exists supplier_goods_cost;

drop table if exists supplier_goods_cost_log;

drop table if exists supplier_goods_real;

drop table if exists supplier_images;

drop table if exists supplier_info;

drop table if exists supplier_info_log;

drop table if exists supplier_users_info;

drop table if exists sys_area;

drop table if exists sys_menu;

drop table if exists sys_role;

drop table if exists sys_role_menu;

drop table if exists sys_user;

drop table if exists sys_user_role;

/*==============================================================*/
/* Table: merchant_info                                         */
/*==============================================================*/
create table merchant_info
(
   id                   bigint(20) not null comment '编号id',
   merchant_code        varchar(30) not null comment '编号',
   name                 varchar(10) not null comment '名称',
   area_id              bigint(11) not null comment '区域编号',
   address              varchar(30) not null comment '地址',
   status               int(2) not null comment '状态:0，禁用/删除，1，有效',
   merchant_type        int(2) not null comment '类型:1、仓库2、店铺',
   remark               varchar(100) comment '备注',
   create_time          timestamp not null comment '创建时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table merchant_info comment '店铺仓库表';

/*==============================================================*/
/* Table: merchant_info_log                                     */
/*==============================================================*/
create table merchant_info_log
(
   id                   bigint(20) not null comment '主键',
   merchant_info_id     bigint(11) not null comment '店铺仓库编号',
   operation_record     varchar(255) not null comment '操作记录',
   operation_id         bigint(20) not null comment '操作人id',
   operation_name       varchar(50) not null comment '操作人姓名',
   operation_time       datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
   remark               varchar(255) comment '备注',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table merchant_info_log comment '店铺仓库修改记录表';

/*==============================================================*/
/* Table: merchant_users_info                                   */
/*==============================================================*/
create table merchant_users_info
(
   id                   bigint(11) not null auto_increment comment '编号',
   merchant_id          bigint(20) comment '商家表编号',
   name                 varchar(20) not null comment '姓名',
   phone_number         varchar(15) not null comment '手机号码',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table merchant_users_info comment '店铺仓库联系人信息';

/*==============================================================*/
/* Table: product_category                                      */
/*==============================================================*/
create table product_category
(
   id                   bigint(11) not null auto_increment comment '编号',
   category_name        varchar(30) not null comment '品类名称',
   level                int(2) not null comment '品类级别',
   parent_id            bigint(20) not null comment '父级编号',
   status               int(2) not null comment '状态',
   create_time          datetime not null comment '创建时间',
   category_code        bigint(20) not null comment '品类编码',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table product_category comment '品类表，树的级别在存储结构上体现。静态表，可缓存';

/*==============================================================*/
/* Table: product_goods                                         */
/*==============================================================*/
create table product_goods
(
   global_id            bigint(11) not null comment '商品id',
   category_id          bigint(20) not null comment '品类id',
   goods_name           varchar(30) not null comment '商品名称',
   remark               varchar(100) not null comment '商品描述',
   bar_code             varchar(20) not null comment '条形码',
   specification        varchar(10) not null comment '规格',
   model_number         varchar(20) not null comment '型号',
   private_type         int(2) comment '计量单位',
   status               int(2) not null comment '状态',
   create_time          timestamp not null comment '创建时间',
   primary key (global_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table product_goods comment '商品基本信息表';

/*==============================================================*/
/* Table: product_goods_images                                  */
/*==============================================================*/
create table product_goods_images
(
   id                   bigint(11) not null auto_increment comment '编号',
   goods_id             bigint(11) comment '商品id',
   image_url            varchar(50) not null comment '图片地址',
   create_time          timestamp not null comment '创建时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table product_goods_images comment '商品图片';

/*==============================================================*/
/* Table: product_goods_log                                     */
/*==============================================================*/
create table product_goods_log
(
   id                   bigint(20) not null auto_increment comment '主键',
   product_goods_global_id bigint(11) not null comment '商品表id',
   operation_record     varchar(255) not null comment '操作记录',
   operation_id         bigint(20) not null comment '操作人id',
   operation_name       varchar(50) not null comment '操作人姓名',
   operation_time       datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
   remark               varchar(255) comment '备注',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table product_goods_log comment '商品基本信息表修改记录表';

/*==============================================================*/
/* Table: purchase_order                                        */
/*==============================================================*/
create table purchase_order
(
   global_id            bigint(11) not null comment '采购单id',
   purchase_request_id  bigint(11) not null comment '采购申请单id',
   supplier_id          bigint(11) not null comment '供应商id',
   proposer             varchar(20) comment '申请人',
   phone_number         varchar(15) comment '电话',
   order_code           varchar(20) comment '采购单编号',
   merchant_id          bigint(11) comment '商家编号',
   receive_address      varchar(50) comment '收货地址',
   receive_name         varchar(20) comment '收货人姓名',
   receive_phone        varchar(15) comment '收货人电话',
   status               int(2) comment '状态',
   create_time          timestamp comment '申请时间',
   finish_time          datetime comment '完结时间',
   remark               varchar(50) comment '备注',
   primary key (global_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table purchase_order comment '采购单';

/*==============================================================*/
/* Table: purchase_order_goods                                  */
/*==============================================================*/
create table purchase_order_goods
(
   global_id            bigint(11) not null comment '采购商品id',
   purchase_order_id    bigint(11) comment '采购单表id',
   goods_id             bigint(11) not null comment '商品编号',
   category_id          bigint(20) not null comment '品类编号',
   amount               decimal(10,2) comment '价格',
   apply_count          int(3) not null comment '申请数量',
   create_time          timestamp not null comment '申请日期',
   status               int(2) comment '状态',
   primary key (global_id)
);

alter table purchase_order_goods comment '采购商品表';

/*==============================================================*/
/* Table: purchase_order_goods_log                              */
/*==============================================================*/
create table purchase_order_goods_log
(
   id                   bigint(20) not null auto_increment comment '主键',
   purchase_order_goods_global_id bigint(11) not null comment '采购商品表id',
   operation_record     varchar(255) not null comment '操作记录',
   operation_id         bigint(20) not null comment '操作人id',
   operation_name       varchar(50) not null comment '操作人姓名',
   operation_time       datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
   remark               varchar(255) comment '备注',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table purchase_order_goods_log comment '采购商品修改记录表';

/*==============================================================*/
/* Table: purchase_order_log                                    */
/*==============================================================*/
create table purchase_order_log
(
   id                   bigint(20) not null auto_increment comment '主键',
   purchase_order_global_id bigint(11) not null comment '采购单表id',
   operation_record     varchar(255) not null comment '操作记录',
   operation_id         bigint(20) not null comment '操作人id',
   operation_name       varchar(50) not null comment '操作人姓名',
   operation_time       datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
   remark               varchar(255) comment '备注',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table purchase_order_log comment '采购单修改记录表';

/*==============================================================*/
/* Table: purchase_put_real                                     */
/*==============================================================*/
create table purchase_put_real
(
   purchase_order_id    bigint(11) comment '采购单id',
   put_warehouse_id     bigint(11) comment '入库单id'
);

alter table purchase_put_real comment '采购单入库单关系表';

/*==============================================================*/
/* Table: purchase_request                                      */
/*==============================================================*/
create table purchase_request
(
   global_id            bigint(11) not null comment '采购申请单id',
   proposer             varchar(20) comment '申请人',
   phone_number         varchar(15) comment '电话',
   order_code           varchar(20) comment '采购申请单编号',
   merchant_id          bigint(11) comment '商家编号',
   receive_address      varchar(50) comment '收货地址',
   receive_name         varchar(20) comment '收货人姓名',
   receive_phone        varchar(15) comment '收货人电话',
   status               int(2) comment '审核状态',
   create_time          timestamp comment '申请时间',
   finish_time          datetime comment '完结时间',
   remark               varchar(50) comment '备注',
   primary key (global_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table purchase_request comment '采购申请单';

/*==============================================================*/
/* Table: purchase_request_goods                                */
/*==============================================================*/
create table purchase_request_goods
(
   global_id            bigint(11) not null comment '采购申请商品id',
   purchase_request_id  bigint(11) comment '采购申请表id',
   goods_id             bigint(11) not null comment '商品编号',
   category_id          bigint(20) not null comment '品类编号',
   apply_count          int(3) not null comment '申请数量',
   create_time          timestamp not null comment '申请日期',
   primary key (global_id)
);

alter table purchase_request_goods comment '采购申请商品表';

/*==============================================================*/
/* Table: purchase_request_goods_log                            */
/*==============================================================*/
create table purchase_request_goods_log
(
   id                   bigint(20) not null auto_increment comment '主键',
   purchase_request_goods_global_id bigint(11) not null comment '采购申请商品表id',
   operation_record     varchar(255) not null comment '操作记录',
   operation_id         bigint(20) not null comment '操作人id',
   operation_name       varchar(50) not null comment '操作人姓名',
   operation_time       datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
   remark               varchar(255) comment '备注',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table purchase_request_goods_log comment '采购申请商品修改记录表';

/*==============================================================*/
/* Table: purchase_request_log                                  */
/*==============================================================*/
create table purchase_request_log
(
   id                   bigint(20) not null auto_increment comment '主键',
   purchase_request_global_id bigint(11) not null comment '采购申请表id',
   operation_record     varchar(255) not null comment '操作记录',
   operation_id         bigint(20) not null comment '操作人id',
   operation_name       varchar(50) not null comment '操作人姓名',
   operation_time       datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
   remark               varchar(255) comment '备注',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table purchase_request_log comment '采购申请单修改记录表';

/*==============================================================*/
/* Table: put_warehouse                                         */
/*==============================================================*/
create table put_warehouse
(
   global_id            bigint(11) not null comment '入库单id',
   supplier_id          bigint(11) not null comment '供应商id',
   order_code           varchar(10) comment '入库单编号',
   proposer             varchar(20) comment '申请人',
   phone_number         varchar(15) comment '电话',
   merchant_id          bigint(11) comment '商家编号',
   receive_address      varchar(50) comment '收货地址',
   receive_name         varchar(20) comment '收货人姓名',
   receive_phone        varchar(15) comment '收货人电话',
   status               int(2) comment '状态',
   create_time          timestamp comment '申请时间',
   remark               varchar(50) comment '备注',
   primary key (global_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table put_warehouse comment '入库单';

/*==============================================================*/
/* Table: put_warehouse_goods                                   */
/*==============================================================*/
create table put_warehouse_goods
(
   global_id            bigint(11) not null comment '采购商品id',
   purchase_order_id    bigint(11) comment '采购单表id',
   goods_id             bigint(11) not null comment '商品编号',
   category_id          bigint(20) not null comment '品类编号',
   purchase_count       int(3) not null comment '采购数量',
   put_warehouse_count  int(3) comment '入库数量',
   create_time          timestamp not null comment '申请日期',
   status               int(2) comment '状态',
   pay_status           int(2) comment '财务状态',
   primary key (global_id)
);

alter table put_warehouse_goods comment '入库单商品表';

/*==============================================================*/
/* Table: put_warehouse_goods_log                               */
/*==============================================================*/
create table put_warehouse_goods_log
(
   id                   bigint(20) not null auto_increment comment '主键',
   put_warehouse_goods_global_id bigint(11) not null comment '入库单商品表id',
   operation_record     varchar(255) not null comment '操作记录',
   operation_id         bigint(20) not null comment '操作人id',
   operation_name       varchar(50) not null comment '操作人姓名',
   operation_time       datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
   remark               varchar(255) comment '备注',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table put_warehouse_goods_log comment 'put_warehouse_goods';

/*==============================================================*/
/* Table: put_warehouse_log                                     */
/*==============================================================*/
create table put_warehouse_log
(
   id                   bigint(20) not null auto_increment comment '主键',
   put_warehouse_global_id bigint(11) not null comment '入库单表id',
   operation_record     varchar(255) not null comment '操作记录',
   operation_id         bigint(20) not null comment '操作人id',
   operation_name       varchar(50) not null comment '操作人姓名',
   operation_time       datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
   remark               varchar(255) comment '备注',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table put_warehouse_log comment '入库单修改记录表';

/*==============================================================*/
/* Table: supplier_goods_cost                                   */
/*==============================================================*/
create table supplier_goods_cost
(
   id                   bigint(11) not null auto_increment comment '价格id',
   cost_type            int(2) not null comment '费用类型',
   cost_money           decimal(10,2) not null comment '金额',
   price_unit           int(2) comment '计价单位:1 数量 -个2 直径-厘米 3 面积-平方米 4 面积-平方厘米 5 延米-米 6 延米-厘米',
   price_type           int(2) comment '计价方式:1 个数计价 2范围 3 起步价',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table supplier_goods_cost comment '商品价格表，保存商品基本价格信息';

/*==============================================================*/
/* Table: supplier_goods_cost_log                               */
/*==============================================================*/
create table supplier_goods_cost_log
(
   id                   bigint(20) not null comment '主键',
   supplier_goods_cost_id bigint(11) not null comment '供应商商品价格表id',
   operation_record     varchar(255) not null comment '操作记录',
   operation_id         bigint(20) not null comment '操作人id',
   operation_name       varchar(50) not null comment '操作人姓名',
   operation_time       datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
   remark               varchar(255) comment '备注',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table supplier_goods_cost_log comment '商品价格表，保存商品基本价格信息修改记录表';

/*==============================================================*/
/* Table: supplier_goods_real                                   */
/*==============================================================*/
create table supplier_goods_real
(
   supplier_id          bigint(11) not null comment '供应商id',
   goods_id             bigint(11) not null comment '商品id',
   cost_id              bigint(11) not null comment '价格id'
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table supplier_goods_real comment '供应商商品关系表';

/*==============================================================*/
/* Table: supplier_images                                       */
/*==============================================================*/
create table supplier_images
(
   id                   bigint(11) not null auto_increment comment '编号',
   supplier_id          bigint(11) comment '供应商编号',
   image_url            varchar(50) not null comment '图片地址',
   image_type           int(2) not null comment '图片类型',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table supplier_images comment '供应商图片表';

/*==============================================================*/
/* Table: supplier_info                                         */
/*==============================================================*/
create table supplier_info
(
   global_id            bigint(11) not null comment '编号',
   name                 varchar(20) not null comment '名称',
   logogram             varchar(20) comment '简称',
   supplier_type        int(2) not null comment '1,全国;2,区域;3,店铺',
   social_code          varchar(20) comment '社会编码',
   register_num         varchar(20) comment '注册号',
   organizational_code  varchar(20) comment '组织机构代码',
   create_time          timestamp comment '创建时间',
   settlement_method    int(2) comment '结算方式',
   repayment_day        int(3) comment '付款天数',
   primary key (global_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table supplier_info comment '供应商信息表';

/*==============================================================*/
/* Table: supplier_info_log                                     */
/*==============================================================*/
create table supplier_info_log
(
   id                   bigint(20) not null auto_increment comment '主键',
   supplier_info_global_id bigint(11) not null comment '供应商编号',
   operation_record     varchar(255) not null comment '操作记录',
   operation_id         bigint(20) not null comment '操作人id',
   operation_name       varchar(50) not null comment '操作人姓名',
   operation_time       datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
   remark               varchar(255) comment '备注',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table supplier_info_log comment '供应商信息修改记录表';

/*==============================================================*/
/* Table: supplier_users_info                                   */
/*==============================================================*/
create table supplier_users_info
(
   id                   bigint(11) not null comment '编号',
   supplier_id          bigint(11) comment '1、法人 2、联系人',
   name                 varchar(20) not null comment '姓名',
   phone_number         varchar(15) not null comment '手机号',
   identity             varchar(18) comment '身份证号',
   user_type            int(2) not null comment '用户类型',
   bank_name            varchar(20) comment '开户行',
   bank_card            int(20) comment '卡号',
   company_name         varchar(20) comment '名称',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table supplier_users_info comment '供应商人员信息表';

/*==============================================================*/
/* Table: sys_area                                              */
/*==============================================================*/
create table sys_area
(
   id                   bigint(11) not null comment '编号',
   code                 bigint(11) not null comment '字典编码id',
   parent               bigint(11) not null comment '父级',
   level                int(2) not null comment '级别',
   state                boolean not null comment '状态',
   value                varchar(100) not null comment '字典编码',
   full_name            varchar(100) comment '全名',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table sys_area comment '该表参照字典表建立';

/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
create table sys_menu
(
   id                   bigint(20) not null comment '编号',
   menu_name            varchar(20) comment '菜单名称',
   menu_code            varchar(20) comment '菜单编码',
   menu_path            varchar(100) comment '菜单路径',
   menu_type            int(1) comment '菜单类型: 1.菜单, 2.按钮',
   parent_id            bigint(20) comment '父级ID',
   sort                 int(2) comment '排序序号',
   level                int(2) comment '菜单层级',
   deleted              int(1) comment '是否删除:1.正常, 2.删除',
   disabled             int(1) comment '是否禁用:1.正常, 2.禁用',
   remark               varchar(100) comment '备注',
   create_date          timestamp comment '创建时间',
   update_date          timestamp comment '最后一次修改时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table sys_menu comment '系统菜单表';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   bigint(20) not null comment '编号',
   role_name            varchar(20) comment '角色名称',
   role_code            varchar(20) comment '角色编码',
   deleted              int(1) comment '是否删除:1.正常, 2.删除',
   disabled             int(1) comment '是否禁用:1.正常, 2.禁用',
   remark               varchar(100) comment '备注',
   create_date          timestamp comment '创建时间',
   update_date          timestamp comment '最后一次修改时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table sys_role comment '角色信息表';

/*==============================================================*/
/* Table: sys_role_menu                                         */
/*==============================================================*/
create table sys_role_menu
(
   id                   bigint(20) not null comment '编号',
   role_id              bigint(20) comment '角色编号',
   menu_id              bigint(20) comment '菜单编号',
   deleted              int(1) comment '是否删除:1.正常, 2.删除',
   disabled             int(1) comment '是否禁用:1.正常, 2.禁用',
   remark               varchar(100) comment '备注',
   create_date          timestamp comment '创建时间',
   update_date          timestamp comment '最后一次修改时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table sys_role_menu comment '系统角色菜单关系表';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   bigint(20) not null auto_increment comment '编号',
   login_name           varchar(20) comment '登录账号',
   user_name            varchar(20) comment '用户名称',
   password             varchar(50) comment '密码',
   salt                 varchar(50) comment '密码时间戳',
   deleted              int(1) comment '是否删除:1.正常, 2.删除',
   disabled             int(1) comment '是否禁用:1.正常, 2.禁用',
   remark               varchar(100) comment '备注',
   create_date          timestamp comment '创建时间',
   update_date          timestamp comment '最后一次修改时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table sys_user comment '用户信息表';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   id                   bigint(20) not null comment '编号',
   user_id              bigint(20) comment '用户编号',
   role_id              bigint(20) comment '角色编号',
   deleted              int(1) comment '是否删除:1.正常, 2.删除',
   disabled             int(1) comment '是否禁用:1.正常, 2.禁用',
   remark               varchar(100) comment '备注',
   create_date          timestamp comment '创建时间',
   update_date          timestamp comment '最后一次修改时间',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table sys_user_role comment '系统用户角色关系表';

alter table merchant_info add constraint FK_Reference_11 foreign key (area_id)
      references sys_area (id) on delete restrict on update restrict;

alter table merchant_info_log add constraint FK_Reference_27 foreign key (merchant_info_id)
      references merchant_info (id) on delete restrict on update restrict;

alter table merchant_users_info add constraint FK_merchant_id foreign key (merchant_id)
      references merchant_info (id) on delete restrict on update restrict;

alter table product_goods add constraint FK_Reference_6 foreign key (category_id)
      references product_category (id) on delete restrict on update restrict;

alter table product_goods_images add constraint FK_Reference_7 foreign key (goods_id)
      references product_goods (global_id) on delete restrict on update restrict;

alter table product_goods_log add constraint FK_Reference_25 foreign key (product_goods_global_id)
      references product_goods (global_id) on delete restrict on update restrict;

alter table purchase_order add constraint FK_Reference_20 foreign key (purchase_request_id)
      references purchase_request (global_id) on delete restrict on update restrict;

alter table purchase_order_goods add constraint FK_Reference_34 foreign key (purchase_order_id)
      references purchase_order (global_id) on delete restrict on update restrict;

alter table purchase_order_goods_log add constraint FK_Reference_32 foreign key (purchase_order_goods_global_id)
      references purchase_order_goods (global_id) on delete restrict on update restrict;

alter table purchase_order_log add constraint FK_Reference_29 foreign key (purchase_order_global_id)
      references purchase_order (global_id) on delete restrict on update restrict;

alter table purchase_put_real add constraint FK_Reference_22 foreign key (purchase_order_id)
      references purchase_order (global_id) on delete restrict on update restrict;

alter table purchase_put_real add constraint FK_Reference_23 foreign key (put_warehouse_id)
      references put_warehouse (global_id) on delete restrict on update restrict;

alter table purchase_request_goods add constraint FK_Reference_15 foreign key (purchase_request_id)
      references purchase_request (global_id) on delete restrict on update restrict;

alter table purchase_request_goods_log add constraint FK_Reference_31 foreign key (purchase_request_goods_global_id)
      references purchase_request_goods (global_id) on delete restrict on update restrict;

alter table purchase_request_log add constraint FK_Reference_30 foreign key (purchase_request_global_id)
      references purchase_request (global_id) on delete restrict on update restrict;

alter table put_warehouse_goods add constraint FK_Reference_21 foreign key (purchase_order_id)
      references put_warehouse (global_id) on delete restrict on update restrict;

alter table put_warehouse_goods_log add constraint FK_Reference_33 foreign key (put_warehouse_goods_global_id)
      references put_warehouse_goods (global_id) on delete restrict on update restrict;

alter table put_warehouse_log add constraint FK_Reference_28 foreign key (put_warehouse_global_id)
      references put_warehouse (global_id) on delete restrict on update restrict;

alter table supplier_goods_cost_log add constraint FK_Reference_24 foreign key (supplier_goods_cost_id)
      references supplier_goods_cost (id) on delete restrict on update restrict;

alter table supplier_goods_real add constraint FK_Reference_16 foreign key (supplier_id)
      references supplier_info (global_id) on delete restrict on update restrict;

alter table supplier_goods_real add constraint FK_Reference_17 foreign key (goods_id)
      references product_goods (global_id) on delete restrict on update restrict;

alter table supplier_goods_real add constraint FK_Reference_18 foreign key (cost_id)
      references supplier_goods_cost (id) on delete restrict on update restrict;

alter table supplier_images add constraint FK_Reference_8 foreign key (supplier_id)
      references supplier_info (global_id) on delete restrict on update restrict;

alter table supplier_info_log add constraint FK_Reference_26 foreign key (supplier_info_global_id)
      references supplier_info (global_id) on delete restrict on update restrict;

alter table supplier_users_info add constraint FK_Reference_9 foreign key (supplier_id)
      references supplier_info (global_id) on delete restrict on update restrict;

alter table sys_role_menu add constraint FK_角色菜单关系 foreign key (menu_id)
      references sys_menu (id) on delete restrict on update restrict;

alter table sys_role_menu add constraint FK_角色菜单关系 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

alter table sys_user_role add constraint FK_用户角色关系 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

alter table sys_user_role add constraint FK_用户角色关系 foreign key (user_id)
      references sys_user (id) on delete restrict on update restrict;

