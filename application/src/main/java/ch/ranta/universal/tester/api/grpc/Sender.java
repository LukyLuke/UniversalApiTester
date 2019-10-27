package ch.ranta.universal.tester.api.grpc;

import java.util.List;

import ch.ranta.universal.tester.dto.JsonEntity;

@FunctionalInterface
public interface Sender {
	boolean send(List<JsonEntity> json);
}
