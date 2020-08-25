package nl.uncinc.androidci

import com.android.build.gradle.internal.dsl.SigningConfig
import org.gradle.api.Plugin
import org.gradle.api.Project


class UncIncAndroidCIPlugin implements Plugin<Project> {
    def androidci = new Properties()

    void apply(Project project) {

        androidci.signingProperies = project.hasProperty('androidci.signingProperties') ? project.getProperty('androidci.signingProperties') : 'android-app-signing.properties'
        androidci.versionCode = project.hasProperty('androidci.versionCode') ? project.getProperty('androidci.versionCode') as Integer : 1
        androidci.versionName = project.hasProperty('androidci.versionName') ? project.getProperty('androidci.versionName') : getGitHash(project)
        def keystoreProperties = new Properties()

        if (project.file(androidci.signingProperies).exists()) {
            def keystorePropertiesFile = project.file(androidci.signingProperies)
            keystoreProperties.load(new FileInputStream(keystorePropertiesFile))

            if (!keystoreProperties.hasProperty("storeFileType")) {
                keystoreProperties.setProperty("storeFileType", 'jks')
            }
        } else {
            project.logger.error("No release build via CI currently or signing properties file not found. Defaulting to debug keystore for CI configuration.")
            keystoreProperties.setProperty("keyAlias", 'androiddebugkey')
            keystoreProperties.setProperty("keyPassword", 'android')
            keystoreProperties.setProperty("storePassword", 'android')
            keystoreProperties.setProperty("storeFile", 'debug.keystore')
            keystoreProperties.setProperty("storeFileType", 'jks')

        }
        androidci.keystoreProperties = keystoreProperties
        project.ext.androidciconfig = this

        project.task('printAndroidCIVars') {
            doLast {
                println 'Signing: ' + androidci.signingProperies
                println 'versionCode: ' + androidci.versionCode
                println 'versionName: ' + androidci.versionName
            }
        }
    }

    Integer getVersionCode() {
        return androidci.versionCode
    }

    String getVersionName() {
        return androidci.versionName
    }

    SigningConfig getSigningConfig(Project project) {
        def signingConfig = new SigningConfig("CIStore")
        signingConfig.storeFile = project.file(androidci.keystoreProperties['storeFile'])
        signingConfig.storePassword = androidci.keystoreProperties['storePassword']
        signingConfig.keyAlias = androidci.keystoreProperties['keyAlias']
        signingConfig.keyPassword = androidci.keystoreProperties['keyPassword']
        return signingConfig
    }

    String getGitHash(Project project) {
            def stdout = new ByteArrayOutputStream()
            project.exec {
                commandLine 'git', 'rev-parse', '--short', 'HEAD'
                standardOutput = stdout
            }
            return stdout.toString().trim()
    }
}