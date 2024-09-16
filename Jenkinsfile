pipeline {
    agent any

    environment {
        // Define any environment variables you need
        MAVEN_HOME = tool name: 'Maven', type: 'maven'
    }

    stages {
        // Checkout the source code from the version control system (e.g., Git)
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        // Build the Spring Boot application using Maven
        stage('Build') {
            steps {
                script {
                    // Use Maven to build the project
                    sh "${MAVEN_HOME}/bin/mvn clean package"
                }
            }
        }

        // Run the tests after building the application
        stage('Test') {
            steps {
                script {
                    // Run Maven tests
                    sh "${MAVEN_HOME}/bin/mvn test"
                }
            }
        }

        // Archive the built artifacts (e.g., JAR file)
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: false
            }
        }

        // Deploy the application using Docker
        stage('Deploy') {
            steps {
                script {
                    // Build Docker image
                    sh 'docker build -t weather-service:latest .'
                    
                    // Run the Docker container (example: running on port 8080)
                    sh 'docker run -d -p 8080:8080 weather-service:latest'
                }
            }
        }
    }

    post {
        // Clean up workspace after the pipeline finishes
        always {
            cleanWs()
        }

        // Notify if the build fails
        failure {
            echo 'Build failed!'
        }
    }
}
