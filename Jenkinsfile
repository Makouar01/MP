pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "springboot_app"
        DOCKER_CONTAINER = "springboot_app_container"
        MYSQL_CONTAINER = "mysql_db"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'master', url: 'https://github.com/Makouar01/MP.git'
            }
        }

        stage('Build Maven Project') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE} .'
            }
        }

        stage('Run Containers') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        stage('Run Tests') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker-compose down'
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        always {
            sh 'docker ps -a'
        }
    }
}
