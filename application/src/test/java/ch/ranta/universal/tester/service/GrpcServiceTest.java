package ch.ranta.universal.tester.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.ranta.universal.tester.api.grpc.Receiver;
import ch.ranta.universal.tester.api.grpc.Sender;
import ch.ranta.universal.tester.dto.JsonEntity;

public class GrpcServiceTest {

	@Test
	public void testSend() throws Exception {
		// Given
		List<JsonEntity> message = new ArrayList<>();
		message.add(new JsonEntity(1, "NAME"));
		
		Sender sender = mock(Sender.class);
		when(sender.send(message)).thenReturn(true);
		Receiver receiver = mock(Receiver.class);
		
		// When
		boolean result = new GrpcService(sender, receiver).send(message);
		
		// Then
		assertThat(result).isTrue();
		verify(sender).send(message);
	}
	
	
	
}
