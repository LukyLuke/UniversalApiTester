package ch.ranta.universal.tester.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.ranta.universal.tester.api.jms.Receiver;
import ch.ranta.universal.tester.api.jms.Sender;
import ch.ranta.universal.tester.domain.entities.ApiResponse;

@Component
public class JmsService {
	private static int MAX_READ_MILLISECONDS = 1000;
	private final static int PAUSE_READ_MILLISECONDS = 5;
	
	private Sender sender;
	private Receiver receiver;

	@Autowired
	public JmsService(Sender sender, Receiver receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}
	
	/**
	 * For testing the timeout.
	 */
	public JmsService(int maxTimeout, Sender sender, Receiver receiver) {
		this(sender, receiver);
		MAX_READ_MILLISECONDS = maxTimeout;
	}
	
	public ApiResponse sendAndWait(String send, String read, String message) throws InterruptedException {
		int timeout = MAX_READ_MILLISECONDS;
		sender.send(send, message);

		Optional<ApiResponse> result;
		while (!(result = receiver.receive(read)).isPresent() && ((timeout -= PAUSE_READ_MILLISECONDS) > 0)) {
			TimeUnit.MILLISECONDS.sleep(PAUSE_READ_MILLISECONDS);
		}
		return result.orElse(new ApiResponse());
	}
}
