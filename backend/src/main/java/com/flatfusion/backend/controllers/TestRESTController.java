package com.flatfusion.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("/test")
public class TestRESTController {

    String body = """
                 .-.            .-.
                /   \\          /   \\
               |   _ \\        / _   |
               ;  | \\ \\      / / |  ;
                \\  \\ \\ \\_.._/ / /  /
                 '. '.;'    ';,' .'
                   './ _    _ \\.'
                   .'  a __ a  '.
              '--./ _,   \\/   ,_ \\.--'
             ----|   \\   /\\   /   |----
              .--'\\   '-'  '-'    /'--.
                  _>.__  -- _.-  `;
                .' _     __/     _/
               /    '.,:".-\\    /:,
               |      \\.'   `""`'.\\\\
                '-,.__/  _   .-.  ;|_
                /` `|| _/ `\\/_  \\_|| `\\
               |    ||/ \\-./` \\ / ||   |
              \\ || I <3 Easter Eggs! | /
            jgs .'  \\ =  _= _ = _= /`\\
               /     `-;----=--;--'   \\
               \\    _.-'        '.    /
                `""`              `""`
            """;

    @GetMapping
    ResponseEntity<String> getString(){
        return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
    }

}
