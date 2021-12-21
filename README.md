# SonarJavaCustomRules

# Project with custom quality rules executed with the Sonar 5.2^.
    
# Testing 

First, clone the project. Then build it:
    ./mvnw package
Copy the generated JAR file into the sonar server (${sonar_dir/extension/plugins} and restart it.
All the rules shall appear in your repository.
    
# Some rules availalbe

#Single Responsibility Principle
        
The solution is based on matching the class fields against the methods using them. Each combination of fields used by each method is considered one responsibility. The goal is to enforce all non-private methods to use all the class fields.

#Try-With-Resources
Identifies when resources are being closed with the "finally" block and suggest replacing it with the try-with-resources.

#Pom Checks
Search for unallowed dependencies, third parties dependencies, property values, and dependencies versions..

#Null Assingments
Checks for null assignments.

#Self-argument objects.
Checks for instance-objects receiving them selfs as an argument.

#Package visibility
Check for package dependencies.
