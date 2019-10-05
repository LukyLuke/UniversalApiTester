package ch.ranta.universal.tester.api.jms;

import java.util.Optional;

@FunctionalInterface
public interface Receiver {

	Optional<Object> receive(String destination);

}
