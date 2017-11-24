package com.fpoon.jaybb.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class MessageDTO {
    private String title;
    @NotBlank
    private String content;
}
