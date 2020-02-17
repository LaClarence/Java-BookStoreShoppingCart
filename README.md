# Java-BookStoreShoppingCart


## Maven 

To run tomcat:

mvn clean install tomcat7:run

To launch unit Tests

mvn -P integration verify

To launch specific Unit test

mvn -P integration  -Dit.test=Module2_* verify


## Eclipse

To import Maven project in eclipse:
File > Import... > Maven > Existing maven project


### Set up maven configuration in eclipse

Run > run configuration... > Maven buils > add

name: Tomcat
base directory: ${workspace_loc:/bookstore}
goal: tomcat7:run

name: Integration Tests
base directory: ${workspace_loc:/bookstore}
goal: -P integration verify


