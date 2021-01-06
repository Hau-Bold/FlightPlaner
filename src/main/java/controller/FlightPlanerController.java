package controller;

import org.apache.logging.log4j.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.json.simple.parser.JSONParser;
import model.GPSCoordinate;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class FlightPlanerController {
    
    private static Logger logger = LogManager.getLogger();

    @RequestMapping("/")
    public String index() {
        return "Hello World!";
    }

    @RequestMapping(value = "/location", method = POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String createLocation(HttpEntity<String> httpEntity) throws ParseException {
        String jsonString = httpEntity.getBody();
        String street = "";
        String city = "";
        String postalCode = "";
        String country = "";
        JSONParser  parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonString);
        if(json.containsKey("street")) {
            street = json.get("street").toString();
        }
        if(json.containsKey("city")) {
            city = json.get("city").toString();
        }
        if(json.containsKey("postalCode")) {
            postalCode = json.get("postalCode").toString();
        }
        if(json.containsKey("country")) {
            country = json.get("country").toString();
        }

        GPSCoordinate gpsCoordinate = new GPSCoordinate(51.0, 10.0);
        try {
            gpsCoordinate = GPSCoordinate.getFromOSM(street, city, postalCode, country);
        } catch(IOException ioe){
            logger.fatal(ioe.getMessage());
            ioe.printStackTrace();
        }


        return gpsCoordinate.toString();
    }
}
