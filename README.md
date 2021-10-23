# Mikoto-Pixiv

Mikoto-Pixiv 是一个使用Java语言编写，能够在全平台下运行的，提供 [pixiv](https://www.pixiv.net) 本地管理、分发库的项目

项目名称来源于:

《魔法禁书目录》及《科学超电磁炮》中人物: 御坂**美琴**(みさか **みこと**) Misaka **Mikoto**

## 配置

### 1 database

#### 1.1 pixiv-web-database

此数据库并不会存储任何的pixiv作品数据 此数据库将会存储您的设备验证信息 web端账号信息等

我们目前只支持使用Mysql进行部署

数据表模板请到 [release](https://github.com/mikoto2464/pixiv/releases) 下载

#### 1.2 pixiv-database

此数据库有且只有一个用途,便是存储所有的pixiv数据

同样的 我们只支持使用Mysql部署 在 [release](https://github.com/mikoto2464/pixiv/releases) 下载数据表模板

### 2 pixiv-forward

为了规避pixiv的反爬虫机制,同时为了应对中国特殊的网络环境,我们使用pixiv-forward进行pixiv数据的转发

我们不建议您将pixiv-forward项目部署在中国大陆地区(懂得都懂)

部署pixiv-forward的方法也很简单,您只需要在 [release](https://github.com/mikoto2464/pixiv/releases) 页面下载此项目的jar包
并将其下载到您的目标服务器 接着执行以下命令
```bash
nohup java -jar pixiv-forward-1.0.0.jar [ip] [port] [userName] [password] > pixiv-forward.log 2>&1 &
```

上面命令中的
```bash
[ip] [port] [userName] [password]
```
分别为您的pixiv-web-database的 ip 端口 用户名 密码
请正确填写

### 3 pixiv-main

(项目还未制作完毕)

### 4 扩展

#### 4.1 JPBC(Java Pixiv Bot Connectivity)

JPBC基于http协议 使pixiv-main得以向其它通讯软件 提供服务
具体文档请见: [docs](https://jpbc.docs.mikoto.net.cn) (文档还未制作完成)

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

## 使用到的开源项目
- [mirai](https://github.com/mamoe/mirai)
- [fastjson](https://github.com/alibaba/fastjson)
- [okhttp](https://github.com/square/okhttp)
- [tomcat](https://github.com/apache/tomcat)
- [spring-boot](https://github.com/spring-projects/spring-boot)

可能还有没提到的项目 欢迎补充