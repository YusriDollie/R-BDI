# Q-Cog Agent Module
This is the repository of the -QCog Agent Module created by Michael Waltham that we are extending.

## Building
The building of this project has been greatly simplified using the gradle build system.

### Prerequisites

* [Gradle Installation](https://gradle.org/install/) - instructions to install gradle
* [SDKMAN](http://sdkman.io/) - this is a useful tool that will install java + gradle for you

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

To use @Jonah Hooper's agent (See PCog repository for setup instructions) use

```
gradle build -Ppcog
gradle run -Ppcog
```

To use RandomAgent - an agent that chooses actions at random use

```
gradle build -Prandom
gradle run -Prandom
```

To use the default `QCog` agent use

```
gradle build -Pwm
gradle run -Pwm
```



## Notes

There should be a gradle plugin available for your IDE of choice.

To connect the agent module with the Q-Cog testbed on a custom port you can use the following

```
gradle run -Ppcog -Pport=1302 # runs pcog on the default port
```
