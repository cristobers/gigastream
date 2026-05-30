package gigastream.auth;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthController {
    @GetMapping("/foo")
    public String foo() {
        return "bar";
    }
    
    // Should the user be allowed to stream?
    @PostMapping("/auth")
    public String authenticateUser(@RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType, @RequestParam Map<String, String> params, @RequestHeader Map<String, String> headers) throws ResponseStatusException {
        // Check the url parameters
        if (!params.containsKey("name")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "No streamkey found within request."
            );
        }

        if (!params.containsKey("type")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "No endpoint found."
            );
        }

        // This is the correct way around, adjust your CURL command accordingly.
        RTMPRequestData data = new RTMPRequestData(params.get("type"), params.get("name"));
        if (Key.IsValid(data)) {
            return "";
        }

        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "Bad key."
        );
    }
}
