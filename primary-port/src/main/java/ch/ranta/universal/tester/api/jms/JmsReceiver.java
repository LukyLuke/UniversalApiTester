package ch.ranta.universal.tester.api.jms;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.stereotype.Component;

import ch.ranta.universal.tester.domain.entities.ApiResponse;

@Component
public class JmsReceiver implements Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsReceiver.class);
	private final Queue<Message> messages = new LinkedList<>();
	private final ReceiverConfig config;

	@Autowired
	public JmsReceiver(ReceiverConfig config) {
		this.config = config;
	}
	
	@Override
	public Optional<ApiResponse> receive(String destination) {
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
		TextMessage message = (TextMessage) messages.remove();
		ApiResponse response = new ApiResponse();
		try {
			response.setMessage(message.getText());
			response.setId(message.getJMSMessageID());
		} catch (JMSException e) {
			LOGGER.debug("Exception: ", e);
		}
		return Optional.of(response);
	}

}
