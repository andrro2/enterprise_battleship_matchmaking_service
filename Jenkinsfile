pipeline {
    agent any

    environment {
        TOKEN = credentials("KubernetesToken")
        BUILDVAR = "var_build_number"

    }

    stages {
        stage('Clone Repo')
        {
            steps{
                 // Get some code from a GitHub repository
                git url: 'https://github.com/andrro2/enterprise_battleship_matchmaking_service.git', branch:'dev'
            }
        }

        stage('Build') {
            steps {

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        }

        stage('Create Container') {
            steps {
                sh "docker build -t andrro/enterprise_battleship_matchmaking:latest ."
            }
        }
        }
            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh "docker login -u ${USERNAME} -p ${PASSWORD}"
                        sh "docker push andrro/enterprise_battleship_matchmaking:latest"
                        sh "sed -i -e 's#${BUILDVAR}#${BUILD_NUMBER}#' deployment.json"
                        sh "curl https://178.164.199.200:6443/apis/apps/v1/namespaces/default/deployments/battleship-matchmaking  \
                        -k -H 'Content-Type: application/json' -H 'Authorization: Bearer ${TOKEN}' --data @deployment.json --request PUT"
                    }
                }
            }

    }
