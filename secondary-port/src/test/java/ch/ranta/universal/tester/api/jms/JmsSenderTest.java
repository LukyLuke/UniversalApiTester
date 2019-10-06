package ch.ranta.universal.tester.api.jms;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;

public class JmsSenderTest {
	
	@Test
	void testSend() {
		// Given
		String destination = "DESTINATION";
		String message = "MESSAGE";
		JmsTemplate template = Mockito.mock(JmsTemplate.class);
		
		// When
		new JmsSender(template).send(destination, message);
		
		// Then
		verify(template).convertAndSend(destination, message);
	}
	
}
