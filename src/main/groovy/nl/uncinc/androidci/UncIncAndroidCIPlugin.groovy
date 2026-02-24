package nl.uncinc.androidci

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.process.ExecOperations

import javax.inject.Inject

class UncIncAndroidCIPlugin implements Plugin<Project> {
    def androidci = new Properties()
    Project project

    private final ExecOperations execOperations

    @Inject
    UncIncAndroidCIPlugin(ExecOperations execOperations) {
        this.execOperations = execOperations
    }

    void apply(Project project) {
        this.project = project

        androidci.signingProperies = project.hasProperty('androidci.signingProperties')
                ? project.getProperty('androidci.signingProperties') : 'android-app-signing.properties'
        androidci.versionCode = project.hasProperty('androidci.versionCode')
                ? project.getProperty('androidci.versionCode') as Integer : 1
        androidci.versionName = project.hasProperty('androidci.versionName')
                ? project.getProperty('androidci.versionName') : getGitHash()

        def keystoreProperties = new Properties()

        if (project.file(androidci.signingProperies).exists()) {
            def keystorePropertiesFile = project.file(androidci.signingProperies)
            keystoreProperties.load(new FileInputStream(keystorePropertiesFile))

            if (!keystoreProperties.hasProperty("storeFileType")) {
                keystoreProperties.setProperty("storeFileType", 'jks')
            }
        } else {
            project.logger.error("No release build via CI currently or signing properties file not found." +
                    " Defaulting to debug keystore for CI configuration.")
            keystoreProperties.setProperty("keyAlias", 'androiddebugkey')
            keystoreProperties.setProperty("keyPassword", 'android')
            keystoreProperties.setProperty("storePassword", 'android')
            keystoreProperties.setProperty("storeFile", 'debug.keystore')
            keystoreProperties.setProperty("storeFileType", 'jks')
        }
        androidci.keystoreProperties = keystoreProperties

        // Expose this class to the project
        project.ext.androidci = this

        project.task('printAndroidCIVars') {
            doLast {
                println 'Signing: ' + androidci.signingProperies
                println 'versionCode: ' + androidci.versionCode
                println 'versionName: ' + androidci.versionName
                println 'androidApplicationId: ' + androidci.applicationId
            }
        }
    }

    Integer getVersionCode() {
        return androidci.versionCode as Integer
    }

    String getVersionName() {
        return androidci.versionName
    }

    String getApplicationId(String appId) {
        androidci.applicationId = project.hasProperty('androidci.applicationId')
                ? project.getProperty('androidci.applicationId') : appId
        return androidci.applicationId
    }

    File getStoreFile() {
        return project.file(androidci.keystoreProperties['storeFile'])
    }

    String getStorePassword() {
        return androidci.keystoreProperties['storePassword']
    }

    String getKeyAlias() {
        return androidci.keystoreProperties['keyAlias']
    }

    String getKeyPassword() {
        return androidci.keystoreProperties['keyPassword']
    }

    String getGitHash() {
        def stdout = new ByteArrayOutputStream()
        execOperations.exec {
            commandLine 'git', 'rev-parse', '--short', 'HEAD'
            standardOutput = stdout
        }
        return stdout.toString().trim()
    }
}
