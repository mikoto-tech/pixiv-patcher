# Mikoto-Pixiv-Patcher

[![wakatime](https://wakatime.com/badge/user/1881dd28-2018-456f-8c50-e897127472e4/project/966b4fb2-011d-44d4-86fd-58667e14cf96.svg)](https://wakatime.com/badge/user/1881dd28-2018-456f-8c50-e897127472e4/project/966b4fb2-011d-44d4-86fd-58667e14cf96)

`Mikoto-Pixiv-Patcher` 是为了

1. 储存Artwork数据
2. 从 [Pixiv-Forward](https://github.com/mikoto-tech/pixiv-forward) 获取数据

## 如何使用?

### Step.1

部署Pixiv-Patcher的方法很简单, 您只需要在 [release](https://github.com/mikoto2464/pixiv-patcher/releases) 页面将jar包其下载到您的目标服务器

### Step.2

执行以下命令(需提前安装[Java](https://openjdk.java.net/))

```bash
java -jar pixiv-patcher-(version).jar
```

### Step.3

程序此时会自动在当前目录生成 `config` 文件夹, 并在其中创建 `default_config.properties` 文件.

### Step.4

此时 您只需要重新执行

```bash
java -jar pixiv-patcher-(version).jar
```

便可以享受`Pixiv-Patcher`所带来的便利了!

## 声明

### 一切开发旨在学习，请勿用于非法用途

- `Mikoto-Pixiv` 是完全免费且开放源代码的软件，仅供学习和娱乐用途使用
- `Mikoto-Pixiv` 不会通过任何方式强制收取费用，或对使用者提出物质条件
- `Mikoto-Pixiv` 由整个开源社区维护，并不是属于某个个体的作品，所有贡献者都享有其作品的著作权。

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

`Mikoto-Pixiv` 采用 `AGPLv3` 协议开源。为了整个社区的良性发展，我们**强烈建议**您做到以下几点：

- **间接接触（包括但不限于使用 `Http API` 或 跨进程技术）到 `Mikoto-Pixiv` 的软件使用 `AGPLv3` 开源**
- **不鼓励，不支持一切商业使用**

鉴于项目的特殊性，开发团队可能在任何时间**停止更新**或**删除项目**。
