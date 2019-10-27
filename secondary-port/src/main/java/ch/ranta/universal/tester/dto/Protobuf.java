package ch.ranta.universal.tester.dto;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.google.protobuf.CodedOutputStream;

/**
 * Class for build a ProtoBuf Message out of a Json-Object
 */
public class Protobuf {

	public byte[] convert(List<JsonEntity> message) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		CodedOutputStream outputStream = CodedOutputStream.newInstance(output);
		
		return output.toByteArray();
	}

}
