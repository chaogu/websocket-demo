package hello;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class EchoWebSocket {
	// Store sessions
	private Queue<Session> sessions = new ConcurrentLinkedQueue<>();

	@OnWebSocketConnect
	public void connected(Session session) {
		System.out.println(session);
		sessions.add(session);
	}

	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason) {
		System.out.println(session);
		System.out.println(statusCode);
		System.out.println(reason);
		sessions.remove(session);
	}

	@OnWebSocketMessage
	public void message(Session session, String message) throws IOException {
		System.out.println(session);
		System.out.println("Got: " + message); // Print message
		session.getRemote().sendString(message); // and send it back
	}

	public void brocast() throws IOException {
		for (Session session : sessions) {
			session.getRemote().sendString("666");
		}
	}
}