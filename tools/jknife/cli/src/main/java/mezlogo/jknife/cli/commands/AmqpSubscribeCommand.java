package mezlogo.jknife.cli.commands;


import mezlogo.jknife.amqpclient.AmqpSubscribe;
import mezlogo.jknife.amqpclient.RabbitClient;
import picocli.CommandLine;

@CommandLine.Command(name = "amqpsubscribe")
public class AmqpSubscribeCommand extends AmqpAbstractCommand implements Runnable {
    @CommandLine.Option(names = {"--routingkey"}, defaultValue = "#")
    public String routingkey;

    public AmqpSubscribe amqpSubscribe() {
        return new AmqpSubscribe(exchange, exchangeType, routingkey, durable, false);
    }

    @Override
    public void run() {
        RabbitClient.open(config())
                .onMessage(System.out::println)
                .subscribe(amqpSubscribe());

        System.out.println("Waiting for messages");

        try {
            Thread.sleep(10_000_000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
