pipeline {
	agent any
	
	stages {
	
	stage('build') {
	        
	       steps { sh 'mvn clean package'}
 
	    }
	    
	    stage('test') {
	        
	        parallel (
	        'unit tests' : {
	                         sh 'mvn test'
	                     },
	        'integration tests' : {
	                                sh 'mvn integration-test'                                
	                            }
			)
	
	    }

    
	}
    
}


