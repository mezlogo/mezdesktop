package mezlogo.jknife.amqpclient;

public record RabbitConnectionConfig(String host, int port, String username, String password, String vhost, boolean tls) {
}
