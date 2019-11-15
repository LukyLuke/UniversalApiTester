package ch.ranta.universal.tester.dto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.google.protobuf.CodedOutputStream;

/**
 * Class for build a ProtoBuf Message out of a Json-Object
 */
public class Protobuf {

	public byte[] convert(List<JsonEntity> message) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		CodedOutputStream outputStream = CodedOutputStream.newInstance(output);
		
		for (JsonEntity entity : message) {
			if (Objects.nonNull(entity.getStringValue())) {
				outputStream.writeString(entity.getKey(), entity.getStringValue());

			} else if (Objects.nonNull(entity.getBooleanValue())) {
				outputStream.writeBool(entity.getKey(), entity.getBooleanValue());

			} else if (Objects.nonNull(entity.getDoubleValue())) {
				outputStream.writeDouble(entity.getKey(), entity.getDoubleValue());

			} else if (Objects.nonNull(entity.getFloatValue())) {
				outputStream.writeFloat(entity.getKey(), entity.getFloatValue());

			} else if (Objects.nonNull(entity.getInt32Value())) {
				outputStream.writeInt32(entity.getKey(), entity.getInt32Value());

			} else if (Objects.nonNull(entity.getUint32Value())) {
				outputStream.writeUInt32(entity.getKey(), entity.getUint32Value());

			} else if (Objects.nonNull(entity.getSint32Value())) {
				outputStream.writeSInt32(entity.getKey(), entity.getSint32Value());

			} else if (Objects.nonNull(entity.getFixed32Value())) {
				outputStream.writeFixed32(entity.getKey(), entity.getFixed32Value());

			} else if (Objects.nonNull(entity.getSfixed32Value())) {
				outputStream.writeSFixed32(entity.getKey(), entity.getSfixed32Value());

			} else if (Objects.nonNull(entity.getInt64Value())) {
				outputStream.writeInt64(entity.getKey(), entity.getInt64Value());

			} else if (Objects.nonNull(entity.getUint64Value())) {
				outputStream.writeUInt64(entity.getKey(), entity.getUint64Value());

			} else if (Objects.nonNull(entity.getSint64Value())) {
				outputStream.writeSInt64(entity.getKey(), entity.getSint64Value());

			} else if (Objects.nonNull(entity.getFixed64Value())) {
				outputStream.writeFixed64(entity.getKey(), entity.getFixed64Value());

			} else if (Objects.nonNull(entity.getSfixed64Value())) {
				outputStream.writeSFixed64(entity.getKey(), entity.getSfixed64Value());
			
			} else if (Objects.nonNull(entity.getObjectValue())) {
				outputStream.writeByteArray(entity.getKey(), convert(entity.getObjectValue()));
			}
		}
		outputStream.flush();
		return output.toByteArray();
	}

}
