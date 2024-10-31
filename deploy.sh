#!/bin/bash

# NKS 클러스터 접속 설정 (credentials은 이미 설정됨)

# ConfigMap을 생성하여 Nginx의 리버스 프록시 설정을 Kubernetes에 적용
echo "Applying Nginx ConfigMap..."
kubectl apply -f kubernetes/nginx-config.yaml

# Nginx Deployment 및 Service 생성
echo "Deploying Nginx..."
kubectl apply -f kubernetes/nginx-deployment.yaml
kubectl apply -f kubernetes/nginx-service.yaml

# 현재 활성화된 배포 (블루 또는 그린)를 확인
ACTIVE_DEPLOY=$(kubectl get deployment -l app=contest23-food-server -o=jsonpath='{.items[0].metadata.name}' | grep blue || echo "green")

# 활성화되지 않은 배포를 대상 배포로 설정
if [ "$ACTIVE_DEPLOY" == "contest23-food-server-deployment-blue" ]; then
    TARGET_DEPLOY="kubernetes/deployment-green.yaml"
    TARGET_SERVICE="kubernetes/service-green.yaml"
    echo "Switching to Green deployment..."
else
    TARGET_DEPLOY="kubernetes/deployment-blue.yaml"
    TARGET_SERVICE="kubernetes/service-blue.yaml"
    echo "Switching to Blue deployment..."
fi

# 대상 배포 파일과 서비스 파일을 사용하여 배포 업데이트
kubectl apply -f $TARGET_DEPLOY
kubectl apply -f $TARGET_SERVICE

echo "Deployment completed successfully."