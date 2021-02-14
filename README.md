#本地swagger
http://localhost:5007/chat-server/doc.html

#mybatis自动生成增加配置，为了自动生成id
useGeneratedKeys="true" keyProperty="id"

#Run redis
docker run -itd --name redis -p 6379:6379 --restart=always redis --requirepass wXc8UEqiimY6VHBG

#阿里云轻量内网ip
http://172.24.55.58:3000/abc/chat-server