package ch.ranta.universal.tester.api.rest;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.ranta.universal.tester.api.dto.Response;
import ch.ranta.universal.tester.domain.entities.ApiResponse;
import ch.ranta.universal.tester.service.JmsService;

@RestController
public class JmsJms {
	private final static Logger LOGGER = LoggerFactory.getLogger(JmsJms.class);
	private JmsService service;

	@Autowired
	public JmsJms(JmsService service) {
		this.service = service;
	}
	
	@RequestMapping(
			method= RequestMethod.POST,
			value = "/jms/jms/{send}/{read}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Response> create(@RequestBody String message, @PathVariable("send") String send, @PathVariable("read") String read) {
		try {
			long start = System.nanoTime();
			ApiResponse result = service.sendAndWait(send, read, message);

			Response response = new Response();
			response.setMessage(result.getMessage());
			response.setMessageId(result.getId());
			response.setRtt(System.nanoTime() - start);
			
			return new ResponseEntity<>(response, Objects.isNull(result.getMessage()) ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
		} catch (InterruptedException e) {
			LOGGER.debug("Exception: ", e);
		}
		return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
	}
	
}
