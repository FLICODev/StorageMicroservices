String branchName = env.BRANCH_NAME

pipeline {
    agent any

    stages {

        stage('Clone') {
            steps {
                echo 'Clone branchName ' + branchName
                sh 'mkdir -p /home/ubuntu/services/storage'

            }
        }
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}