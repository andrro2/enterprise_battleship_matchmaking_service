pipeline {
    agent any

    environment {
        TOKEN = 'eyJhbGciOiJSUzI1NiIsImtpZCI6InBUTE5hVjQ0ZV9NVTJRUHpvZ1E5Zjh6VF9RNEFpekpyeWNMUnUwckV3V1kifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImRhc2hib2FyZC1hZG1pbi1zYS10b2tlbi1ma3psbiIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJkYXNoYm9hcmQtYWRtaW4tc2EiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiJiODU2NGUyMy01YzFkLTRjZTQtYTQwZS01MWQzNGJkZDY0NTUiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6ZGVmYXVsdDpkYXNoYm9hcmQtYWRtaW4tc2EifQ.A4KCUwH5cnKADqBxhEWgpTdgMz6IfFeHFv81szm_ZzFbe9pGqdjCbPPxzga4768aocGYKWTMQf9UG1m6vgKZgjVFlt67tZGq4tlz2RS0v9Eib0BxM51rTlxWH4V3gmhXSm0NC1mrdbhAlOuFaRH-Whf3dK948QcQQEujWwNCrNbKHjQN_09Yc2tibG1OHEzJlX3jGAlKbDb6q5hDqRGQ-HLx6Ai6nHjxTpro2rwj9OX7xfSsnV7Bll996bdqKWqj-V8Ys1Jyp1p3XL4gy1rwP0ChrgbztkqxIy0R7V1C3rjIpOGcr0Kkq3_mEHUW8EQtWPhE4wFJhqm0RnohVtugFA'
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
