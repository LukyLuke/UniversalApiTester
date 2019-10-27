package ch.ranta.universal.tester.api.rest;

import java.util.List;
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

import ch.ranta.universal.tester.api.Creator;
import ch.ranta.universal.tester.api.dto.JsonDto;
import ch.ranta.universal.tester.api.dto.Response;
import ch.ranta.universal.tester.api.mapper.JsonDtoMapper;
import ch.ranta.universal.tester.domain.entities.ApiResponse;
import ch.ranta.universal.tester.dto.JsonEntity;
import ch.ranta.universal.tester.service.GrpcService;
import ch.ranta.universal.tester.service.JmsService;

@RestController
public class GrpcJms implements Creator {
	private final static Logger LOGGER = LoggerFactory.getLogger(GrpcJms.class);
	private JmsService jmsService;
	private GrpcService grpcService;

	@Autowired
	public GrpcJms(GrpcService grpcService, JmsService jmsService) {
		this.grpcService = grpcService;
		this.jmsService = jmsService;
	}

	@Override
	@RequestMapping(
			method= RequestMethod.POST,
			value = "/grpc/jms/{read}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Response> create(@RequestBody List<JsonDto> message, @PathVariable("read") String read, String write) {
		try {
			long start = System.nanoTime();
			Response response = new Response();
			
			List<JsonEntity> json = JsonDtoMapper.buildJsonEntityList(message);
			
			if (grpcService.send(json)) {
				ApiResponse result = jmsService.receive(read);
				
				response.setMessage(result.getMessage());
				response.setMessageId(result.getId());
			}
			response.setRtt(System.nanoTime() - start);
			
			return new ResponseEntity<>(response, Objects.isNull(response.getMessage()) ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
		} catch (InterruptedException e) {
			LOGGER.debug("Exception: ", e);
		}
		return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
	}

}
