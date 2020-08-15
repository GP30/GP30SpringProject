package softuni.springproject.web.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import softuni.springproject.base.TestBase;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTestBase extends TestBase {
    @LocalServerPort
    protected int port;

    protected String getFullUrl(String route) {
        return "http://localhost:" + port + route;
    }

    protected TestRestTemplate getRestTemplate() {
        return new TestRestTemplate();
    }

    protected TestRestTemplate getRestTemplate(String username, String password) {
        if (username != null && password != null) {
            return new TestRestTemplate(username, password);
        }

        return getRestTemplate();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}
