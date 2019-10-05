package ch.ranta.universal.tester.api.jms;

@FunctionalInterface
public interface Sender {

	void send(String destinationName, String message);

}
