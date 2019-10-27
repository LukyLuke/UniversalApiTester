package ch.ranta.universal.tester.api.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ch.ranta.universal.tester.api.dto.JsonDto;
import ch.ranta.universal.tester.dto.JsonEntity;

public class JsonDtoMapper {
	public static List<JsonEntity> buildJsonEntityList(List<JsonDto> message) {
		return message.stream()
				.map(JsonDtoMapper::buildEntity)
				.collect(Collectors.toList());
	}

	private static JsonEntity buildEntity(JsonDto dto) {
		return Objects.isNull(dto) ? null :
			JsonEntity.builder()
				.key(dto.getKey())
				.name(dto.getName())
				
				.stringValue(dto.getStringValue())
				.booleanValue(dto.getBooleanValue())
				.doubleValue(dto.getDoubleValue())
				.floatValue(dto.getFloatValue())
				
				.fixed32Value(dto.getFixed32Value())
				.sfixed32Value(dto.getSfixed32Value())
				.uint32Value(dto.getUint32Value())
				.sint32Value(dto.getSint32Value())
				.int32Value(dto.getInt32Value())
				
				.fixed64Value(dto.getFixed64Value())
				.sfixed64Value(dto.getSfixed64Value())
				.uint64Value(dto.getUint64Value())
				.sint64Value(dto.getSint64Value())
				.int64Value(dto.getInt64Value())
				
				.objectValue(buildEntity(dto.getObjectValue()))
				
				.build();
	}
}
