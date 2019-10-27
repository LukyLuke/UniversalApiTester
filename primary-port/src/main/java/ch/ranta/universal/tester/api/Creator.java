package ch.ranta.universal.tester.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ch.ranta.universal.tester.api.dto.JsonDto;
import ch.ranta.universal.tester.api.dto.Response;

public interface Creator {

	default ResponseEntity<Response> create(String message, String send, String read) {
		return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR);
	};

	default ResponseEntity<Response> create(List<JsonDto> message, String read, String write) {
		return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR);
	};

}