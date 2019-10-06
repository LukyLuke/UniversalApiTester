package ch.ranta.universal.tester.api.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ch.ranta.universal.tester.api.dto.Response;
import ch.ranta.universal.tester.domain.entities.ApiResponse;
import ch.ranta.universal.tester.service.JmsService;

public class CallTest {
	
	@Test
	void testCreate_Success() {
		// Given
		String readQueue = "READ_FROM";
		String sendQueue = "SEND_TO";
		String message = "MESSAGE";
		
		HttpStatus expectedStatus = HttpStatus.OK;
		ApiResponse expectedResult = new ApiResponse();
		expectedResult.setMessage("RESPONSE");
		expectedResult.setId("UUID-MESSAGE-ID");
		JmsService service = new JmsService((q, m) -> {}, (q) -> Optional.of(expectedResult));
		
		// When
		ResponseEntity<Response> result = new Call(service).create(message, sendQueue, readQueue);
		
		// Then
		assertThat(result.getStatusCode()).isEqualTo(expectedStatus);
		assertThat(result.getBody().getMessage()).isEqualTo(expectedResult.getMessage());
		assertThat(result.getBody().getMessageId()).isEqualTo(expectedResult.getId());
		assertThat(result.getBody().getRtt()).isGreaterThan(0);
	}
	
	@Test
	void testCreate_Failed() throws Exception {
		// Given
		HttpStatus expectedStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		String readQueue = "READ_FROM";
		String sendQueue = "SEND_TO";
		String message = "MESSAGE";

		JmsService service = new JmsService(10, (q, m) -> {}, (q) -> Optional.empty());
		
		// When
		ResponseEntity<Response> result = new Call(service).create(message, sendQueue, readQueue);
		
		// Then
		assertThat(result.getStatusCode()).isEqualTo(expectedStatus);
	}
	
	@Test
	void testCreate_Exception() throws Exception {
		// Given
		HttpStatus expectedStatus = HttpStatus.GATEWAY_TIMEOUT;
		String readQueue = "READ_FROM";
		String sendQueue = "SEND_TO";
		String message = "MESSAGE";
		
		JmsService service = mock(JmsService.class);
		doThrow(InterruptedException.class).when(service).sendAndWait(sendQueue, readQueue, message);
		
		// When
		ResponseEntity<Response> result = new Call(service).create(message, sendQueue, readQueue);
		
		// Then
		assertThat(result.getStatusCode()).isEqualTo(expectedStatus);
	}
}
