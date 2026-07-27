package com.sirajchaudhary.library.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryInput {

    @NotBlank
    private String name;

    private String description;
}