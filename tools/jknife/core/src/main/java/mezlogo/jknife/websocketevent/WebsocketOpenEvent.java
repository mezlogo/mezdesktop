package mezlogo.jknife.websocketevent;

import java.util.Date;

public record WebsocketOpenEvent<CTX>(Date date, CTX ctx) implements WebsocketEvent<CTX> {
}
