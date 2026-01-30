pipeline {
    agent any

    tools {
        jdk 'jdk17'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Code checkout successful'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'Build successful 🎉'
        }
        failure {
            echo 'Build failed ❌'
        }
    }
}
