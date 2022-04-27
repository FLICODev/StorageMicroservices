String branchName = env.BRANCH_NAME

pipeline {
    agent any

    stages {

        stage('Clone') {
            steps {
                echo 'Clone branchName ' + branchName
                sh 'mkdir -p storage'

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