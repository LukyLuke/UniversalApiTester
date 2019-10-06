package ch.ranta.universal.tester.api.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpoint;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.listener.MessageListenerContainer;

@Configuration
@EnableJms
public class ReceiverConfig implements JmsListenerConfigurer {

	@Value("${activemq.broker-url}")
	private String brokerUrl;

	private JmsListenerEndpointRegistrar registrar;

	/**
	 * For testing.
	 */
	ReceiverConfig setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
		return this;
	}
	
	@Bean
	public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
		ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory();
		amqConnectionFactory.setBrokerURL(brokerUrl);
		return amqConnectionFactory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(receiverActiveMQConnectionFactory());
		return factory;
	}

	@Bean
	public JmsReceiver receiver() {
		return new JmsReceiver(this);
	}
	
	@Override
	public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
		this.registrar = registrar;
	}
	
	public void registerJmsEndpoint(JmsListenerEndpoint endpoint) {
		MessageListenerContainer listener = registrar.getEndpointRegistry().getListenerContainer(endpoint.getId());
		if (listener == null) {
			registrar.registerEndpoint(endpoint);
		}
	}
}
