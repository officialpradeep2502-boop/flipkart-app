pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Code checkout successful'
            }
        }

        stage('Build') {
            steps {
                sh '''
                    export JAVA_HOME=$(/usr/libexec/java_home -v 17)
                    export PATH=$JAVA_HOME/bin:$PATH

                    echo "JAVA_HOME=$JAVA_HOME"
                    which java
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
