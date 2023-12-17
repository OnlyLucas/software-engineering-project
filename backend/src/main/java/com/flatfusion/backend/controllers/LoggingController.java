package com.flatfusion.backend.controllers;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for demonstrating logging levels using SLF4J and Logback.
 * Accessing the root endpoint ("/") triggers logging messages at various levels.
 * The logs are printed to the configured logging system (e.g., console, file).
 */
@RestController
public class LoggingController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    /**
     * Handles requests to the root endpoint ("/").
     *
     * Triggers logging messages at different levels: TRACE, DEBUG, INFO, WARN, ERROR.
     *
     * @return A greeting message and a prompt to check the logs for output.
     */
    @RequestMapping("/")
    public String index() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        return "Howdy! Check out the Logs to see the output...";
    }
}
