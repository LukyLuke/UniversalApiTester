package ch.ranta.universal.tester.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.ranta.universal.tester.api.grpc.Receiver;
import ch.ranta.universal.tester.api.grpc.Sender;
import ch.ranta.universal.tester.dto.JsonEntity;

@Component
public class GrpcService {

	private Sender sender;
	private Receiver receiver;

	@Autowired
	public GrpcService(Sender sender, Receiver receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}

	public boolean send(List<JsonEntity> json) throws InterruptedException {
		return sender.send(json);
	}

}
