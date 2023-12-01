package mezlogo.jknife.resubsciber;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class Resubscriber<KEY, RESOURCE extends Closeable> implements Consumer<KEY> {
    private final Map<KEY, RESOURCE> registry = new HashMap<>();
    private final Function<KEY, CompletableFuture<ResubscriberPair<RESOURCE, KEY>>> resourceFactory;

    public Resubscriber(Function<KEY, CompletableFuture<ResubscriberPair<RESOURCE, KEY>>> resourceFactory) {
        this.resourceFactory = resourceFactory;
    }

    @Override
    public void accept(KEY key) {
        RESOURCE resource = registry.remove(key);

        if (null != resource) {
            try {
                resource.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        resourceFactory.apply(key)
                .thenAccept(pair -> {
                    registry.put(key, pair.left());
                    pair.future().thenAccept(this::accept);
                });
    }

    public void init(List<KEY> keys) {
        for (KEY key : keys) {
            accept(key);
        }
    }

    public Collection<RESOURCE> values() {
        return registry.values();
    }
}
