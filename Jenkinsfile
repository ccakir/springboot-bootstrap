node {
    stage 'checkout'
    echo 'Checking out source code'
    git url: 'https://github.com/ccakir/springboot-bootstrap'
    stage 'compile'
    echo "compile"
    // sh "${mvnHome}/bin/mvn compile"
    stage 'test'
    echo "Test"
    // sh "${mvnHome}/bin/mvn test"
    stage 'User Acceptance'
    timeout(time: 10, unit: 'SECONDS') {
        input 'Alles Okk'
    }
    stage 'Deploy'
    echo 'Deploy'
}