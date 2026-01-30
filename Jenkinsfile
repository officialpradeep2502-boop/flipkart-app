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
                sh 'mvn clean package -DskipTests'
            }
        }
    }
}
