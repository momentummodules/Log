A mix of Hugo and formatting logging.

See example here https://github.com/nomad5modules/Log

Use it like this in the build.gradle file:

```
buildscript {
    repositories {
        jcenter()
        // NOTE: This is only needed when developing the plugin!
        mavenLocal()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:1.3.0'
        classpath 'com.nomad5.log:log-plugin:1.0.1'
    }
}


apply plugin: 'com.android.application'
apply plugin: 'log'

dependencies {
    repositories {
        mavenLocal()
    }
    compile 'com.nomad5.log:log-annotations:1.0.1'
    compile 'com.nomad5.log:log-runtime:1.0.1'
}

android {
	...
}

dependencies {
    ...
    compile 'com.nomad5.log:log-annotations:1.0.1'
    compile 'com.nomad5.log:log-runtime:1.0.1'
}```
