package dev.patika.patika;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HelloController {

    List<Student> students = new ArrayList<>();

    // End-point
    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public StringResponse sayHello(@RequestParam(required = false ,defaultValue = "Hasan") String name, @PathParam("year") int year){
        return  new StringResponse("Hello " + name + " from " + year);
    }

    @GetMapping("/hello/{name}/{age}")
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "There is an error during response")
    public String sayMyName(@PathVariable String name, @PathVariable int age){
        return "Hello " + name + " ,age : " + age;
    }

    @GetMapping("/greeting/{id}")
    public ResponseEntity<String> greeting(@RequestHeader("Cookie") String cookie, @PathVariable int id){
        if(id >= 100){
            //return ResponseEntity.badRequest().body("ID cannot be greater then 99");
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ID cannot be greater then 99");
            return new ResponseEntity<>("ID cannot be greater then 99", HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("my-custom-header","Test header");

        return ResponseEntity.ok().headers(headers).body("ID : " + id + " , cookie : " + cookie);
        //return new ResponseEntity<>("ID : " + id + " , cookie : " + cookie, headers, HttpStatus.CREATED);
    }

    @GetMapping("/custom")
    public void customHeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cookie = request.getHeader("Cookie");

        response.setHeader("custom-header","Koray");
        response.setHeader("cookie",cookie);
        response.setStatus(201);
        response.getWriter().println("Hello world...");

    }


    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(){

        students.add(new Student(1,"Koray Guney","Istanbul"));
        students.add(new Student(2,"Aleyna Kütük","Denizli"));
        students.add(new Student(3,"Emre Oğuz","Istanbul"));

        return ResponseEntity.ok().body(students);
    }

    @PostMapping("/students")
    public ResponseEntity<List<Student>> addStudent(@RequestBody Student student){
        students.add(student);
        return ResponseEntity.ok().body(students);
    }


    @GetMapping("*")
    public ResponseEntity<String> fallBackMethod(){
        return new ResponseEntity<>("There is no ennpoint like that", HttpStatus.NOT_FOUND);
    }

    // http://localhost:8080/sum?num1=10&num2=20
    // output --> 10 + 20 = 30
    @GetMapping("/calculate/{operation}")
    public String calculate(@PathVariable String operation , @RequestParam int num1, @RequestParam int num2){

        String response="";

        switch (operation){
            case "sum":
                response = num1 + " + " + num2 + " = " + (num1+num2);
                break;
            case "subtract":
                response = num1 + " - " + num2 + " = " + (num1-num2);
                break;
            case "divide":
                response = num1 + " / " + num2 + " = " + ((double)num1/num2);
                break;
            case "multiply":
                response = num1 + " * " + num2 + " = " + (num1*num2);
                break;
            default:
                response = "Operation is not valid";
        }
        return response;
    }

}
