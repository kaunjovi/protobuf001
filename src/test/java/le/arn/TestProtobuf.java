package le.arn;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import le.arn.GreetingProtos.Greeting;
import le.arn.GreetingProtos.Greeting.Builder;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestProtobuf {

	final static Logger logger = LoggerFactory.getLogger(TestProtobuf.class);
	public static final String HELLO_WORLD = "Hello world.";
	public static final String SER_FILE = "src/test/resources/Greeting.ser";

	@Test
	public void test() {
		logger.debug("Write message to a file.");

		Builder greetingBuilder = GreetingProtos.Greeting.newBuilder();
		greetingBuilder.setGreeting(HELLO_WORLD);

		Greeting greeting = greetingBuilder.build();

		try {
			// write
			FileOutputStream output = new FileOutputStream(SER_FILE);
			greeting.writeTo(output);
			output.close();

			// read
			Greeting greetingFromFile = Greeting.parseFrom(new FileInputStream(
					SER_FILE));
			logger.debug("We read this from the file {}", greetingFromFile);

			assertEquals(HELLO_WORLD, greetingFromFile.getGreeting());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
