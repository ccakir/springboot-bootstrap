pipeline {
	agent any
	tools {
	    maven : 'Maven_3_6_3'
	}

	
	stages {
	
	stage('build') {
           
	        
	       steps { sh 'mvn clean package'}
 
	   
	    }
	    
	stage('Test') {
	        steps {
	            sh 'mvn test'
	           
	        }
	        


			
	
	    }

    
	}
    
}
