#!/bin/bash

# 构建脚本
set -e

echo "开始构建 Docker 镜像..."

docker build --no-cache -t interview-app:latest .

echo "构建完成！"

# 可选：推送到镜像仓库
# docker tag interview-app:latest your-registry/interview-app:latest
# docker push your-registry/interview-app:latest

echo "镜像构建完成，可以使用以下命令运行："
echo "docker run -d -p 8081:8081 --name interview-app \
  -e MYSQL_JDBC_URL=jdbc:mysql://your-mysql-host:3306/yourdb?useSSL=false \
  -e MYSQL_USERNAME=your_db_user \
  -e MYSQL_PASSWORD=your_db_password \
  -e REDIS_HOST=your-redis-host \
  -e REDIS_PASSWORD=your_redis_password \
  interview-app:latest"
echo "或者使用 docker-compose:"
echo "docker-compose up -d" 