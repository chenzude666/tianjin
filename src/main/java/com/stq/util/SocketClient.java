package com.stq.util;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

/**
 * Created by dazongshi on 17-10-30.
 */
public class SocketClient {
    private Socket socket;

    public static final String EVENT_CONNECT = "connect";
    public static final String EVENT_CONNECTING = "connecting";
    public static final String EVENT_DISCONNECT = "disconnect";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_MESSAGE = "message";
    public static final String EVENT_CONNECT_ERROR = "connect_error";
    public static final String EVENT_CONNECT_TIMEOUT = "connect_timeout";
    public static final String EVENT_RECONNECT = "reconnect";
    public static final String EVENT_RECONNECT_ERROR = "reconnect_error";
    public static final String EVENT_RECONNECT_FAILED = "reconnect_failed";
    public static final String EVENT_RECONNECT_ATTEMPT = "reconnect_attempt";
    public static final String EVENT_RECONNECTING = "reconnecting";
    public static final String EVENT_PING = "ping";
    public static final String EVENT_PONG = "pong";


    public SocketClient(String url) throws URISyntaxException {
        socket = IO.socket(url);
    }

    public void connect() {
        socket.connect();
    }

    public void disconnect() {
        socket.disconnect();
    }

    public SocketClient on(String event, Emitter.Listener listener) {
        socket.on(event, listener);
        return this;
    }

    public SocketClient emit(String event, Object... args) {
        socket.emit(event, args);
        return this;
    }

    public SocketClient send(Object... args) {
        socket.send(args);
        return this;
    }

}

