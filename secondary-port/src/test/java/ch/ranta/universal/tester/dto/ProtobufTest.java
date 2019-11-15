package ch.ranta.universal.tester.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.protobuf.WireFormat;

public class ProtobufTest {
	
	@Test
	void testConvert_Empty() throws IOException {
		// Given
		String expected = "";

		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(new String(result)).isEqualTo(expected);
	}
	
	@Test
	void testConvert_String() throws IOException {
		// Given
		String given = "VALUE";
		byte[] expected = new byte[] { WireFormat.WIRETYPE_LENGTH_DELIMITED, 5, 'V', 'A', 'L', 'U', 'E' };
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").stringValue(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).contains(expected);
	}
	
	@Test
	void testConvert_Boolean() throws IOException {
		// Given
		Boolean given = Boolean.TRUE;
		byte[] expected = new byte[] {WireFormat.WIRETYPE_VARINT, 1};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").booleanValue(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_Double() throws IOException {
		// Given
		Double given = Double.valueOf((double)666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_FIXED64, 0, 0, 0, 0, 0, -48, -124, 64};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").doubleValue(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_Float() throws IOException {
		// Given
		Float given = Float.valueOf((float)666.66);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_FIXED32, 61, -86, 38, 68};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").floatValue(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_Int32() throws IOException {
		// Given
		Integer given = Integer.valueOf(666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_VARINT, -102, 5};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").int32Value(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_UInt32() throws IOException {
		// Given
		Integer given = Integer.valueOf(666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_VARINT, -102, 5};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").uint32Value(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_SInt32() throws IOException {
		// Given
		Integer given = Integer.valueOf(666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_VARINT, -76, 10};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").sint32Value(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_Fixed32() throws IOException {
		// Given
		Integer given = Integer.valueOf(666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_FIXED32, -102, 2, 0, 0};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").fixed32Value(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_SFixed32() throws IOException {
		// Given
		Integer given = Integer.valueOf(666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_FIXED32, -102, 2, 0, 0};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").sfixed32Value(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_Int64() throws IOException {
		// Given
		Long given = Long.valueOf(666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_VARINT, -102, 5};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").int64Value(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_UInt64() throws IOException {
		// Given
		Long given = Long.valueOf(666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_VARINT, -102, 5};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").uint64Value(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_SInt64() throws IOException {
		// Given
		Long given = Long.valueOf(666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_VARINT, -76, 10};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").sint64Value(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_Fixed64() throws IOException {
		// Given
		Long given = Long.valueOf(666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_FIXED64, -102, 2, 0, 0, 0, 0, 0, 0};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").fixed64Value(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_SFixed64() throws IOException {
		// Given
		Long given = Long.valueOf(666);
		byte[] expected = new byte[] {WireFormat.WIRETYPE_FIXED64, -102, 2, 0, 0, 0, 0, 0, 0};
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").sfixed64Value(given).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testConvert_Nested() throws IOException {
		// Given
		String given1 = "VALUE-1";
		String given2 = "VALUE-2";
		byte[] expected = new byte[] {WireFormat.WIRETYPE_LENGTH_DELIMITED, 18,
				WireFormat.WIRETYPE_LENGTH_DELIMITED, 7, 'V', 'A', 'L', 'U', 'E', '-', '1',
				WireFormat.WIRETYPE_LENGTH_DELIMITED, 7, 'V', 'A', 'L', 'U', 'E', '-', '2'
				};
		
		List<JsonEntity> list = new ArrayList<JsonEntity>();
		list.add(JsonEntity.builder().key(0).name("name").stringValue(given1).build());
		list.add(JsonEntity.builder().key(0).name("name").stringValue(given2).build());
		
		List<JsonEntity> message = new ArrayList<>();
		message.add(JsonEntity.builder().key(0).name("name").objectValue(list).build());
		
		// When
		byte[] result = new Protobuf().convert(message);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(expected);
	}
	
	
}
