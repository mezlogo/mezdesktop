package mezlogo.jknife.websocketevent;

import java.util.Date;

public record WebsocketSendMessageEvent<CTX>(Date date, CTX ctx, String message) implements WebsocketEvent<CTX> {
}
