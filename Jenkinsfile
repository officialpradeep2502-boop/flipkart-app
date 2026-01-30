pipeline {
    agent any

    environment {
        JAVA_HOME = "/Users/pradeepverma/Library/Java/JavaVirtualMachines/ms-17.0.17/Contents/Home"
        PATH = "${JAVA_HOME}/bin:${PATH}"
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

        stage('Build') {
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
