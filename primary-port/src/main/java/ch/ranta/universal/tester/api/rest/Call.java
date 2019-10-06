package ch.ranta.universal.tester.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.ranta.universal.tester.service.JmsService;

@RestController
public class Call {
	private final static Logger LOGGER = LoggerFactory.getLogger(Call.class);
	private JmsService service;

	@Autowired
	public Call(JmsService service) {
		this.service = service;
	}
	
	@PostMapping("/jms/jms/{send}/{read}")
	public String create(@RequestBody String message, @PathVariable("send") String send, @PathVariable("send") String read) {
		try {
			return service.sendAndWait(send, read, message);
		} catch (InterruptedException e) {
			LOGGER.debug("Exception: ", e);
		}
		return "ERROR";
	}
	
}
