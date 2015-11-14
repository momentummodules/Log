# Log

A mix of https://github.com/JakeWharton/hugo and and https://github.com/noveogroup/android-logger

See example here https://github.com/nomad5modules/Log

## Usage

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
}
```

Furthermore place a file called 'log.properties' into the /src/main/resources folder that specifies the format of the logging depending on the caller class. One example is like this

```
root=VERBOSE:Momentum:[%thread] --- %-20class{-3}
logger.momentum.example.SomeActivity=VERBOSE:SomeActivity:[%thread] --- %-20class{-3} ||| %logger{-3} ||| %source ||| %caller
logger.hugo.weaving.internal.Hugo=VERBOSE:Hugggggo:[%thread] --- %-20class{-3}
```

## Issues

* for now its a problem if your package starts with 'com.nomad5.log'

## Todo
* Better documentation
* References to Hugo