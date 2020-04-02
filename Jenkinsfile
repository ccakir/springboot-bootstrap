pipeline {
	agent any
	
	stages {
	
	stage('build') {
	        
	       steps { sh 'mvn clean package'}
 
	    }
	    
	stage('test') {
	        steps {
	            sh 'mvn test'
	            sh 'mvn integration-test'
	        }
	        


			
	
	    }

    
	}
    
}


