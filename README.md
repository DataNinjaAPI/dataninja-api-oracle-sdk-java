## Data Ninja Oracle Java client

### Introduction

This is a Java client for interfacing the Data Ninja Smart Data services with Oracle Spatial RDF services. Please see the Data Ninja documentation for the specification of the Smart Sentiment REST API.

Please follow these instructions to get started with the Java Client. A pre-built of a client jar is included in the jar directory. Alternatively, you can build from source using JDK 1.8 and Maven.  

#### Build

Edit the configuration file 

    $ vi src/main/resources/dataninja.conf

Add your Mashape key to the configuration file. If you don't have a key, please signup by visiting the Data Ninja home page hosted by Mashape.

    dataninja {
        ...
        mashape-key="<Your-Mashape-Key>"
    } 

For your convinience, Oracle Spatial RDF client jar files are included in the oraclejars directory. Alternatively, you can download the jar files directly from Oracle Developer website or use the version included in the Oracle Big Data applicance. Perform a Maven build of the project:

    $ mvn clean package install

#### Run Demo

Please see net.dataninja.oracle.demo.OracleDataNinjaDemo.java class for guidance on how to use the Java client. You can run the command-line demo to see how the client works:

    $ java -cp ./jar/OracleDataNinjaClient.jar:./oraclejars/'*' net.dataninja.oracle.demo.OracleDataNinjaDemo
