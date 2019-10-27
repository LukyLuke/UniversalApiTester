package ch.ranta.universal.tester.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ProtobufTest {
	
	@Test
	void testConvert() {
		// Given
		List<JsonEntity> message = new ArrayList<>();
		message.add(new JsonEntity(0, "name"));
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result.toString()).isEqualTo("");
	}
	
	
}
