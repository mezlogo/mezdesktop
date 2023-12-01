package mezlogo.jknife.amqpclient;

public record AmqpPublish(String exchange, ExchangeType type, String routingkey, byte[] body) {
}
