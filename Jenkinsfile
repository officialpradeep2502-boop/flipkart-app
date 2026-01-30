pipeline {
    agent any

    environment {
        JAVA_HOME = "/Users/pradeepverma/Library/Java/JavaVirtualMachines/ms-17.0.17/Contents/Home"
        PATH = "${JAVA_HOME}/bin:${PATH}"
        IMAGE_NAME = "flipkart-backend:latest"
    }

    stages {

        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build (Maven)') {
            steps {
                sh '''
                   echo "Using JAVA_HOME=$JAVA_HOME"
                   java -version
                   ls -la
                   chmod +x mvnw
                   ./mvnw clean package -DskipTests
                '''
            }
        }

        // 🔥 YAHI PAR DOCKER STAGE ADD HOTA HAI
        stage('Build Docker Image') {
            steps {
                sh '''
                   docker --version
                   docker build -t $IMAGE_NAME .
                '''
            }
        }
    }

    post {
        success {
            echo 'CI + Docker build successful 🎉'
        }
        failure {
            echo 'Build failed ❌'
        }
    }
}
