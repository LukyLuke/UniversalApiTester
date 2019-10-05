package ch.ranta.universal.tester.api.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class SenderConfig {
	@Value("${activemq.broker-url}")
	private String brokerUrl;

	@Bean
	public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
		ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory();
		amqConnectionFactory.setBrokerURL(brokerUrl);
		return amqConnectionFactory;
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory() {
		return new CachingConnectionFactory(senderActiveMQConnectionFactory());
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate(cachingConnectionFactory());
		template.setMessageIdEnabled(true);
		return template;
	}

	@Bean
	public JmsSender sender() {
		return new JmsSender(jmsTemplate());
	}
}
