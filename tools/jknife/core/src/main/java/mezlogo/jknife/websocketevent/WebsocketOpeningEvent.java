package mezlogo.jknife.websocketevent;

import java.util.Date;

public record WebsocketOpeningEvent<CTX>(Date date, CTX ctx) implements WebsocketEvent<CTX> {
}
