# 电影后台管理系统

技术栈：Struts2、Spring5、Hibernate5、JSP、MySQL。

## 当前状态

- 项目目录：`F:\PROJECT\AI\project\filmsystem`
- 本地测试地址：`http://localhost:18080/filmsystem/login.action`
- 线上测试地址：`https://film.zzuli.site/filmsystem/login.action`
- 线上部署方式：Docker 部署 `tomcat:8.5-jdk8` + `mysql:8.0`
- 线上旧系统：原 `/BBS/` 已下线，当前部署的是 `filmsystem.war`
- 默认后台账号：`admin / admin`

线上 Docker 容器：

```text
film_tomcat  8081 -> 8080
film_mysql   3307 -> 3306
```

## 环境

- JDK：`F:\Program Files\Java\jdk1.8.0_131`
- MySQL：用户名 `root`，密码 `root`
- 数据库：`filmsystem`
- Tomcat：使用现有 Tomcat，建议 Tomcat 8.5/9，不建议 Tomcat 10

## 构建

在项目根目录执行：

```powershell
$env:JAVA_HOME='F:\Program Files\Java\jdk1.8.0_131'
$env:Path="$env:JAVA_HOME\bin;$env:Path"
mvn clean package
```

生成文件：

```text
target/filmsystem.war
```

## 部署

把 `target/filmsystem.war` 放到现有 Tomcat 的 `webapps` 目录，启动 Tomcat 后访问：

```text
http://localhost:8080/filmsystem/login.action
```

如果页面或提交内容出现中文乱码，确认 Tomcat 的 HTTP Connector 带有 UTF-8 配置：

```xml
<Connector port="8080" protocol="HTTP/1.1"
           URIEncoding="UTF-8"
           useBodyEncodingForURI="true" />
```

默认后台账号：

```text
用户名：admin
密码：admin
```

### 线上部署说明

服务器目录：

```text
/home/ubuntu/film-system
```

线上 Tomcat 使用的 WAR：

```text
/home/ubuntu/film-system/filmsystem.war
```

线上 MySQL 数据库：

```text
数据库：filmsystem
用户名：root
密码：root
容器内地址：film_mysql:3306
```

线上 Nginx 会把根路径跳转到：

```text
/filmsystem/login.action
```

## 数据库

项目启动时会通过 Hibernate 自动建表，并初始化默认管理员和影片分类。也可以手动执行：

```text
database/filmsystem.sql
```

数据库连接配置在：

```text
src/main/resources/db.properties
```

## 功能

- 用户管理：后台用户新增、编辑、删除、启用/停用、登录、退出
- 用户安全：登录验证码、当前用户修改密码，密码使用 MD5 保存
- 新闻管理：新闻新增、编辑、删除、查看，前台新闻浏览，支持搜索和分页
- 影片分类管理：分类新增、编辑、删除，支持搜索和分页
- 影片上传：上传封面和影片文件
- 影片管理：影片信息查看、编辑、删除、分类筛选、搜索分页、影片预览
- 公共浏览：前台新闻列表、新闻详情浏览
- 服务器部署：可通过 `-Dfilmsystem.upload.dir=/path/to/uploads` 把上传文件保存到外部持久目录

## 最新改动

- 从服务器下线旧 `BBS.war`，替换为当前 `filmsystem.war`
- 修复中文乱码：JSP、请求响应、数据库连接、Tomcat URI 均按 UTF-8 处理
- 增加登录验证码
- 增加当前用户修改密码
- 增加用户、新闻、影片分类、影片列表搜索分页
- 增加影片分类筛选
- 增加影片详情页视频预览
- 增加上传文件缺失兜底提示，避免重新部署后页面破图
- 增加 `/uploads/*` 文件访问 Servlet，支持视频 `Range` 请求
- 上传文件改为外部持久化目录，避免 Docker 容器重建后丢失
- 上传大小限制从 500MB 调整为约 2GB
- README 同步记录当前部署和使用情况

## 上传说明

默认上传文件保存在应用部署目录下：

```text
/uploads/posters
/uploads/films
```

服务器 Docker 部署建议挂载外部目录，并在 Tomcat 启动参数中设置：

```text
-Dfilmsystem.upload.dir=/usr/local/tomcat/uploads
```

当前线上已挂载到宿主机目录：

```text
/home/ubuntu/film-system/uploads
```

当前支持的文件扩展名：

```text
封面：jpg, jpeg, png, gif, webp
影片：mp4, avi, mkv, mov, wmv, flv
```

当前上传上限约为 2GB。更大的电影文件建议后续改成分片上传或对象存储。

## 注意事项

- 本项目不是 SpringBoot、SpringMVC、MyBatis 项目，当前实现按要求使用 Struts2、Spring5、Hibernate5。
- 本地 `db.properties` 默认连接 `localhost:3306/filmsystem`。
- 服务器部署时 WAR 包内数据库地址已调整为 `film_mysql:3306/filmsystem`。
- Hibernate 配置为 `hbm2ddl.auto=update`，启动时会自动补齐表结构。
- 默认管理员和默认影片分类会在空库启动时自动初始化。
- 重新部署 WAR 不会清空线上上传文件，因为线上已经使用外部持久目录。
