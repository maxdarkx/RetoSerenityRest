package com.juanmaya.task.reqres;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import java.util.HashMap;

public class DoGet implements Task {
    private String resource;
    private HashMap<String, Object> headers = new HashMap<>();
    private String idRequest;

    public DoGet usingTheResource(String resource) {
        this.resource = resource;
        return this;
    }

    public DoGet withHeaders(HashMap<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public DoGet andIdRequest(String idRequest) {
        this.idRequest = idRequest;
        return this;
    }

    public static DoGet doGet()
    {
        return new DoGet();
    }


    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(resource)
                        .with( request -> request.pathParam("id",idRequest))
        );
    }
}
