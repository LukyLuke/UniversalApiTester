package ch.ranta.universal.tester.api.jms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

public class JmsSenderTest {
	
	@Test
	void testSend() {
		// Given
		String destination = "DESTINATION";
		String message = "MESSAGE";
		JmsTemplate template = Mockito.mock(JmsTemplate.class);
		
		// When
		boolean result = new JmsSender(template).send(destination, message);
		
		// Then
		assertThat(result).isTrue();
		verify(template).convertAndSend(destination, message);
	}
	
	@Test
	void testSend_Failed() {
		// Given
		String destination = "DESTINATION";
		String message = "MESSAGE";
		JmsTemplate template = Mockito.mock(JmsTemplate.class);
		doThrow(new JmsException("") {}).when(template).convertAndSend(destination, message);
		
		// When
		boolean result = new JmsSender(template).send(destination, message);
		
		// Then
		assertThat(result).isFalse();
	}
	
}
