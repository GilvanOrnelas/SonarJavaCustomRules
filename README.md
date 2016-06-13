# SonarJavaCustomRules

# Project with custom quality rules executed with the Sonar 5.2^.
    
    This project implements a lot of userful code quality rules, that I feel miss when using the Sonar. 
    
# To execute with Sonar

The project uses an embedded maven to manage the dependencies and compile.
To use the rules download the project and run the command:
    ./mvnw package
Copy the generated JAR file to the Sonar server (${sonar_dir/extension/plugins} and restart the sonar.
All the rules shall appear in your repository.
    
# Some rules availalbe

#Single Responsibility Principle
        
Follows the idea that each field from the Class shall be used by all it's not private methods. For example:
    
    ```
    public class TwoResponsabilities {
        private String fieldA;
        private String fieldB;
        private String fieldC;
        ...
        public void methodA () {
            usesField(fieldA, fieldB);
        }
        
        public void methodB () {
            usesField(fieldC);
        }
    }
    ```
This class has 2 responsibilities. If we add the "fieldC" to methodA, and the fields "fieldA" and "fieldB" to the methodB, it will became to has only one responsibility.
This way is not as elaborated as many solutions that we see, like the LCOM4 algorithm, but it had been proved very userful, and more important, it is easy to the developers to understand how it works, which means less complaints :)

#Try-With-Resources
Identifies when resources are been closed with the finnaly block, instead from the very cool try-with-resource. I realy don't know why the sonar it self didn't implement this rule.

#Pom Checks
Search for unallowed dependencies, third parties dependencipes, property values and dependencies versions.
It is always good to know what is happening in your project.

#Null Assingments
Despite some times be necessary (very rare), it is not good to initialise objects with null. If this happens a lot, soon or later it will be back to hunt you.

#Objects that receives it selfs as arguments
Well, let's just say that this use to be a bad implementation to a domain, or object oriented model.

#Package visibility
Choose which package can use each other package.

#Much more
Probably the other rules, and even the ones above will not be very reused. But this is a very good example from how to use the plugin to create your own rules, and make the word a better place to code:)
