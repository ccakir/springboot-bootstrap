pipeline {
	agent any
	
	
	stages {
	
	stage('build') {
	steps {
            withMaven(maven : 'Maven_3_6_3') {
                bat'mvn clean compile'
            }
	        
	       steps { sh 'mvn clean package'}
 
	    }
	    }
	    
	stage('Test') {
	        steps {
	            sh 'mvn test'
	           
	        }
	        


			
	
	    }

    
	}
    
}


