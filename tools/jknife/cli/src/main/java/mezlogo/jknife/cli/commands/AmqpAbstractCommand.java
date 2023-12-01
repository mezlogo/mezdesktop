package mezlogo.jknife.cli.commands;

import mezlogo.jknife.amqpclient.ExchangeType;
import mezlogo.jknife.amqpclient.RabbitConnectionConfig;
import picocli.CommandLine;

@CommandLine.Command(name = "amqpabstract")
public abstract class AmqpAbstractCommand {
    @CommandLine.Option(names = {"--port"}, defaultValue = "${JKNIFE_AMQP_PORT:-5672}")
    public int port;
    @CommandLine.Option(names = {"--host"}, defaultValue = "${JKNIFE_AMQP_HOST:-localhost}")
    public String host;
    @CommandLine.Option(names = {"--vhost"}, defaultValue = "${JKNIFE_AMQP_VHOST:-/}")
    public String vhost;
    @CommandLine.Option(names = {"--username"}, defaultValue = "${JKNIFE_AMQP_USERNAME:-guest}")
    public String username;
    @CommandLine.Option(names = {"--password"}, defaultValue = "${JKNIFE_AMQP_PASSWORD:-guest}")
    public String password;

    @CommandLine.Option(names = {"--exchangeType"}, defaultValue = "TOPIC", description = "Valid values: ${COMPLETION-CANDIDATES}")
    public ExchangeType exchangeType;

    @CommandLine.Option(names = {"--tls"}, defaultValue = "${JKNIFE_AMQP_TLS:-false}")
    public boolean tls;

    @CommandLine.Option(names = {"--durable"}, defaultValue = "${JKNIFE_AMQP_DURABLE:-true}")
    public boolean durable;

    @CommandLine.Option(names = {"--exchange"}, required = true)
    public String exchange;


    public RabbitConnectionConfig config() {
        return new RabbitConnectionConfig(host, port, username, password, vhost, tls);
    }
}
