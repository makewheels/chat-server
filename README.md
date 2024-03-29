## Swagger

<http://localhost:5007/chat-server/doc.html>

## mybatis自动生成增加配置，为了自动生成id

```xml
useGeneratedKeys="true" keyProperty="id"
```

## Redis in Docker

```shell
docker run -itd --name redis -p 6379:6379 --restart=always redis --requirepass wXc8UEqiimY6VHBG
```

## 阿里流水线运行：

```shell
tar zxvf /home/admin/app/package.tgz -C /home/admin/app/
chmod a+x /home/admin/app/deploy.sh
sh /home/admin/app/deploy.sh restart
```

## 开机自动运行：

```shell
chmod a+x /etc/rc.d/rc.local vim /etc/rc.d/rc.local

nohup java -jar -Dspring.profiles.active=aliyun /home/admin/app/target/chat-server-0.0.1-SNAPSHOT.jar >
/home/admin/app/logs/start.log 2>&1 &
```

## Docker 构建推送

```shell
mvn docker:build -DpushImage
```

## Docker启动MySQL

```shell
docker run -itd --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=9Zu3nzrl5mgYM46V -e MYSQL_DATABASE=chat -e MYSQL_USER=chat -e MYSQL_PASSWORD=l8pqjJzm4GMcLyj1 --restart=always mysql/mysql-server:5.7.33
```
