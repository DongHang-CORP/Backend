# NKS 클러스터 접속 설정
#!/bin/bash

# NKS 클러스터 접속 설정
export KUBECONFIG=./kubeconfig.yaml  # kubeconfig 파일 경로

# 애플리케이션 배포
kubectl apply -f kubernetes/deployment.yaml
kubectl apply -f kubernetes/service.yaml