package mezlogo.jknife.cli.commands;

import mezlogo.jknife.stomp.StompLabContext;
import mezlogo.jknife.stomp.StompManager;
import mezlogo.jknife.websocketevent.WebsocketEvent;
import mezlogo.jknife.websocketevent.WebsocketOpenEvent;
import picocli.CommandLine;

import java.net.URI;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@CommandLine.Command(name = "stomplab")
public class StompResubsciableCommand implements Runnable {
    @CommandLine.Option(names = {"--uri"}, required = true, arity = "1..*")
    public Collection<URI> uris;

    @CommandLine.Option(names = {"--times"}, required = true)
    public int times;

    @CommandLine.Option(names = {"--heartbeat"}, required = false, defaultValue = "10000")
    public int heartbeat;

    @CommandLine.Option(names = {"--verbose"}, required = false, defaultValue = "false")
    public boolean verbose;


    @Override
    public void run() {
        StompManager stompManager = StompManager.lab(uris, times, heartbeat, verbose ? StompResubsciableCommand::verboseEventConsumer : StompResubsciableCommand::filteredEventConsumer);
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(stompManager::ping, heartbeat, heartbeat, TimeUnit.MILLISECONDS);
    }

    public static void verboseEventConsumer(WebsocketEvent<StompLabContext> event) {
        System.out.println(event);
    }
    public static void filteredEventConsumer(WebsocketEvent<StompLabContext> event) {
        if (event instanceof WebsocketOpenEvent<StompLabContext> open) {
            System.out.printf("%s,OPEN,%s,%d%n", open.date(), open.ctx().uri(), open.ctx().id());
        }
    }
}
