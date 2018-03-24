package com.fpoon.jaybb.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class BotThreadDTO extends ThreadDTO {
    @NotBlank
    private String url;
}
