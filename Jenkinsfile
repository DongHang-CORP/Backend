pipeline {
    agent any // 에이전트 선택 (특정 노드 라벨을 사용할 수도 있음)
    environment {
        NCP_CONTAINER_REGISTRY = "contest23-server.kr.ncr.ntruss.com" // 도커 이미지 저장 및 획득용 네이버 컨테이너 레지스트리 링크
        KUBECONFIG = credentials('contest23_k8s') // Jenkins에 저장된 Kubeconfig credentials ID 사용
    }
    stages {
        // 코드 불러오기
        stage('Checkout') {
            steps {
                script {
                    echo "Stage: Checkout started"
                    checkout scm
                    echo "Stage: Checkout completed"
                }
            }
        }
        // 레포지토리 빌드
        stage('Build') {
            steps {
                script {
                    echo "Stage: Build started"
                    sh './gradlew clean bootjar'
                    echo "Stage: Build completed"
                }
            }
        }
        // Docker 이미지 빌드 (Dockerfile을 기반으로 빌드)
        stage('Build Docker Container Image') {
            steps {
                script {
                    echo "Stage: Build Docker Container Image started"

                    // Docker 이미지 빌드 (Dockerfile을 기반으로 빌드)
                    def imageName = "${NCP_CONTAINER_REGISTRY}/contest23-server:${env.BUILD_NUMBER}"
                    echo "Building Docker image: ${imageName}"
                    def customImage = docker.build(imageName)
                    echo "Docker build completed"

                    // 이미지 변수 저장
                    env.IMAGE_NAME = imageName
                    env.CUSTOM_IMAGE = customImage
                }
            }
        }
        // 네이버 클라우드 로그인
        stage('Login to NCP Container Registry') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'naver_cloud_api_access_credential', usernameVariable: 'NCP_ACCESS_KEY', passwordVariable: 'NCP_ACCESS_KEY_PSW')]) {
                    sh "echo ${NCP_ACCESS_KEY_PSW} | docker login ${NCP_CONTAINER_REGISTRY} -u ${NCP_ACCESS_KEY} --password-stdin"
                }
            }
        }
        // 컨테이너 이미지 빌드
        stage('Push Container Image To NCP Container Registry') {
            steps {
                script {
                    echo "Stage: Docker Build and Push started"

                    // Docker 이미지 푸시
                    sh "docker push ${env.IMAGE_NAME}"
                    echo "Pushed Docker image: ${env.IMAGE_NAME}"

                    // 'latest' 태그로도 푸시
                    sh "docker tag ${env.IMAGE_NAME} ${NCP_CONTAINER_REGISTRY}/contest23-server:latest"
                    sh "docker push ${NCP_CONTAINER_REGISTRY}/contest23-server:latest"
                    echo "Docker image pushed with latest tag"

                    echo "Stage: Docker Build and Push completed"
                }
            }
        }
        // Kubernetes 배포
        stage('Deploy') {
            steps {
                script {
                    echo "Stage: Deploy started"
                    echo "Deploying the project to Kubernetes"
                    sh 'pwd'
                    sh 'ls -al'
                    // Execute deploy.sh if it exists
                    echo "Deployment to Kubernetes completed"
                    sh 'export KUBECONFIG=$KUBECONFIG && /var/jenkins_home/workspace/cicd/deploy.sh'
                    echo "Stage: Deploy completed"
                }
            }
        }
    }
}