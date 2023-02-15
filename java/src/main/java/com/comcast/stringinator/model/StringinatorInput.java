package com.comcast.stringinator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StringinatorInput {
    @NotBlank(message = "Input String is mandatory and cannot be blank or null.")
    private String input;
}
