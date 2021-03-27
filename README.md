#Swagger
http://localhost:5007/chat-server/doc.html

#mybatis自动生成增加配置，为了自动生成id
useGeneratedKeys="true" keyProperty="id"

#Redis in Docker
docker run -itd --name redis -p 6379:6379 --restart=always redis --requirepass wXc8UEqiimY6VHBG

流水线运行：
tar zxvf /home/admin/app/package.tgz -C /home/admin/app/
sh /home/admin/app/deploy.sh restart

开机自动运行：
vim /etc/rc.d/rc.local

chmod a+x /etc/rc.d/rc.local
