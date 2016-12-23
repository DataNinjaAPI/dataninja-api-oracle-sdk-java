## Data Ninja Services Java client for Oracle Spatial and Graph Database

### Introduction

This is a Java client for interfacing the Data Ninja Smart Sentiment services with Oracle Spatial and Graph using RDF. Please see the Data Ninja documentation for the specification of the Smart Sentiment REST API.

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

Please see net.dataninja.oracle.demo.OracleDataNinjaDemo.java class for guidance on how to use the Java client. Edit the dataninja.conf configuration file to add your Oracle database credential (default values are provided):

    oracle {
        jdbcURL="jdbc:oracle:thin:@127.0.0.1:1521:orcl"
        dbUser="scott"
        dbPasswd="tiger"
        dbModelName="extraction"
    }

You can run the command-line demo to see how the client works:

    $ java -cp ./jar/DataNinjaClientForOracle.jar:./oraclejars/'*' net.dataninja.oracle.demo.DataNinjaForOracleDemo
