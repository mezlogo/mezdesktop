package mezlogo.trun.core;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public record TrunConfig(
        Optional<Trun.ConnectorSslConfig> secure,
        String context,
        Optional<Integer> httpPort,
        Path webapp,
        List<Path> classes,
        Optional<Path> resources,
        Optional<Path> libs) {
}
