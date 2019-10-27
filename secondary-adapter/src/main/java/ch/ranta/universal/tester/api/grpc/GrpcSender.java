package ch.ranta.universal.tester.api.grpc;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ranta.universal.tester.dto.JsonEntity;
import ch.ranta.universal.tester.dto.Protobuf;

/**
 * A GRPC-Sender for using ProtoBuf without any Proto-Files and Service-Definitions.
 * 
 * HTTP/2 Specs: https://github.com/grpc/grpc/blob/master/doc/PROTOCOL-HTTP2.md
 * 
 */
public class GrpcSender implements Sender {
	private static final Logger LOGGER = LoggerFactory.getLogger(GrpcSender.class);
	private static final String VERSION = "0.1.0";
	
	private static final int HTTP_STATUS_OK = 200;
	private static final String GRPC_STATUS_OK = "0";

	@Override
	public boolean send(List<JsonEntity> message) {
		LOGGER.error("Sending message='{}'", message);
		HttpClient client = HttpClient
				.newBuilder()
				.version(Version.HTTP_2)
				.build();
		try {
			byte[] proto = new Protobuf().convert(message);
			HttpResponse<String> response = client.send(
					HttpRequest.newBuilder()
						.uri(URI.create("http://127.0.0.1/grpc"))
						.header("x-user-agent", "grpc-java-universaltesting/" + VERSION)
						.header("content-type", "application/grpc+proto")
						.header("grpc-timeout", "1S")
						.header("grpc-encoding", "gzip")
						.POST(BodyPublishers.ofByteArray(proto))
						.build(),
					BodyHandlers.ofString());
			
			String received = response.headers().firstValue("grpc-status").orElse("");
			
			return response.statusCode() == HTTP_STATUS_OK && received.equals(GRPC_STATUS_OK);
		} catch (IOException | InterruptedException e) {
			LOGGER.error("Exception: {}", e);
		}
		return false;
	}

}
