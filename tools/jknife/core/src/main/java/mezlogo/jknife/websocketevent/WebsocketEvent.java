package mezlogo.jknife.websocketevent;

import java.util.Date;

public sealed interface WebsocketEvent<CTX> permits WebsocketBinaryMessageEvent, WebsocketCloseEvent, WebsocketErrorEvent, WebsocketOpenEvent, WebsocketOpeningEvent, WebsocketSendMessageEvent, WebsocketTextMessageEvent {
    Date date();
    CTX ctx();
}
