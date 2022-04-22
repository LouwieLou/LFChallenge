package io.lightfeather.springtemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

@SpringBootApplication
@RestController
public class Application {

  @RequestMapping("/")
  public String home() {
    return "Hello there!";
  }

  @RequestMapping("/api/supervisors")
  public List<String> get() {

    // Receive the array of JSON objects.
    final String url = "https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers";
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(url, String.class);

    JSONArray jsonArray = new JSONArray(result);
    JSONArray filteredArr = new JSONArray();
    List<JSONObject> holder = new ArrayList<JSONObject>();
    List<String> supervisorList = new ArrayList<String>();

    // Loop through the array of JSON objects and only push into our filtered array
    // the ones whose jurisdictions are letters.
    for (int i = 0; i < jsonArray.length(); i++) {
      String jurString = jsonArray.getJSONObject(i).getString("jurisdiction");
      if (Character.isAlphabetic(jurString.charAt(0))) {
        filteredArr.put(jsonArray.getJSONObject(i));
      }
    }

    // Switch to a List of objects to sort using functions.
    for (int i = 0; i < filteredArr.length(); i++) {
      holder.add(filteredArr.getJSONObject(i));
    }

    // Use the sort function and a personal comparator to check across names and
    // jurisdictions.
    Collections.sort(holder, new Comparator<JSONObject>() {
      private static final String L_NAME = "lastName";
      private static final String F_NAME = "firstName";

      @Override
      public int compare(JSONObject a, JSONObject b) {
        String valA = (String) a.get(L_NAME);
        String valB = (String) b.get(L_NAME);
        if (a.getString("jurisdiction").charAt(0) - b.getString("jurisdiction").charAt(0) != 0) {
          return a.getString("jurisdiction").charAt(0) - b.getString("jurisdiction").charAt(0);
        } else if (a.get(L_NAME) != b.get(L_NAME)) {
          return valA.compareTo(valB);
        } else {
          valA = (String) a.get(F_NAME);
          valB = (String) b.get(F_NAME);
          return valA.compareTo(valB);
        }
      }
    });

    // Reuse the old array and fill it with our filtered and sorted objects.
    jsonArray.clear();
    for (int i = 0; i < holder.size(); i++) {
      jsonArray.put(holder.get(i));
    }

    // Loop through the newly filled array and fill up the supervisor list.
    for (int i = 0; i < jsonArray.length(); i++) {
      String formattedString = jsonArray.getJSONObject(i).getString("jurisdiction") + " - "
          + jsonArray.getJSONObject(i).getString("lastName")
          + ", " + jsonArray.getJSONObject(i).getString("firstName");
      supervisorList.add(formattedString);
    }

    return supervisorList;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping("api/submit")
  public ResponseEntity<Object> post(@RequestBody String jsonInput) {

    JSONObject user = new JSONObject(jsonInput);

    // If the user is missing the necessary fields, throw an exception. Else, we
    // print their info to the console.
    try {
      if (user.getString("firstName") != null && user.getString("lastName") != null
          && user.getString("supervisor") != null) {

        System.out.println(user.toString());
        return new ResponseEntity<>(user.toMap(), HttpStatus.OK);
      }
    } catch (Exception e) {
      System.out.println("Please include first and last names as well as a supervisor.");
      return new ResponseEntity<>("Please include first and last names as well as a supervisor.",
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(user.toMap(), HttpStatus.OK);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
