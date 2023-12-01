package mezlogo.jknife.cli.commands;


import mezlogo.jknife.amqpclient.AmqpPublish;
import mezlogo.jknife.amqpclient.RabbitClient;
import picocli.CommandLine;

import java.nio.charset.StandardCharsets;

@CommandLine.Command(name = "amqppublish")
public class AmqpPublishCommand extends AmqpAbstractCommand implements Runnable {
    @CommandLine.Option(names = {"--routingkey"}, defaultValue = "")
    public String routingkey;

    @CommandLine.Option(names = {"--body"})
    public String body;


    public AmqpPublish amqpPublish() {
        var bodyAsBytes = null == body ? null : body.getBytes(StandardCharsets.UTF_8);
        return new AmqpPublish(exchange, exchangeType, routingkey, bodyAsBytes);
    }

    @Override
    public void run() {
        try (var rabbit = RabbitClient.open(config())) {
            rabbit.publish(amqpPublish());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
