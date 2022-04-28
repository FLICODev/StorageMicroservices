String branchName = env.BRANCH_NAME

pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                echo 'Building..'
                sh 'mvn clean install'
            }
        }
        stage('Move War') {
            steps{
                sh 'cp -b target/storage.war /opt/tomcat/webapps'
            }
        }
    }
}