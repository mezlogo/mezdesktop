plugins {
    id("mezlogo.common-build")
    `java-library`
}

val tomcatVersion = "9.0.62"

dependencies {
    api("org.apache.tomcat.embed:tomcat-embed-core:$tomcatVersion")
//    api("org.apache.tomcat.embed:tomcat-embed-websocket:$tomcatVersion")
    api("org.apache.tomcat:tomcat-websocket:$tomcatVersion")
    api("org.apache.tomcat.embed:tomcat-embed-el:$tomcatVersion")
    api("org.apache.tomcat.embed:tomcat-embed-jasper:$tomcatVersion")
    api("javax.servlet:javax.servlet-api:4.0.1")

//    api("org.slf4j:slf4j-api:1.7.26")
//    api("org.slf4j:jul-to-slf4j:1.7.26")
//    api("org.slf4j:jcl-over-slf4j:1.7.26")
//    api("org.slf4j:log4j-over-slf4j:1.7.26")
//
//    api("ch.qos.logback:logback-core:1.2.3")
//    api("ch.qos.logback:logback-classic:1.2.3")
//    api("ch.qos.logback:logback-access:1.2.3")
}