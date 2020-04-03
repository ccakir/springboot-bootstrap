pipeline {
    agent any
    tools { 
        maven 'Maven_3_6_3' 
        jdk 'Java8' 
    }
    stages {
        

        stage ('Build') {
            steps {
                bat 'mvn clean package'
            }
        }
        stage ('Test') {
            steps {
                bat 'mvn test'
            }
        }
        
    }
}
