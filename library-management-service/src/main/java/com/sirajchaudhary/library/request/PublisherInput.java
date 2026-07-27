package com.sirajchaudhary.library.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublisherInput {

    @NotBlank
    private String name;

    private String email;

    private String website;
}