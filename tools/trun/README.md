trun: simplify deploy war into tomcat
===

features:
- minimal tomcat instance (wo jndi, default webapps and so on)
- juil patch logs everything to slf4j instead jul
- slf4j and logback are default logger system
- slf4j jcl and log4j bridges are included
- default logback.xml is included in trun 

---
usage:
- deploy war or exploded war with optional path to static resources (classes and webapp) + port + /contextPath

*libs are not supported, i.e. you should specify path to war with `WEB-INF/lib`*

`java -jar trun.jar $PATH_TO_WAR_OR_EXPLODED_WAR [$PATH_TO_CLASSESS] [$PATH_TO_RESOURCES] [$PORT [$CTX]]`

`java -jar trun.jar sample.war`

`java -jar trun.jar build/explodedwar`

`java -jar trun.jar build/classes resources/webapp 9000 /myapp`

- specify `-Dlogback.configurationFile` for external logback.xml configuration for both tomcat and webapp

---
modules:
- bootstrap: tomcat runner
- juliToSlf4jAdapter: adapts org.apache.juli.logging.LogFactory to slf4j
- executable: all-in-one jar

---
samples:
- greetServlet: systemProps and current time servlet
- webAppXml: example of web.xml
- webAppWoXml: example of web.xml less web app 
- logbackServlet: example with ONE format for both tomcat and webapp

---
todo:
- add jsp sample servlet `*.jsp`
- add resources sample servlet `/resources/*`
- add websocket sample

---
questions:
4) move wiki inside README.MD
3) make src and resources for war plugin, wo redundunt src/main/java
2) remove dups in samples inside build.gradle
1) what's happening when webappClassLoader runs wo delegating
```
logbackServlet + delegate: false
===
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/tmp/.tomcat6432443965808015208/webapps/ROOT/WEB-INF/lib/logback-classic-1.2.3.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/home/mezlogo/alma/tools/trun/trun/build/libs/trun.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.

SLF4J: Actual binding is of type [ch.qos.logback.classic.util.ContextSelectorStaticBinder]
```
