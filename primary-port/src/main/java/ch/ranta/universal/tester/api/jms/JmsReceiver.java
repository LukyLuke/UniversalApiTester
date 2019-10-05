package ch.ranta.universal.tester.api.jms;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiver implements Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsReceiver.class);
	private Queue<Object> messages = new LinkedList<Object>();
	private ReceiverConfig config;

	@Autowired
	public JmsReceiver(ReceiverConfig config) {
		this.config = config;
	}
	
	@Override
	public Optional<Object> receive(String destination) {
		SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
		endpoint.setId("FooBar");
		endpoint.setDestination(destination);
		endpoint.setMessageListener(message -> {
			messages.add(message);
			LOGGER.info("Message received: {}", message);
		});
		config.registerJmsEndpoint(endpoint);
		
		if (messages.isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable(messages.remove());
	}

}
