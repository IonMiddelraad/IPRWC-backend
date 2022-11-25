package controller;

import dao.HelloWorldDao;
import model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HelloWorldController {

    private final HelloWorldDao helloWorldDao;

    public HelloWorldController(HelloWorldDao helloWorldDao) {
        this.helloWorldDao = helloWorldDao;
    }


    @GetMapping(value = "/sayhello")
    public ApiResponse sayHelloWorld(){

        return new ApiResponse(HttpStatus.OK, "Hello World!");
    }

    @GetMapping("/error")
    public String errorHandler(){
        return "It would seem there is a problem";
    }
}
