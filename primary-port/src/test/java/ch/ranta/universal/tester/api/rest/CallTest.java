package ch.ranta.universal.tester.api.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ch.ranta.universal.tester.service.JmsService;

public class CallTest {
	
	@Test
	void testCreate_Success() {
		// Given
		String expectedResult = "RESULT";
		String readQueue = "READ_FROM";
		String sendQueue = "SEND_TO";
		String message = "MESSAGE";
		JmsService service = new JmsService((q, m) -> {}, (q) -> Optional.of(expectedResult));
		
		// When
		String result = new Call(service).create(message, sendQueue, readQueue);
		
		// Then
		assertThat(result).isEqualTo(expectedResult);
	}
	
	@Test
	void testCreate_Failed() throws Exception {
		// Given
		String expectedResult = "TIMEOUT";
		String readQueue = "READ_FROM";
		String sendQueue = "SEND_TO";
		String message = "MESSAGE";

		JmsService service = new JmsService(10, (q, m) -> {}, (q) -> Optional.empty());
		
		// When
		String result = new Call(service).create(message, sendQueue, readQueue);
		
		// Then
		assertThat(result).isEqualTo(expectedResult);
	}
	
	@Test
	void testCreate_Exception() throws Exception {
		// Given
		String expectedResult = "ERROR";
		String readQueue = "READ_FROM";
		String sendQueue = "SEND_TO";
		String message = "MESSAGE";
		
		JmsService service = mock(JmsService.class);
		doThrow(InterruptedException.class).when(service).sendAndWait(sendQueue, readQueue, message);
		
		// When
		String result = new Call(service).create(message, sendQueue, readQueue);
		
		// Then
		assertThat(result).isEqualTo(expectedResult);
	}

}
