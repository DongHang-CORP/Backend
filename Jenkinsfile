pipeline {
    agent any
    environment {
        KUBECONFIG = credentials('contest23_k8s')
        GITHUB_CREDS = credentials('ee5915aa-feef-4e25-bf4b-2936b1471b69')
    }
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/develop'], [name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/your-repo-url.git',
                        credentialsId: 'ee5915aa-feef-4e25-bf4b-2936b1471b69'
                    ]]
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
                    sh 'chmod +x ./deploy.sh'
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