package com.comcast.stringinator.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringinatorResult {
    private final String input;
    private final Integer length;
    private final String mostFrequentCharacter;
    private final Integer mostFrequentCharacterOccurrenceCount;
}
