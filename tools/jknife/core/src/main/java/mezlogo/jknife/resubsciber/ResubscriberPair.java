package mezlogo.jknife.resubsciber;

import java.util.concurrent.CompletableFuture;

public record ResubscriberPair<LEFT, RIGHT>(LEFT left, CompletableFuture<RIGHT> future) {
}
