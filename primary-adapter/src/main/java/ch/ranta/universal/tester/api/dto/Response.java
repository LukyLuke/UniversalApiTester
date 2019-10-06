package ch.ranta.universal.tester.api.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long rtt;
	private String messageId;
	private Object message;	
}
