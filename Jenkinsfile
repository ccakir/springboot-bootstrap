pipeline {
    agent any
    
    stages {
        

        stage ('Build') {
        
        	steps {
            withMaven(maven : 'Maven_3_6_3') {
                bat'mvn clean compile'
            }
        }
            steps {
                bat 'package'
            }
        }
        stage ('Test') {
            steps {
                bat 'mvn test'
            }
        }
        
    }
}
