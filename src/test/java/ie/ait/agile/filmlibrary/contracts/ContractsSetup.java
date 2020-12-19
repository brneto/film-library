package ie.ait.agile.filmlibrary.contracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ie.ait.agile.filmlibrary.exception.GlobalExceptionHandler;
import io.restassured.config.EncoderConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

abstract class ContractsSetup {

    void standaloneSetup(Object... controllers) {
        var mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(
                new ObjectMapper()
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .registerModule(new JavaTimeModule()));

        // https://github.com/spring-cloud/spring-cloud-contract/issues/1428
        EncoderConfig encoderConfig =
                new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssuredMockMvc.config =
                new RestAssuredMockMvcConfig().encoderConfig(encoderConfig);

        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders
                        .standaloneSetup(controllers)
                        .setMessageConverters(mappingJackson2HttpMessageConverter)
                        .setControllerAdvice(new GlobalExceptionHandler()));
    }

    void webAppContextSetup(WebApplicationContext webApplicationContext) {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }
}
