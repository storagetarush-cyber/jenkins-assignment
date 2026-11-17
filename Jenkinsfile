pipeline {
    agent {
        label 'linux-agent'
    }

    parameters {
        string(name: 'APP_VERSION', defaultValue: '1.0.0', description: 'Release version tag')
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging', 'prod'], description: 'Target deployment environment')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Execute automated test suite')
    }

    environment {
        APP_NAME = 'assignment-3-demo-app'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out source code on ${NODE_NAME}..."
                checkout scm
                sh 'git log -1 --oneline || true'
            }
        }

        stage('Build') {
            steps {
                echo "Building ${APP_NAME} version ${params.APP_VERSION}..."
                sh '''
                    mkdir -p dist
                    echo "App: ${APP_NAME} | Version: ${APP_VERSION}" > dist/app.artifact
                    ls -lh dist/
                '''
            }
        }

        stage('Test') {
            when {
                expression { return params.RUN_TESTS == true }
            }
            steps {
                echo "Running unit and integration tests..."
                sh '''
                    test -f dist/app.artifact
                    grep -q "${APP_VERSION}" dist/app.artifact
                    echo "All verification checks passed!"
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying ${APP_NAME} v${params.APP_VERSION} to [${params.DEPLOY_ENV}] environment..."
                sh '''
                    mkdir -p /home/jenkins-agent/deployments/${DEPLOY_ENV}
                    cp dist/app.artifact /home/jenkins-agent/deployments/${DEPLOY_ENV}/
                    ls -l /home/jenkins-agent/deployments/${DEPLOY_ENV}/
                '''
            }
        }
    }

    post {
        always {
            echo "Archiving build artifacts and cleaning temporary files..."
            archiveArtifacts artifacts: 'dist/**', fingerprint: true
        }
        success {
            echo "PIPELINE SUCCEEDED: ${APP_NAME} v${params.APP_VERSION} deployed to ${params.DEPLOY_ENV}."
        }
        failure {
            echo "PIPELINE FAILED: Check Console Output for errors in build #${BUILD_NUMBER}."
        }
    }
}
