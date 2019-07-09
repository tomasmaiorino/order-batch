It's simple application that does commission calculations.

## Used Technologies

**1. Java version 8.**

**2. Spring boot 2.1.6.RELEASE **

**3. Maven **  Life cycle management and project build.

## Considerations

## Usage In Local Machine

###### System requirements

JDK - Java version 1.8 or greater.

Maven 3.6 or greater

###### After download the fonts from [link github](https://github.com/tomasmaiorino/order-batch), to install the application and test it execute the maven command:

```$
mvn clean install
```

###### To run the application's tests execute the maven command:
```$
mvn test
```

##### Running the application.

####### 1. Build the jar.

```$
mvn package
```

####### 2. Run the application from the target folder:
```$
java -jar order-batch-0.0.1-SNAPSHOT.jar <file-path> e.g: java -jar order-batch-0.0.1-SNAPSHOT.jar "sample.orders.csv"
```