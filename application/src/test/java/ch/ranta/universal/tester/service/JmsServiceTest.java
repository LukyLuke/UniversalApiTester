package ch.ranta.universal.tester.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ch.ranta.universal.tester.api.jms.Receiver;
import ch.ranta.universal.tester.api.jms.Sender;
import ch.ranta.universal.tester.domain.entities.ApiResponse;

public class JmsServiceTest {

	@Test
	public void testJmsServiceIntSenderReceiver() throws Exception {
		// Given
		String send = "SEND";
		String read = "READ";
		String message = "MESSAGE";
		
		Sender sender = mock(Sender.class);
		Receiver receiver = mock(Receiver.class);
		
		// When
		ApiResponse result = new JmsService(10, sender, receiver).sendAndWait(send, read, message);
		
		// Then
		verify(sender).send(send, message);
		verify(receiver, times(2)).receive(read);
		assertThat(result.getMessage()).isNull();
	}
	
	@Test
	public void testJmsServiceIntSenderReceiver_WithResult() throws Exception {
		// Given
		String send = "SEND";
		String read = "READ";
		String message = "MESSAGE";
		
		ApiResponse response = new ApiResponse();
		response.setMessage(message);
		
		Sender sender = mock(Sender.class);
		Receiver receiver = mock(Receiver.class);
		when(receiver.receive(read)).thenReturn(Optional.of(response));
		
		// When
		ApiResponse result = new JmsService(10, sender, receiver).sendAndWait(send, read, message);
		
		// Then
		verify(sender).send(send, message);
		verify(receiver, times(1)).receive(read);
		assertThat(result.getMessage()).isEqualTo(message);
	}

}
