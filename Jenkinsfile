pipeline {
    agent any // Specifies that the pipeline can run on any available agent

    
    environment {
        GITHUB_CREDS = credentials('github-token')
        // GH_TOKEN = credentials('github-token')
        REPO = 'muniif10/database_crm'   // GitHub repo
        RELEASE_VERSION = 'v1.0.12'       // Release/tag version
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
 stage('Tag & Push') {
            steps {
                sh '''
                git config user.name "Jenkins"
                git config user.email "jenkins@magico.com"

                git tag -a ${RELEASE_VERSION} -m "Release ${RELEASE_VERSION}"

                git push https://${GITHUB_CREDS}@github.com/${REPO}.git --tags
                '''
            }
        }

        stage('Create GitHub Release') {
            steps {
                sh '''
                # Authenticate GitHub CLI
                echo $GH_TOKEN | gh auth login --with-token

                # Create release and upload JAR
                gh release create ${RELEASE_VERSION} target/*.jar \
                    --repo ${REPO} \
                    --title "Release ${RELEASE_VERSION}" \
                    --notes "Automated release from Jenkins"
                '''
            }
        }
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
