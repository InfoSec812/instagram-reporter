# Instagram Post Count Collector

## Overview
A simple Java application to collect post counts for a given list of tags.

## Prerequisites
* Java JDK 8
* Apache Maven

## Building
```bash
mvn clean compile
```

## Running
```bash
java -jar target/instagram-reporter.jar <tag> [<tag> ...]
```

## Example Output
```
tag1                                                                                 :30922               
tag2                                                                                 :2467                
tag3                                                                                 :1132                
tag4                                                                                 :622                 
tag5                                                                                 :614                 
tag6                                                                                 :1553  
```