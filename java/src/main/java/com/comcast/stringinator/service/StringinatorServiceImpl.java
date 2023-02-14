package com.comcast.stringinator.service;

import com.comcast.stringinator.controller.StringinatorController;
import com.comcast.stringinator.exception.InvalidInputException;
import com.comcast.stringinator.model.StatsResult;
import com.comcast.stringinator.model.StringinatorInput;
import com.comcast.stringinator.model.StringinatorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class StringinatorServiceImpl implements StringinatorService {

    private Map<String, Integer> seenStrings = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(StringinatorController.class);

    @Override
    public StringinatorResult stringinate(@NonNull final StringinatorInput input) {
        seenStrings.compute(input.getInput(), (k, v) -> (v == null) ? Integer.valueOf(1) : v + 1);
        Map.Entry<Character, Integer> frequentlyOccurringCharacter = findFrequentlyOccurringCharacter(input.getInput());
        logger.info("Most Frequently Occurring character: {} for a given string: {}", frequentlyOccurringCharacter,
                input.getInput());

        StringinatorResult result = new StringinatorResult(input.getInput(), Integer.valueOf(input.getInput().length()), frequentlyOccurringCharacter.getKey().toString(),
                frequentlyOccurringCharacter.getValue());
        return result;
    }

    @Override
    public StatsResult stats() {
        if (seenStrings.isEmpty()) {
            logger.info("There are no elements yet in the HashMap, returning empty results.");
            return new StatsResult(seenStrings, "");
        }
        final Map.Entry<String, Integer> mostPopularKey = Collections.max(seenStrings.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue));
        logger.info("Most Popular key: {} from stats: {}", mostPopularKey.getKey(), seenStrings.entrySet());
        return new StatsResult(seenStrings, mostPopularKey.getKey());
    }

    /**
     * Creates a mapping for character count in a string and returns the most frequently occurring character.
     * If all the characters have same occurrence it will return the first entry. This is case-sensitive so upper case and
     * lower case character would be counted as a separate entity.
     *
     * @param input NonNull Input string
     * @return Map.Entry<Character, Integer> Mapping of Character to its occurrence count in the string.
     */
    private Map.Entry<Character, Integer> findFrequentlyOccurringCharacter(@NonNull final String input) {
        final HashMap<Character, Integer> characterOccurrence = new HashMap<>();
        Map.Entry<Character, Integer> frequentEntry = null;
        // Creates a mapping of character count in the string.
        for (int i = 0; i < input.length(); i++) {
            Integer charValue = characterOccurrence.getOrDefault(input.charAt(i), 0) + 1;
            characterOccurrence.put(input.charAt(i), charValue);
        }
        //TODO: Translate this to HTTP 4xx error for the end user or check this in controller to begin with.
        if (characterOccurrence.isEmpty()) {
            logger.error("No mapping created from the input string.");
            throw new InvalidInputException("Failed to read string, please check the input.");
        }
        // Loops through the entries and finds the highest occurrence character with its value.
        for (Map.Entry<Character, Integer> entry: characterOccurrence.entrySet()) {
            if (frequentEntry == null || entry.getValue().compareTo(frequentEntry.getValue()) > 0) {
                frequentEntry = entry;
            }
        }
        return frequentEntry;
    }


}
