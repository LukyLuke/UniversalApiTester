package ch.ranta.universal.tester.api.jms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import javax.jms.Message;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

public class JmsReceiverTest {

	@Test
	public void testReceive_NotReceived() throws Exception {
		// Given
		String destination = "DESTINATION";
		ReceiverConfig config = mock(ReceiverConfig.class);
		
		// When
		Optional<Object> result = new JmsReceiver(config).receive(destination);
		
		// Then
		verify(config).registerJmsEndpoint(ArgumentMatchers.any());
		assertThat(result).isNotPresent();
	}
	
	@Test
	public void testReceive_Received() throws Exception {
		// Given
		String destination = "DESTINATION";
		ReceiverConfig config = mock(ReceiverConfig.class);
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				((SimpleJmsListenerEndpoint)invocation.getArgument(0))
					.getMessageListener()
					.onMessage(mock(Message.class));
				return null;
			}
		}).when(config).registerJmsEndpoint(ArgumentMatchers.any());
		
		// When
		Optional<Object> result = new JmsReceiver(config).receive(destination);
		
		// Then
		verify(config).registerJmsEndpoint(ArgumentMatchers.any());
		assertThat(result).isPresent();
	}

}
