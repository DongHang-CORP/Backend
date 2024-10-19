pipeline {
    agent any
    environment {
        KUBECONFIG = credentials('contest23_k8s')
    }
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/develop'], [name: '*/main']],
                    userRemoteConfigs: [[url: 'https://github.com/DongHang-CORP/Backend']]
                ])
                script {
                    env.GIT_BRANCH = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                }
                sh 'echo "Current branch: ${GIT_BRANCH}"'
            }
        }
        stage('Build') {
            when {
                expression {
                    return env.GIT_BRANCH in ['develop', 'main']
                }
            }
            steps {
                sh 'echo "Building the project"'
                sh './gradlew clean build'
            }
        }
        stage('Deploy') {
            when {
                expression {
                    return env.GIT_BRANCH in ['develop', 'main']
                }
            }
            steps {
                sh 'echo "Deploying the project to Kubernetes"'
                withEnv(["KUBECONFIG=${KUBECONFIG}"]) {
                    sh './deploy.sh'
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}