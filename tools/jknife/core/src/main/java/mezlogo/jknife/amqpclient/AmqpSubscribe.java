package mezlogo.jknife.amqpclient;

public record AmqpSubscribe(String name, ExchangeType type, String routingKey, boolean durable, boolean autoDelete) {
}
