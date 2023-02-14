package com.comcast.stringinator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class StatsResult {
    private final Map<String, Integer> inputs;
    private final String most_popular;
}
