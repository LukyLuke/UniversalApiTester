package ch.ranta.universal.tester.api.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ch.ranta.universal.tester.api.dto.Response;
import ch.ranta.universal.tester.domain.entities.ApiResponse;
import ch.ranta.universal.tester.service.GrpcService;
import ch.ranta.universal.tester.service.JmsService;

public class GrpcJmsTest {
	@Test
	void testCreate_Success() {
		// Given
		HttpStatus expectedStatus = HttpStatus.OK;
		String readQueue = "READ_FROM";
		String message = "MESSAGE";
		
		ApiResponse expectedResult = new ApiResponse();
		expectedResult.setMessage("RESPONSE");
		expectedResult.setId("UUID-MESSAGE-ID");
		
		JmsService jmsService = new JmsService(null, (q) -> Optional.of(expectedResult));
		GrpcService grpcService = new GrpcService((m) -> true, null);
		
		// When
		ResponseEntity<Response> result = new GrpcJms(grpcService, jmsService).create(message, readQueue);
		
		// Then
		assertThat(result.getStatusCode()).isEqualTo(expectedStatus);
		assertThat(result.getBody().getMessage()).isEqualTo(expectedResult.getMessage());
		assertThat(result.getBody().getMessageId()).isEqualTo(expectedResult.getId());
		assertThat(result.getBody().getRtt()).isGreaterThan(0);
	}
	
	@Test
	void testCreate_Failed_Sending() throws Exception {
		// Given
		HttpStatus expectedStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		String readQueue = "READ_FROM";
		String message = "MESSAGE";
		
		ApiResponse expectedResult = new ApiResponse();
		expectedResult.setMessage("RESPONSE");
		expectedResult.setId("UUID-MESSAGE-ID");
		
		JmsService jmsService = new JmsService(null, (q) -> Optional.of(expectedResult));
		GrpcService grpcService = new GrpcService((m) -> false, null);
		
		// When
		ResponseEntity<Response> result = new GrpcJms(grpcService, jmsService).create(message, readQueue);
		
		// Then
		assertThat(result.getStatusCode()).isEqualTo(expectedStatus);
	}
	
	@Test
	void testCreate_Failed_Receive() throws Exception {
		// Given
		HttpStatus expectedStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		String readQueue = "READ_FROM";
		String message = "MESSAGE";

		JmsService jmsService = new JmsService(10, null, (q) -> Optional.empty());
		GrpcService grpcService = new GrpcService((m) -> true, null);
		
		// When
		ResponseEntity<Response> result = new GrpcJms(grpcService, jmsService).create(message, readQueue);
		
		// Then
		assertThat(result.getStatusCode()).isEqualTo(expectedStatus);
	}
	
	@Test
	void testCreate_Sender_Exception() throws Exception {
		// Given
		HttpStatus expectedStatus = HttpStatus.GATEWAY_TIMEOUT;
		String readQueue = "READ_FROM";
		String message = "MESSAGE";
		
		ApiResponse expectedResult = new ApiResponse();
		expectedResult.setMessage("RESPONSE");
		expectedResult.setId("UUID-MESSAGE-ID");
		
		JmsService jmsService = new JmsService(null, (q) -> Optional.of(expectedResult));
		
		GrpcService grpcService = mock(GrpcService.class);
		doThrow(InterruptedException.class).when(grpcService).send(message);
		
		// When
		ResponseEntity<Response> result = new GrpcJms(grpcService, jmsService).create(message, readQueue);
		
		// Then
		assertThat(result.getStatusCode()).isEqualTo(expectedStatus);
	}
	
	@Test
	void testCreate_Receiver_Exception() throws Exception {
		// Given
		HttpStatus expectedStatus = HttpStatus.GATEWAY_TIMEOUT;
		String readQueue = "READ_FROM";
		String message = "MESSAGE";
		
		JmsService jmsService = mock(JmsService.class);
		doThrow(InterruptedException.class).when(jmsService).receive(readQueue);
		
		GrpcService grpcService = new GrpcService((m) -> true, null);
		
		// When
		ResponseEntity<Response> result = new GrpcJms(grpcService, jmsService).create(message, readQueue);
		
		// Then
		assertThat(result.getStatusCode()).isEqualTo(expectedStatus);
	}
}
