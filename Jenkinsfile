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
                sh 'rm -f /opt/tomcat/webapps/storage.war'
                sh 'cp -b target/storage.war /opt/tomcat/webapps'
            }
        }
        stage('Deploy'){
            steps{
              deploy adapters: [tomcat8(credentialsId: '79db8cc1-3d9f-4e3f-82dc-105c5f5c78d4', path: '', url: 'http://flico.it:8080')], contextPath: '/storage', onFailure: false, war: 'webapps/storage.war'

            }
        }
    }
}