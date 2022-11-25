package dao;

import repositories.HelloWorldRepository;

public class HelloWorldDao {

    private final HelloWorldRepository helloWorldRepository;

    public HelloWorldDao(HelloWorldRepository helloWorldRepository) {
        this.helloWorldRepository = helloWorldRepository;
    }
}
