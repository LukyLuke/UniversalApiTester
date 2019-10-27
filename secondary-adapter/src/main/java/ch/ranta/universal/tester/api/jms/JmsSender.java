package ch.ranta.universal.tester.api.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import ch.ranta.universal.tester.api.jms.Sender;

public class JmsSender implements Sender {
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsSender.class);
	private JmsTemplate template;

	public JmsSender(JmsTemplate template) {
		this.template = template;
	}
	
	@Override
	public boolean send(String destinationName, String message) {
		LOGGER.error("Sending message='{}'", message);
		try {
			this.template.convertAndSend(destinationName, message);
			return true;
		} catch (JmsException e) {
			LOGGER.error("Exception: {}", e);
		}
		return false;
	}
}
