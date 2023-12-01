package mezlogo.jknife.amqpclient;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class RabbitClient implements AutoCloseable {
    private final Connection connection;
    private final Channel channel;
    private final String queueName;
    private final HashSet<String> declaredExchanges = new HashSet<>();
    private final List<Consumer<AmqpMessage>> consumers = new ArrayList<>();

    public RabbitClient(Connection connection, Channel channel, String queueName) {
        this.connection = connection;
        this.channel = channel;
        this.queueName = queueName;
        try {
            channel.basicConsume(queueName, true, this::deliverHandler, consumerTag -> {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static RabbitClient open(RabbitConnectionConfig config) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(config.username());
        factory.setPassword(config.password());
        factory.setVirtualHost(config.vhost());
        factory.setHost(config.host());
        factory.setPort(config.port());

        if (config.tls()) {
            try {
                factory.useSslProtocol();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String queueName = channel.queueDeclare().getQueue();
            return new RabbitClient(connection, channel, queueName);
        } catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public RabbitClient subscribe(AmqpSubscribe amqpSubscribe) {
        String exchange = amqpSubscribe.name();

        try {
            if (this.declaredExchanges.add(exchange)) {
                channel.exchangeDeclare(exchange, amqpSubscribe.type().name().toLowerCase(), amqpSubscribe.durable(), amqpSubscribe.autoDelete(), Collections.emptyMap());
            }
            channel.queueBind(queueName, exchange, amqpSubscribe.routingKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    public RabbitClient publish(AmqpPublish amqpPublish) {
        try {
            channel.basicPublish(amqpPublish.exchange(), amqpPublish.routingkey(), null, amqpPublish.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public RabbitClient onMessage(Consumer<AmqpMessage> consumer) {
        this.consumers.add(consumer);
        return this;
    }

    private void deliverHandler(String consumerTag, Delivery message) {
        Envelope envelope = message.getEnvelope();
        AmqpMessage amqpMessage = new AmqpMessage(envelope.getExchange(), envelope.getRoutingKey(), message.getProperties().getHeaders(), new String(message.getBody(), StandardCharsets.UTF_8));
        consumers.forEach(c -> c.accept(amqpMessage));
    }

    @Override
    public void close() throws Exception {
        this.channel.close();
        this.connection.close();
    }
}
