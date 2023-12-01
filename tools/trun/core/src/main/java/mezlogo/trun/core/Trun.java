package mezlogo.trun.core;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.VersionLoggerListener;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.apache.coyote.http2.Http2Protocol;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Trun {
    /*
     * 1) setBaseDir BEFORE getConnector
     * 2) create OR request tomcat::connector
     * */
    public static Tomcat deploy(TrunConfig config) throws LifecycleException, IOException {
        Tomcat tomcat = new Tomcat();

        String tmp = Files.createTempDirectory(config.context().replaceAll("/", "") + "_" + config.httpPort().orElse(0)).toFile().getAbsolutePath();
        tomcat.setBaseDir(tmp);
        StandardContext context = (StandardContext) tomcat.addWebapp(config.context(), config.webapp().toFile().getAbsolutePath());

        config.httpPort().ifPresent(port -> {
            Http11Nio2Protocol protocolHandler = new Http11Nio2Protocol();
            Connector connector = new Connector(protocolHandler);
            connector.setPort(port);
            tomcat.setConnector(connector);
        });

        config.secure().ifPresent(secure -> {
            Connector connector = createSsl(secure);
            tomcat.setConnector(connector);
        });

        WebResourceRoot resourceRoot = new StandardRoot(context);
        config.classes().forEach(it ->
                resourceRoot.addPreResources(new DirResourceSet(resourceRoot, "/WEB-INF/classes", it.toFile().getAbsolutePath(), "/"))
        );
        config.resources().ifPresent(it ->
                resourceRoot.addPreResources(new DirResourceSet(resourceRoot, "/", it.toFile().getAbsolutePath(), "/"))
        );
        config.libs().ifPresent(it ->
                resourceRoot.addPreResources(new DirResourceSet(resourceRoot, "/WEB-INF/lib", it.toFile().getAbsolutePath(), "/"))
        );
        context.setResources(resourceRoot);

        tomcat.getServer().addLifecycleListener(new VersionLoggerListener());

        return tomcat;
    }

    private static Connector createSsl(ConnectorSslConfig secure) {
        Http11Nio2Protocol protocolHandler = new Http11Nio2Protocol();
        protocolHandler.setSslImplementationName("org.apache.tomcat.util.net.jsse.JSSEImplementation");
        protocolHandler.setKeystorePass(secure.pass());
        protocolHandler.setKeystoreFile(secure.keystore.toFile().getAbsolutePath());
        protocolHandler.setSecure(true);
        protocolHandler.setSSLEnabled(true);

        if (secure.useHttp2) {
            Http2Protocol upgradeProtocol = new Http2Protocol();
            protocolHandler.addUpgradeProtocol(upgradeProtocol);
        }

        Connector connector = new Connector(protocolHandler);
        connector.setPort(secure.httpsPort());
        connector.setScheme("https");

        return connector;
    }

    public record ConnectorSslConfig(Path keystore, String pass, int httpsPort, boolean useHttp2) {
    }

}
