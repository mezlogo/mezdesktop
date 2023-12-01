package mezlogo.jknife.amqpclient;

import java.util.Map;

public record AmqpMessage(String exchange, String routingkey, Map<String,Object> headers, String body) {
}
