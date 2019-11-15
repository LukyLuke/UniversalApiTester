package ch.ranta.universal.tester.api.grpc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;

import ch.ranta.universal.tester.dto.JsonEntity;

public class GrpcSenderTest {

	@Test
	public void testSend_Failed() throws Exception {
		// Given
		List<JsonEntity> list = new ArrayList<JsonEntity>();
	
		// When
		boolean result = new GrpcSender().send(list);
	
		// Then
		assertThat(result).isFalse();
	}
	
	@Test
	public void testSend_Successfull() throws Exception {
		// Given
		List<JsonEntity> list = new ArrayList<JsonEntity>();
		
		HttpClient client = mock(HttpClient.class);
		HttpResponse<String> response = mock(HttpResponse.class);
		doReturn(response).when(client).send(ArgumentMatchers.any(), ArgumentMatchers.any());
		HttpHeaders headers = mock(HttpHeaders.class);
		doReturn(headers).when(response).headers();
		
		// When
		boolean result = new GrpcSender(client).send(list);
		
		// Then
		assertThat(result).isTrue();
	}

}
