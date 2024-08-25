package com.tinqinacademy.authentication.restexport;

import com.tinqinacademy.authentication.api.mappings.MappingConstants;
import com.tinqinacademy.authentication.api.model.validate.ValidateInput;
import com.tinqinacademy.authentication.api.model.validate.ValidateOutput;
import feign.Headers;
import feign.RequestLine;

@Headers({
        "Content-Type: application/json"
})
public interface AuthenticationClient {
    @RequestLine("POST "+MappingConstants.validate)
    ValidateOutput validate(ValidateInput input);
}
