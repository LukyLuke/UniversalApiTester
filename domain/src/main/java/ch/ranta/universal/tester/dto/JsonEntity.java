package ch.ranta.universal.tester.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class JsonEntity {
	
	private final int key;
	private final String name;
	
	private String stringValue;
	private Boolean booleanValue;
	private Double doubleValue;
	private Float floatValue;
	
	private JsonEntity objectValue;
	
	private Integer int32Value;
	private Integer uint32Value;
	private Integer sint32Value;
	private Integer fixed32Value;
	private Integer sfixed32Value;
	
	private Long int64Value;
	private Long uint64Value;
	private Long sint64Value;
	private Long fixed64Value;
	private Long sfixed64Value;
	
}
