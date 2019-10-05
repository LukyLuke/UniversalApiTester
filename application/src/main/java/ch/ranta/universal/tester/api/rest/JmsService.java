package ch.ranta.universal.tester.api.rest;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.ranta.universal.tester.api.jms.Receiver;
import ch.ranta.universal.tester.api.jms.Sender;

@Component
public class JmsService {
	private Sender sender;
	private Receiver receiver;

	@Autowired
	public JmsService(Sender sender, Receiver receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public String sendAndWait(String send, String read, String message) throws InterruptedException {
		sender.send(send, message);
		Optional<Object> result;
		while (!(result = receiver.receive(read)).isPresent()) {
			TimeUnit.MILLISECONDS.sleep(5);
		}
		return result.get().toString();
	}
}
