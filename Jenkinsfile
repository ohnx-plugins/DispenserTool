pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }
  stages {
    stage('Build') {
      steps {
        sh '''mvn install'''
      }
    }
    stage('Archive Artifacts') {
      steps {
        archiveArtifacts 'target/dispenserTool-0.0.1.jar'
      }
    }
  }
}
