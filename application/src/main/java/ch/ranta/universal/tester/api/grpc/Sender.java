package ch.ranta.universal.tester.api.grpc;

@FunctionalInterface
public interface Sender {
	boolean send(String message);
}
