package ch.ranta.universal.tester.api.jms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpoint;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.listener.MessageListenerContainer;

public class ReceiverConfigTest {

	@Test
	void testSenderActiveMQConnectionFactory() {
		// Given
		String expectedBrokerUrl = "BROKER-URL";
		
		// When
		ActiveMQConnectionFactory result = new ReceiverConfig().setBrokerUrl(expectedBrokerUrl).receiverActiveMQConnectionFactory();
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result.getBrokerURL()).isEqualTo(expectedBrokerUrl);
	}
	
	@Test
	void testCachingConnectionFactory() {
		// Given
		String expectedBrokerUrl = "BROKER-URL";
		
		// When
		DefaultJmsListenerContainerFactory result = new ReceiverConfig().setBrokerUrl(expectedBrokerUrl).jmsListenerContainerFactory();
		
		// Then
		assertThat(result).isNotNull();
	}
	
	@Test
	void testReceiver() {
		// Given
		String expectedBrokerUrl = "BROKER-URL";
		
		// When
		JmsReceiver result = new ReceiverConfig().setBrokerUrl(expectedBrokerUrl).receiver();
		
		// Then
		assertThat(result).isNotNull();
	}
	
	@ParameterizedTest
	@CsvSource({
		"false",
		"true"
	})
	void testRegisterJmsEndpoint(boolean registered) {
		// Given
		String expectedBrokerUrl = "BROKER-URL";
		final String givenId = "TESTING";
		
		JmsListenerEndpointRegistrar registrar = Mockito.mock(JmsListenerEndpointRegistrar.class);
		JmsListenerEndpointRegistry listenerRegistry = mock(JmsListenerEndpointRegistry.class);
		when(listenerRegistry.getListenerContainer(givenId)).thenReturn(registered ? mock(MessageListenerContainer.class) :null);
		when(registrar.getEndpointRegistry()).thenReturn(listenerRegistry);
		
		ReceiverConfig impl = new ReceiverConfig().setBrokerUrl(expectedBrokerUrl);
		impl.configureJmsListeners(registrar);
		
		JmsListenerEndpoint givenEndpoint = new JmsListenerEndpoint() {
			public void setupListenerContainer(MessageListenerContainer listenerContainer) {}
			public String getId() { return givenId; }
		};
		
		// When
		impl.registerJmsEndpoint(givenEndpoint);
		
		// Then
		verify(registrar, times(registered ? 0 : 1)).registerEndpoint(givenEndpoint);
	}


}
