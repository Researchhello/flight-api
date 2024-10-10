pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "jenkins-maven"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Researchhello/flight-api']])

                // Run Maven on a Unix agent.
                bat "mvn clean install"

            }

        }
         stage('Build docker image') {
                steps {
                    script {
                        bat "docker build -t infinity467/flight-api ."
                    }
                }
            }
        stage('Push image to hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhubpwd')]) {
                        bat "docker login -u infinity467 -p ${dockerhubpwd}" //variable is pswd
                        bat "docker push infinity467/flight-api"
                    }
                }
            }
        }
    }
}
