package com.juanmaya.question.reqres;

import com.juanmaya.model.reqres.CreateUser;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class PostUserCreateQuestion implements Question <CreateUser> {
    @Override
    public CreateUser answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(CreateUser.class);
    }
}
