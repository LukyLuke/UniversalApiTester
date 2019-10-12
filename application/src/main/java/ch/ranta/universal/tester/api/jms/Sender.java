package ch.ranta.universal.tester.api.jms;

@FunctionalInterface
public interface Sender {

	boolean send(String destinationName, String message);

}
