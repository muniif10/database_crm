pipeline {
    agent any // Specifies that the pipeline can run on any available agent

    environment {
        // Define environment variables, e.g., Maven home or Java home
        MAVEN_HOME = tool 'M3' // Assuming 'M3' is the name of your Maven installation in Jenkins
        JAVA_HOME = tool 'jdk-17' // Assuming 'jdk-17' is the name of your JDK installation in Jenkins
    }

    tools {
        // Declare tools to be used in the pipeline
        maven 'M3'
        jdk 'jdk-21'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/muniif10/database_crm.git' // Replace with your repository URL
            }
        }

        stage('Build') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean install -DskipTests" // Build the application, skipping tests
            }
        }

        stage('Test') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn test" // Run unit and integration tests
            }
        }

        stage('Package') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn package" // Create the executable JAR
            }
        }

    //     stage('Deploy') {
    //         // This stage can be customized based on your deployment strategy
    //         // Examples: Deploying to a remote server, Docker image creation, artifact deployment to Nexus
    //         // steps {
    //         //     echo "Deploying the Spring Boot application..."
    //         //     // Example: Copying the JAR to a remote server (requires SSH agent or similar)
    //         //     // sh "scp target/your-app.jar user@remote-host:/path/to/deploy"
    //         // }
    //     }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
