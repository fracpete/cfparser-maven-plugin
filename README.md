# parsergen-maven-plugin
Maven plugin for generating parsers using JavaCup and JFlex.

Currently based on:
* [JavaCup](http://www2.cs.tum.edu/projects/cup/): 11b-20160615
* [JFlex](https://jflex.de/): 1.4.3


## Example

Assuming you have your `Scanner.flex` and `Parser.cup` files in the following 
directory:

```
src/main/resources/my/project/parser
```

Then use the following plugin definition in the `build` section of you your 
`pom.xml` file:

```xml
  <build>
    <plugins>
      <plugin>
        <groupId>com.github.fracpete</groupId>
        <artifactId>parsergen-maven-plugin</artifactId>
        <version>0.0.1</version>
        <configuration>
          <directories>
            <directory>${project.basedir}/src/main/resources/my/project/parser</directory>
          </directories>
        </configuration>
      </plugin>
    </plugins>
  </build>
```

Using `mvn parsergen:build` will place the generated Java code of the parser
in package `my.project.parser` (the corresponding directory to `resources/my/project/parser`
is `java/my/project/parser`).

**Note:** The `directory` tag can be supplied multiple times, in case you need
to compile multiple parsers.
