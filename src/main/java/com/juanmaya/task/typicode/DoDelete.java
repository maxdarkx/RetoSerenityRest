package com.juanmaya.task.typicode;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import java.util.HashMap;

public class DoDelete implements Task {
    private String resource;
    private String idRequest;

    public DoDelete usingTheResource(String resource) {
        this.resource = resource;
        return this;
    }

    public DoDelete andIdToDelete(String idRequest) {
        this.idRequest = idRequest;
        return this;
    }

    public static DoDelete doDelete()
    {
        return new DoDelete();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from(resource)
                        .with(request -> request.pathParam("id",idRequest))
        );
    }

    @Override
    public Performable then(Performable nextPerformable) {
        return Task.super.then(nextPerformable);
    }
}
