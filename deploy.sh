bash#!/bin/bash

# NKS 클러스터 접속 설정
export KUBECONFIG=/path/to/kubeconfig # nks 연결 후 설정 예정

# 애플리케이션 배포
kubectl apply -f kubernetes/deployment.yaml
kubectl apply -f kubernetes/service.yaml