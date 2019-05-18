# Versions Helper Maven Plugin

Maven Plugin to parse version strings and set properties containing parts of the versions

[![Build Status](https://travis-ci.org/knowhowlab/versions-helper-maven-plugin.svg?branch=master)](https://travis-ci.org/knowhowlab/versions-helper-maven-plugin)

## Features

- support of multiple version properties
- parse/validation of input versions
- set properties
    - major (next) version (part)
    - minor (next) version (part)
    - incremental or patch (next) version (part)
    - build number (next) and (part)

## Installation

```xml
<plugin>
    <groupId>org.knowhowlab.maven.plugins</groupId>
    <artifactId>versions-helper-maven-plugin</artifactId>
    <version>(version)</version>
</plugin>
```

## Configuration

- **setVersions** (boolean:-true) - set properties with major/minor/patch/build current and next versions
- **setVersionParts** (boolean:-false) - set properties with major/minor/patch/build current and next version parts
 
## Properties

Input: 
- netty.version = 4.1.36.Final

Output:
- if setVersions = true
    - netty.version.majorVersion = "4" 
    - netty.version.minorVersion = "4.1" 
    - netty.version.incrementalVersion = "4.1.36" 
    - netty.version.buildNumberVersion = "4.1.36.0" 
    - netty.version.nextMajorVersion = "5" 
    - netty.version.nextMinorVersion = "4.2" 
    - netty.version.nextIncrementalVersion = "4.1.37" 
    - netty.version.nextBuildNumberVersion = "4.1.36.1"
- if setVersionParts = true
    - netty.version.majorVersionPart = "4"
    - netty.version.minorVersionPart = "1"
    - netty.version.incrementalVersionPart = "36"
    - netty.version.buildNumberPart = "0"
    - netty.version.nextMajorVersionPart = "5"
    - netty.version.nextMinorVersionPart = "2"
    - netty.version.nextIncrementalVersionPart = "37"
    - netty.version.nextBuildNumberPart = "1"

## Example

Define **netty.version** as maven property

```xml
<properties>
    <netty.version>4.1.36.Final</netty.version>
</properties>
```

Use Versions Helper Plugin

```xml
<plugin>
    <groupId>org.knowhowlab.maven.plugins</groupId>
    <artifactId>versions-helper-maven-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>parse</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <versions>
            <netty.version>${netty.version}</netty.version>
        </versions>
    </configuration>
</plugin>
```

Use set version in Bnd (OSGi) bnd.bndrun file.

```txt
...
-runproperties: ...

-runrequires: ...

-runbundles: \
    ...,\
    io.netty.buffer;version='[${netty.version.incrementalVersion},${netty.version.nextIncrementalVersion})',\
    io.netty.codec;version='[${netty.version.incrementalVersion},${netty.version.nextIncrementalVersion})',\
    io.netty.common;version='[${netty.version.incrementalVersion},${netty.version.nextIncrementalVersion})',\
    io.netty.resolver;version='[${netty.version.incrementalVersion},${netty.version.nextIncrementalVersion})',\
    io.netty.transport;version='[${netty.version.incrementalVersion},${netty.version.nextIncrementalVersion})',\
    ...
...
```