package ch.ranta.universal.tester.api.jms;

import java.util.Optional;

import ch.ranta.universal.tester.domain.entities.ApiResponse;

@FunctionalInterface
public interface Receiver {

	Optional<ApiResponse> receive(String destination);

}
