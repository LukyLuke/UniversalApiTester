package ch.ranta.universal.tester.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Call {
	private final static Logger LOGGER = LoggerFactory.getLogger(Call.class);
	private JmsService service;

	@Autowired
	public Call(JmsService service) {
		this.service = service;
	}
	
	@RequestMapping("/jms/jms/{topic}/{message}")
	public String create(@PathVariable("topic") String topic, @PathVariable("message") String message) {
		try {
			return service.sendAndWait(topic, message);
		} catch (InterruptedException e) {
			LOGGER.debug("Exception: ", e);
		}
		return "ERROR";
	}
	
}
