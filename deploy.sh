#!/bin/bash

# Kubernetes 리소스 배포
echo "Applying Kubernetes Deployment and Service files..."
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

# 배포된 파드들의 상태와 IP 주소 확인
echo "Checking deployed pod IP addresses..."
kubectl get pods -o wide
