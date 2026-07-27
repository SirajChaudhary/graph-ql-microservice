package com.sirajchaudhary.library.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorInput {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String email;
}