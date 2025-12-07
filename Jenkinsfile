pipeline {
    agent any // Specifies that the pipeline can run on any available agent

    
    environment {
        GITHUB_CREDS = credentials('github-token')
    }

    tools {
        maven 'maven'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/muniif10/database_crm.git' // Replace with your repository URL
            }
        }

        stage('Build') {
            steps {
                sh "mvn clean install -DskipTests" // Build the application, skipping tests
            }
        }

        // stage('Test') {
        //     steps {
        //         sh "mvn test" // Run unit and integration tests
        //     }
        // }

        stage('Package') {
            steps {
                sh "mvn package -DskipTests" // Create the executable JAR
            }
        }
         stage('Tag & Push Release') {
            steps {
                sh '''
                git config user.name "Jenkins"
                git config user.email "jenkins@example.com"

                # Tag the release
                git tag -a v1.0.2 -m "Release v1.0.2"

                # Push tag to GitHub using credentials
                git push https://${GITHUB_CREDS_USR}:${GITHUB_CREDS_PSW}@github.com/muniif10/database_crm.git --tags
                '''
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
