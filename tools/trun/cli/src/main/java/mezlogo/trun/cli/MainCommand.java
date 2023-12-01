package mezlogo.trun.cli;

import mezlogo.trun.core.Trun;
import mezlogo.trun.core.TrunConfig;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CommandLine.Command(
        name = "trun",
        mixinStandardHelpOptions = true
)
public class MainCommand implements Runnable {
    @CommandLine.Option(names = {"-c", "--context"}, defaultValue = "")
    String context;

    @CommandLine.Option(names = {"-p", "--http"})
    Integer port;

    @CommandLine.Option(names = {"--https"})
    Integer httpsPort;

    @CommandLine.Option(names = {"--resources"})
    Path resources;

    @CommandLine.Option(names = {"--classes"})
    List<Path> classes;

    @CommandLine.Option(names = {"--libs"})
    Path libs;

    @CommandLine.Option(names = {"--keystore"})
    Path keystore;

    @CommandLine.Option(names = {"--pass"})
    String pass;

    @CommandLine.Option(names = {"--webapp"}, required = true)
    Path webapp;

    @CommandLine.Option(names = {"--h2"}, defaultValue = "false")
    boolean h2;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new MainCommand()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        // when none of the options explicitly presented select defaul 8080
        // either use port even for http
        var atLeastOnePort = null == port && null == httpsPort ? 8080 : null == port ? httpsPort : port;
        var secure = Optional.ofNullable(null != keystore ? new Trun.ConnectorSslConfig(keystore, pass, null == httpsPort ? atLeastOnePort : httpsPort, h2) : null);

        var httpPortOptional = Optional.ofNullable(secure.isPresent() && null == httpsPort ? null : atLeastOnePort);

        TrunConfig config = new TrunConfig(secure, context, httpPortOptional, webapp,
                null == classes ? Collections.emptyList() : classes,
                Optional.ofNullable(resources),
                Optional.ofNullable(libs));

        Tomcat tomcat = null;
        try {
            tomcat = Trun.deploy(config);
        } catch (LifecycleException | IOException e) {
            throw new RuntimeException(e);
        }
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
        tomcat.getServer().await();
    }
}
