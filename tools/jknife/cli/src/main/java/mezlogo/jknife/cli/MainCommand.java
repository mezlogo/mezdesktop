package mezlogo.jknife.cli;

import mezlogo.jknife.cli.commands.AmqpPublishCommand;
import mezlogo.jknife.cli.commands.AmqpSubscribeCommand;
import mezlogo.jknife.cli.commands.StompResubsciableCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "jknife",
        subcommands = {CommandLine.HelpCommand.class, AmqpPublishCommand.class, AmqpSubscribeCommand.class, StompResubsciableCommand.class}
)
public class MainCommand {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new MainCommand()).execute(args);
//        System.exit(exitCode);
    }
}
