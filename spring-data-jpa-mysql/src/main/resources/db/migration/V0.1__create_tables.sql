-- create tables

-- t_book 图书表
CREATE TABLE `t_book` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `book_name` varchar(256)  NOT NULL COMMENT '书名',
    `isbn` varchar(32) NOT NULL COMMENT '国际标准书号ISBN',
    `description` varchar(256)  COMMENT '描述',
    `price` decimal(10,2) unsigned NOT NULL COMMENT '价格',
    `publish_date` date NOT NULL COMMENT '出版日期',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '软删除标识：0 有效， 1 已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_isbn` (`isbn`) USING BTREE,
    KEY `idx_book_name` (`book_name`) USING BTREE
) COMMENT='图书表';

-- t_author 作者表
CREATE TABLE `t_author` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `first_name` varchar(64)  NOT NULL COMMENT '姓',
    `last_name` varchar(64) NOT NULL COMMENT '名',
    `full_name` varchar(128) NOT NULL COMMENT '全名',
    `nationality` varchar(32) COMMENT '国籍编码',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '软删除标识：0 有效， 1 已删除',
    PRIMARY KEY (`id`),
    KEY `idx_full_name` (`full_name`) USING BTREE
) COMMENT='作者表';

-- t_publisher 出版社表
CREATE TABLE `t_publisher` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `publisher_name` varchar(128)  NOT NULL COMMENT '出版社名称',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '软删除标识：0 有效， 1 已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_publisher_name` (`publisher_name`) USING BTREE
) COMMENT='出版社表';

-- t_book_author 图书与作者关联表
CREATE TABLE `t_book_author` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `book_id` bigint(20) unsigned NOT NULL,
    `author_id` bigint(20) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_book_author` (`book_id`, `author_id`) USING BTREE
) COMMENT='图书与作者关联表';

-- t_book_publisher 图书与出版社关联表
CREATE TABLE `t_book_publisher` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `book_id` bigint(20) unsigned NOT NULL,
    `publisher_id` bigint(20) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_book_publisher` (`book_id`, `publisher_id`) USING BTREE
) COMMENT='图书与出版社关联表';



