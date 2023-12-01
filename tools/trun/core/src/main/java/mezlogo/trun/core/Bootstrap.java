package mezlogo.trun.core;

//import ch.qos.logback.access.servlet.TeeFilter;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Bootstrap {
    /*
    webAppPath: [exploded war dir|war file]
    ?classesPath: path to compiled classes
    ?port = 8080
    ?contextPath = ""
     */
    public static void main(String[] args) throws LifecycleException, IOException {
        System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
//        SLF4JBridgeHandler.removeHandlersForRootLogger();
//        SLF4JBridgeHandler.install();

        TrunConf trunConf = parseArgs(args);

        File warArchive = Paths.get(trunConf.webAppPath).toFile();

        if (!warArchive.exists()) {
            throw new RuntimeException("War archive does not exist at specified path: " + trunConf.webAppPath);
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(trunConf.port);
        tomcat.getConnector();

        File webAppTempDir = createWebAppTempDir(trunConf);

        Paths.get(webAppTempDir.getAbsolutePath(), "webapps").toFile().mkdirs();

        tomcat.setBaseDir(webAppTempDir.getAbsolutePath());


        StandardContext context = (StandardContext) tomcat.addWebapp(trunConf.contextPath, warArchive.getAbsolutePath());


//        context.setDelegate(true);
//        context.setCookieProcessor(new LegacyCookieProcessor());
//        context.addServletContainerInitializer(new JasperInitializer(), null);
//        context.addServletContainerInitializer(new WsSci(), null);

//        context.addLifecycleListener(event -> {
//            if (Lifecycle.CONFIGURE_START_EVENT.equals(event.getType())) {
//                ((StandardContext) event.getLifecycle()).removeParameter("logbackConfigLocation");
//            }
//        });

//        if (!"".equals(trunConf.classesPath)) {
//            File additionalClasses = new File(trunConf.classesPath);
//
//            if (!additionalClasses.exists()) {
//                throw new RuntimeException("Additional classes path does not exist: " + trunConf.classesPath);
//            }
//
//            if (!additionalClasses.isDirectory()) {
//                throw new RuntimeException("Additional classes path does not a directory: " + trunConf.classesPath);
//            }
//
//            WebResourceRoot resources = new StandardRoot(context);
//            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionalClasses.getAbsolutePath(), "/"));
//            context.setResources(resources);
//        }
//
//
//        String teeLogback = System.getProperty("teeLogback");

//        if (null != teeLogback) {
//            boolean isFile = new File(teeLogback).isFile();
//            if (!isFile) {
//                throw new IllegalArgumentException("-DteeLogback should be a file with logback config. Passed: " + teeLogback);
//            }
//
//            System.out.println("===SETTING TeeFilter from: " + teeLogback);
//
//            FilterDef filterDef = new FilterDef();
//            filterDef.setFilter(new TeeFilter());
//            filterDef.setFilterName("TeeFilter");
//            context.addFilterDef(filterDef);
//
//            FilterMap filterMap = new FilterMap();
//            filterMap.setFilterName("TeeFilter");
//            filterMap.addURLPattern("/*");
//            context.addFilterMapBefore(filterMap);
//
//            LogbackValveAbsFile valve = new LogbackValveAbsFile();
//            valve.initLogbackFile(teeLogback);
//            context.addValve(valve);
//        }

        tomcat.start();
        tomcat.getServer().await();
    }

    public static File createWebAppTempDir(TrunConf trunConf) {
        File trunTmpRoot = new File(System.getProperty("user.home"), ".trun");
        trunTmpRoot.mkdirs();

        String[] webAppPathName = trunConf.webAppPath.split(File.separator);
        String appBase = webAppPathName[webAppPathName.length - 1];

        File webAppTempDir = Paths.get(trunTmpRoot.getAbsolutePath(), appBase).toFile();
        if (webAppTempDir.exists()) {
            webAppTempDir.delete();
        }
        webAppTempDir.mkdirs();

        return webAppTempDir;
    }

    /*
    webAppPath
    webAppPath classesPath
    webAppPath classesPath 8081
    webAppPath classesPath 8081 /myapp
    webAppPath 8081
    webAppPath 8081 /myapp
     */
    public static TrunConf parseArgs(String... args) {
        if (0 == args.length) {
            throw new RuntimeException("first argument should be exiten web archive");
        }

        int port = 8080;
        String classesPath = "";

        if (2 <= args.length) {
            String classesPathOrPort = args[1];
            try {
                port = Integer.parseInt(classesPathOrPort);
            } catch (NumberFormatException e) {
                classesPath = classesPathOrPort;
            }
        }

        String contextPath = "";
        if (3 <= args.length) {
            String contextPathOrPort = args[2];
            try {
                port = Integer.parseInt(contextPathOrPort);
            } catch (NumberFormatException e) {
                contextPath = contextPathOrPort;
            }
        }

        if (4 <= args.length) {
            contextPath = args[3];
        }

        return new TrunConf(args[0], classesPath, port, contextPath);
    }

    public static class TrunConf {
        public final String webAppPath;
        public final String classesPath;
        public final int port;
        public final String contextPath;

        public TrunConf(String webAppPath, String classesPath, int port, String contextPath) {
            this.webAppPath = webAppPath;
            this.classesPath = classesPath;
            this.port = port;
            this.contextPath = contextPath;
        }
    }
}
