package com.comcast.stringinator.controller;

import com.comcast.stringinator.model.StatsResult;
import com.comcast.stringinator.model.StringinatorInput;
import com.comcast.stringinator.model.StringinatorResult;
import com.comcast.stringinator.service.StringinatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
public class StringinatorController {

    private static final Logger logger = LoggerFactory.getLogger(StringinatorController.class);

    @Autowired
    private StringinatorService stringinatorService;

    @GetMapping("/")
	public String index() {
        logger.info("Request received for index page.");
		return "<pre>\n" +
		"Welcome to the Stringinator 3000 for all of your string manipulation needs.\n" +
		"GET / - You're already here! \n" +
		"POST /stringinate - Get all of the info you've ever wanted about a string. Takes JSON of the following form: {\"input\":\"your-string-goes-here\"}\n" +
		"GET /stats - Get statistics about all strings the server has seen, including the longest and most popular strings.\n" +
		"</pre>";
	}

    //TODO: Throw HTTP 4xx error code for this validation.
    @GetMapping(path = "/stringinate", produces = "application/json")
    public StringinatorResult stringinateGet(@RequestParam(name = "input", required = true) @Min(1) String input) {
        logger.info("Request received for /stringinate path, with input: {}", input);
        StringinatorResult result = stringinatorService.stringinate(new StringinatorInput(input));
        return result;
    }


	@PostMapping(path = "/stringinate", consumes = "application/json", produces = "application/json")
    public StringinatorResult stringinate(@Valid @RequestBody StringinatorInput input) {
        logger.info("Request received for /stringinate path, with input: {}", input.getInput());
        StringinatorResult result = stringinatorService.stringinate(input);
        return result;
    }

    @GetMapping(path = "/stats")
    public StatsResult stats() {
        logger.info("Request received for /stats path");
        StatsResult result = stringinatorService.stats();
        return result;
    }

}
