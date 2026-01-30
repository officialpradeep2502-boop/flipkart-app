pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checkout successful'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
    }
}
