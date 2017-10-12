# Q-Cog Agent Module
This is the repository of an etention for the Q-Cog Agent Module created by Yusri Dollie.

## Building
The building of this project has been greatly simplified using the gradle build system.

### Prerequisites

* [Gradle Installation](https://gradle.org/install/) - instructions to install gradle
* [SDKMAN](http://sdkman.io/) - this is a useful tool that will install java + gradle for you
* [Q-Cog] - Experimental Platform for AI evaluation (Property of CAIR)

### Important Stuff

To build with the standard network interface agent which just explores in the scenario.

```
gradle build
gradle run
```

To use @Yusri Dollie's `R-BDI` agent using `jadex` use

```
gradle build -Pbcog
gradle run -Pbcog
```

To use @Yusri Dollie' `plain BDI` agent using `jadex` use

```
gradle build -Pbdi
gradle run -Pbdi
```


## Notes

There should be a gradle plugin available for your IDE of choice.


