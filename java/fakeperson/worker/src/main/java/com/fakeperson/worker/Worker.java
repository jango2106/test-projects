package com.fakeperson.worker;

import com.fakeperson.worker.service.FakePersonService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Worker {

    private FakePersonService service;

    public Worker(FakePersonService service) {
        this.service = service;
    }

    @RabbitListener(queues = "fakePersonQueue")
    public void handleMessage(String payload) throws InterruptedException {
        Thread.sleep(4000);
        System.out.println(payload);
        this.service.addFakePerson(payload);
    }
}
