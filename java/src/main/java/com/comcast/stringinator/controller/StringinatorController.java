package com.comcast.stringinator.controller;

import com.comcast.stringinator.model.StatsResult;
import com.comcast.stringinator.model.StringinatorInput;
import com.comcast.stringinator.model.StringinatorResult;
import com.comcast.stringinator.service.StringinatorService;
import com.comcast.stringinator.util.StringSanitizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
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

    @GetMapping(path = "/stringinate", produces = "application/json")
    public StringinatorResult stringinateGet(@RequestParam(name = "input", required = true) String input) {
        logger.info("Request received for /stringinate path, with input: {}", input);
        final String sanitizedString = StringSanitizer.sanitize(input);
        logger.info("Sanitized string for /stringinate GET API call: {}", sanitizedString);
        if (sanitizedString.isBlank()) {
            logger.info("Invalid input: {} recevied for request.", input);
            throw new ResponseStatusException(BAD_REQUEST, "Unable to find resource");
        }
        StringinatorResult result = stringinatorService.stringinate(new StringinatorInput(sanitizedString));
        return result;
    }


	@PostMapping(path = "/stringinate", consumes = "application/json", produces = "application/json")
    public StringinatorResult stringinate(@Valid @RequestBody StringinatorInput input) {
        logger.info("Request received for /stringinate path, with input: {}", input.getInput());
        final String sanitizedString = StringSanitizer.sanitize(input.getInput());
        logger.info("Sanitized string for /stringinate POST API call: {}", sanitizedString);
        input.setInput(sanitizedString);
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
