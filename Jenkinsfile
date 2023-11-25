#!groovy
pipeline{
    agent any
    environment {
        version = 'latest'
        image='sample'
        host='34.87.48.221'
    }
    stages {
        stage('Build Jar file') {
            tools {
                maven 'maven 3.9'
            }
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t nguyenvh2k/${image} .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                withCredentials([usernamePassword(credentialsId: 'Docker_hub', usernameVariable: 'DOCKER_USERNAME',passwordVariable:'DOCKER_PASSWORD')]) {
                    script {
                        sh 'echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin'
                        // sh 'docker build -t nguyenvh2k/${image}:${version} .'
                        sh 'docker push nguyenvh2k/${image}:${version}'
                    }
                }
            }
        }
        stage("Copy env and deloy for Cloud"){
            steps{
                sshagent(credentials: ['vps_227']){
                    sh "scp -o StrictHostKeyChecking=no docker-compose.yaml deploy.sh root@${host}:~/web"
                    sh "ssh -o StrictHostKeyChecking=no root@${host}  '(cd web;chmod u+x deploy.sh; ./deploy.sh)'"
                }
            }
        }
    }
}