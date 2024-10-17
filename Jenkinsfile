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
            }
        }
        stage('Build') {
            when {
                anyOf {
                    branch 'main'    // main 브랜치일 때 빌드
                    branch 'develop' // develop 브랜치일 때 빌드
                }
            }
            steps {
                // 실제 빌드 작업이 필요한 경우 해당 명령어로 대체 가능
                sh 'echo "Building the project"'
            }
        }
        stage('Deploy') {
            when {
                anyOf {
                    branch 'main'    // main 브랜치일 때 배포
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