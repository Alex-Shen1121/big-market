# 普通镜像构建，随系统版本构建 amd/arm
#docker build -t shenchenyu/big-market-app:1.0 -f ./Dockerfile .

# 兼容 amd、arm 构建镜像
docker buildx build --platform linux/amd64,linux/arm64 -f ./Dockerfile -t shenchenyu/big-market-app:1.0 . --push
