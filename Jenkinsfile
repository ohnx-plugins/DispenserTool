pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''mvn install
'''
      }
    }
    stage('Archive Artifacts') {
      steps {
        archiveArtifacts 'target/dispenserTool-0.0.1.jar'
      }
    }
  }
}