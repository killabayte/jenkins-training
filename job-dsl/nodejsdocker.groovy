job('NodeJSDocker') {
    scm {
        git('git://github.com/killabayte/docker-project.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSLUser')
            node / gitConfigEmail('jenkins-dsluser@killabayte.com')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('killabayte/docker-nodejs-project')
            tag('${GIT_REVISION,length=9}')
            registryCredentials('dockerhub')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}
