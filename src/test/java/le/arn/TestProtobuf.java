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

		// 1 : Create a Greeting object using the Protobuf builder.
		Builder greetingBuilder = GreetingProtos.Greeting.newBuilder();
		greetingBuilder.setGreeting(HELLO_WORLD);
		Greeting greeting = greetingBuilder.build();

		try {

			// 2 : Write the message into a file. Serialize the object.
			logger.debug("Write message to a file.");
			FileOutputStream output = new FileOutputStream(SER_FILE);
			greeting.writeTo(output);
			output.close();

			// 3 : Deserialize the object from the file.
			Greeting greetingFromFile = Greeting.parseFrom(new FileInputStream(
					SER_FILE));
			logger.debug("We read this from the file {}", greetingFromFile);

			// 4 : All is well.
			assertEquals(HELLO_WORLD, greetingFromFile.getGreeting());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
