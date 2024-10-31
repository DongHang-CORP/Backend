pipeline {
    agent any
    environment {
        NCP_CONTAINER_REGISTRY = "contest23-server.kr.ncr.ntruss.com"
        KUBECONFIG = credentials('contest23_k8s')
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "Stage: Checkout started"
                    checkout scm
                    echo "Stage: Checkout completed"
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    echo "Stage: Build started"
                    sh './gradlew clean bootjar'
                    echo "Stage: Build completed"
                }
            }
        }
        stage('Build Docker Container Image') {
            steps {
                script {
                    echo "Stage: Build Docker Container Image started"
                    def imageName = "${NCP_CONTAINER_REGISTRY}/contest23-server:${env.BUILD_NUMBER}"
                    echo "Building Docker image: ${imageName}"
                    def customImage = docker.build(imageName)
                    echo "Docker build completed"
                    env.IMAGE_NAME = imageName
                    env.CUSTOM_IMAGE = customImage
                }
            }
        }
        stage('Login to NCP Container Registry') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'naver_cloud_api_access_credential', usernameVariable: 'NCP_ACCESS_KEY', passwordVariable: 'NCP_ACCESS_KEY_PSW')]) {
                    sh "echo ${NCP_ACCESS_KEY_PSW} | docker login ${NCP_CONTAINER_REGISTRY} -u ${NCP_ACCESS_KEY} --password-stdin"
                }
            }
        }
        stage('Push Container Image To NCP Container Registry') {
            steps {
                script {
                    echo "Stage: Docker Build and Push started"
                    sh "docker push ${env.IMAGE_NAME}"
                    echo "Pushed Docker image: ${env.IMAGE_NAME}"
                    sh "docker tag ${env.IMAGE_NAME} ${NCP_CONTAINER_REGISTRY}/contest23-server:latest"
                    sh "docker push ${NCP_CONTAINER_REGISTRY}/contest23-server:latest"
                    echo "Docker image pushed with latest tag"
                    echo "Stage: Docker Build and Push completed"
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    echo "Stage: Deploy started"

                    // 네임스페이스 목록 출력 및 네임스페이스 확인
                    echo "Checking available namespaces:"
                    sh 'kubectl get namespaces'

                    // 설정된 네임스페이스로 kubectl create 권한 확인
                    def namespace = 'default' // 여기서 원하는 네임스페이스로 변경할 수 있음
                    echo "Checking create permission in namespace: ${namespace}"
                    sh "kubectl auth can-i create deployment --namespace=${namespace} || echo 'Insufficient permissions to create deployment in ${namespace} namespace.'"

                    // 배포 진행
                    echo "Deploying the project to Kubernetes"
                    sh 'export KUBECONFIG=$KUBECONFIG && /var/jenkins_home/workspace/cicd/deploy.sh'

                    // 배포된 파드 IP 주소 확인
                    echo "Checking pod IPs after deployment"
                    sh 'kubectl get pods -o wide --namespace=${namespace}'

                    echo "Stage: Deploy completed"
                }
            }
        }
    }
}