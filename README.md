# Mikoto-Pixiv

Mikoto-Pixiv 是一个使用Java语言编写，能够在全平台下运行的，提供 [pixiv](https://www.pixiv.net) 本地管理、分发库的项目

项目名称来源于:

《魔法禁书目录》及《科学超电磁炮》中人物: 御坂**美琴**(みさか **みこと**) Misaka **Mikoto**

## 1 pixiv-main

请在 [release](https://github.com/mikoto2464/pixiv-main/releases) 下载

启动参数:

```
-t 生成模板配置文件
-l <crawlerName> 从crawler目录下的<crawlerName>.crawler加载crawler
-c <crawlerName> 从config目录下的<crawlerName>.crawler.properties新建crawler
```

### 2 database

#### 2.1 pixiv-web-database

此数据库并不会存储任何的pixiv作品数据 此数据库将会存储您的设备验证信息 web端账号信息等

我们目前只支持使用Mysql进行部署

数据表模板:

```mysql
create table pixiv_web_data.user_data
(
    pk_id         bigint unsigned auto_increment
        primary key,
    user_name     varchar(20)  not null,
    user_password varchar(64)  not null,
    user_salt     varchar(10)  not null,
    user_key      varchar(64)  not null,
    profile_url   varchar(512) null,
    create_time   datetime     not null,
    update_time   datetime     not null,
    constraint user_data_pk_id_uindex
        unique (pk_id),
    constraint user_data_user_key_uindex
        unique (user_key),
    constraint user_data_user_name_uindex
        unique (user_name)
);
```

#### 2.2 pixiv-database

此数据库有且只有一个用途,便是存储所有的pixiv数据

同样的 我们只支持使用Mysql部署

数据库模板:

```mysql
create table pixiv_data.bookmark_0_1000
(
    pk_artwork_id       bigint         not null
        primary key,
    artwork_title       varchar(32)    null,
    author_id           bigint         not null,
    author_name         varchar(100)   not null,
    description         varchar(10000) null,
    tags                varchar(309)   null,
    illust_url_mini     varchar(100)   null,
    illust_url_thumb    varchar(100)   null,
    illust_url_small    varchar(100)   null,
    illust_url_regular  varchar(100)   null,
    illust_url_original varchar(100)   null,
    page_count          int            null,
    bookmark_count      int            null,
    like_count          int            null,
    view_count          int            null,
    grading             tinyint        not null,
    update_date         datetime       null,
    create_date         datetime       null,
    crawl_date          datetime       null,
    constraint bookmark_under_1000_pk_artwork_id_uindex
        unique (pk_artwork_id)
);
```

### 3 pixiv-forward

请见仓库 [pixiv-forward](https://github.com/mikoto2464/pixiv-forward)

### 4 pixiv-displayer

请见仓库 [pixiv-displayer](https://github.com/mikoto2464/pixiv-displayer)

### 5 pixiv-engine

请见仓库 [pixiv-engine](https://github.com/mikoto2464/pixiv-engine)

### 5 pixiv-api

请见仓库 [pixiv-api](https://github.com/mikoto2464/pixiv-api)

### 5 扩展

#### 5.1 JPBC(Java Pixiv Bot Connectivity)

JPBC基于http协议 使pixiv-main得以向其它通讯软件 提供服务 具体文档请见: [docs](https://jpbc.docs.mikoto.net.cn) (文档还未制作完成)

配置它的方法很简单 只要在 [release](https://github.com/mikoto2464/pixiv/releases) 中下载到相应的JPBC插件,后在机器人服务端上安装此插件
接着在pixiv-main的config文件中打开对应的功能即可.

## 声明

### 一切开发旨在学习，请勿用于非法用途

- Mikoto-Pixiv 是完全免费且开放源代码的软件，仅供学习和娱乐用途使用
- Mikoto-Pixiv 不会通过任何方式强制收取费用，或对使用者提出物质条件
- Mikoto-Pixiv 由整个开源社区维护，并不是属于某个个体的作品，所有贡献者都享有其作品的著作权。

### 许可证

    Copyright (C) 2021-2021 Mikoto and contributors.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

`mikoto-pixiv` 采用 `AGPLv3` 协议开源。为了整个社区的良性发展，我们**强烈建议**您做到以下几点：

- **间接接触（包括但不限于使用 `Http API` 或 跨进程技术）到 `mikoto-pixiv` 的软件使用 `AGPLv3` 开源**
- **不鼓励，不支持一切商业使用**

鉴于项目的特殊性，开发团队可能在任何时间**停止更新**或**删除项目**。