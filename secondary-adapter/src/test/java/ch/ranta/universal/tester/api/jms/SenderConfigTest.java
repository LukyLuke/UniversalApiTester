package ch.ranta.universal.tester.api.jms;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

public class SenderConfigTest {
	
	@Test
	void testSenderActiveMQConnectionFactory() {
		// Given
		String expectedBrokerUrl = "BROKER-URL";
		
		// When
		ActiveMQConnectionFactory result = new SenderConfig().setBrokerUrl(expectedBrokerUrl).senderActiveMQConnectionFactory();
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result.getBrokerURL()).isEqualTo(expectedBrokerUrl);
	}
	
	@Test
	void testCachingConnectionFactory() {
		// Given
		String expectedBrokerUrl = "BROKER-URL";
		
		// When
		CachingConnectionFactory result = new SenderConfig().setBrokerUrl(expectedBrokerUrl).cachingConnectionFactory();
		
		// Then
		assertThat(result).isNotNull();
		assertThat(((ActiveMQConnectionFactory)result.getTargetConnectionFactory()).getBrokerURL()).isEqualTo(expectedBrokerUrl);
	}
	
	@Test
	void testJmsTemplate() {
		// Given
		String expectedBrokerUrl = "BROKER-URL";
		
		// When
		JmsTemplate result = new SenderConfig().setBrokerUrl(expectedBrokerUrl).jmsTemplate();
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result.isMessageIdEnabled()).isTrue();
	}
	
	@Test
	void testSender() {
		// Given
		String expectedBrokerUrl = "BROKER-URL";
		
		// When
		JmsSender result = new SenderConfig().setBrokerUrl(expectedBrokerUrl).sender();
		
		// Then
		assertThat(result).isNotNull();
	}
	
}
