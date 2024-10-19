pipeline {
    agent any // 에이전트 선택 (특정 노드 라벨을 사용할 수도 있음)
    environment {
        // Jenkins Credential에 추가된 kubeconfig를 사용
        KUBECONFIG = credentials('contest23_k8s') // Jenkins에 저장된 Kubeconfig credentials ID 사용
    }
    stages {
        stage('Checkout') {
            steps {
                // 깃허브 저장소에서 코드 가져오기
                checkout scm
                sh 'echo "Current branch: $(git rev-parse --abbrev-ref HEAD)"'
            }
        }
        stage('Build') {
            when {
                anyOf {
//                     branch 'main'    // main 브랜치일 때 빌드
                    branch 'develop' // develop 브랜치일 때 빌드
                }
            }
            steps {
                sh 'echo "Building the project"'
                sh './gradlew clean build' // Gradle Wrapper를 사용해 프로젝트 빌드
            }
        }
        stage('Deploy') {
            when {
                anyOf {
//                     branch 'main'    // main 브랜치일 때 배포
                    branch 'develop' // develop 브랜치일 때 배포
                }
            }
            steps {
                sh 'echo "Deploying the project to Kubernetes"'

                // kubeconfig 설정 후 배포
                sh 'export KUBECONFIG=$KUBECONFIG && ./deploy.sh'
            }
        }
    }
}