pipeline {
    agent any

    environment {
        JAVA_HOME = "/opt/homebrew/Cellar/openjdk@17/17.0.10/libexec/openjdk.jdk/Contents/Home"
        PATH = "${JAVA_HOME}/bin:${PATH}"
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Code checkout successful'
            }
        }

        stage('Build') {
            steps {
                sh '''
                   echo "JAVA_HOME=$JAVA_HOME"
                   java -version
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
