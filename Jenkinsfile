String branchName = env.BRANCH_NAME

pipeline {
    agent any

    stages {

        stage('Clone') {
            echo 'Clone branchName ' + branchName
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