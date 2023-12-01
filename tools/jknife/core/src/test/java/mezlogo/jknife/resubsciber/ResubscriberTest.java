package mezlogo.jknife.resubsciber;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

class ResubscriberTest {
    SampleKey key = new SampleKey("key", 100);

    @Test
    void should_notify_created_resource() {
        var resource = Mockito.mock(SampleResource.class);

        Function<SampleKey, CompletableFuture<ResubscriberPair<SampleResource, SampleKey>>> factory = Mockito.mock(Function.class);
        Mockito.when(factory.apply(Mockito.any())).thenReturn(CompletableFuture.completedFuture(new ResubscriberPair<>(resource, new CompletableFuture<>())));

        var sut = new Resubscriber<>(factory);

        sut.init(Lists.list(key));
        String msg = "hello";
        for (SampleResource value : sut.values()) {
            value.send(msg);
        }

        Mockito.verify(resource, Mockito.times(1)).send(msg);
    }

    @Test
    void should_recreate_and_call_active_sub() {
        var resourceWillBeClosed = Mockito.mock(SampleResource.class);
        var resourceWillBeRecreated = Mockito.mock(SampleResource.class);

        Function<SampleKey, CompletableFuture<ResubscriberPair<SampleResource, SampleKey>>> factory = Mockito.mock(Function.class);
        var sut = new Resubscriber<>(factory);

        // GIVEN
        CompletableFuture<SampleKey> closeResource = new CompletableFuture<>();
        Mockito.when(factory.apply(Mockito.any()))
                .thenReturn(CompletableFuture.completedFuture(new ResubscriberPair<>(resourceWillBeClosed, closeResource)))
                .thenReturn(CompletableFuture.completedFuture(new ResubscriberPair<>(resourceWillBeRecreated, new CompletableFuture<>())));

        // WHEN
        sut.init(Lists.list(key));
        closeResource.complete(key);
        String msg = "hello";
        for (SampleResource value : sut.values()) {
            value.send(msg);
        }

        // THEN
        Mockito.verify(resourceWillBeClosed, Mockito.times(0)).send(Mockito.any());
        Mockito.verify(resourceWillBeRecreated, Mockito.times(1)).send(msg);
    }

    public interface SampleResource extends Closeable {
        void send(String msg);
    }

    public record SampleKey(String value, int num) {
    }
}