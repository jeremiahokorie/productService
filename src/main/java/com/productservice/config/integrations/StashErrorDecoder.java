package com.productservice.config.integrations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import static feign.FeignException.errorStatus;

/**
 * @author Stephen Obi <stephen@frontedge.io>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 03/02/2022 01:29
 */

@Slf4j
public class StashErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        //START DECODING ORIGINAL ERROR MESSAGE
        String errorMessage = null;
        Reader reader = null;
        //capturing error message from response body.
        try {
            reader = response.body().asReader(StandardCharsets.UTF_8);
            String result = IOUtils.toString(reader);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            FeignExceptionMessage exceptionMessage = mapper.readValue(result,
                    FeignExceptionMessage.class);
            errorMessage = exceptionMessage.getMessage();
        } catch (IOException e) {
            log.error("IO Exception on reading exception message feign client" + e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error("IO Exception on reading exception message feign client" + e);
            }
        }


        if (response.status() >= 400 && response.status() <= 499) {
            log.error("StashClientException [{}] - {}", response.status(), new Gson().toJson(response.body()));

            return new StashClientException(response.status(), response.body().toString());
        }
        if (response.status() >= 500 && response.status() <= 599) {
            return new StashServerException(response.status(), errorMessage);
        }
        return errorStatus(methodKey, response);
    }
}
