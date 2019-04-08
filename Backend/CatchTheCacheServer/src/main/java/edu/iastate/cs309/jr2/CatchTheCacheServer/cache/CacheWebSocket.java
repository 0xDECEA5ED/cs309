package edu.iastate.cs309.jr2.CatchTheCacheServer.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import edu.iastate.cs309.jr2.CatchTheCacheServer.models.*;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/caches/m/{id}/websocket", configurator = CustomConfigurator.class)
@Component
public class CacheWebSocket {

	// Store all socket session and their corresponding id
	private static Map<Session, Integer> sessionIdMap = new HashMap<>();
	private static Map<Integer, Session> idSessionMap = new HashMap<>();

	@Autowired
	CacheService cacheService;

	private static final Logger logger = LoggerFactory.getLogger(CacheWebSocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("id") int id) throws IOException {
		logger.info("Connection Opened: " + session.getId());
		sessionIdMap.put(session, id);
		idSessionMap.put(id, session);
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		logger.info("Entered into Message: Got Message: " + message);
		// Message format is Sender: Message
		String info[] = new String[2];
		// Split message into sender and text
		info = message.split(":", 2);
		int id = sessionIdMap.get(session);
		MessageRequest request = new MessageRequest(info[0], info[1]);
		cacheService.postMessage(id, request);
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Connection Closed: " + session.getId());
		int id = sessionIdMap.get(session);
		sessionIdMap.remove(session);
		idSessionMap.remove(id);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// logger.info("Error: " + throwable.getMessage());
		throwable.printStackTrace();
	}

	public static void broadcast(Integer chatId, String message) throws IOException {
		sessionIdMap.forEach((session, id) -> {
			if (id == chatId) {
				logger.info("Broadcasting for chat: " + chatId);
				synchronized (session) {
					try {
						session.getBasicRemote().sendText(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
