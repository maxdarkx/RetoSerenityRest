package com.juanmaya.question.typicode;

import com.juanmaya.model.reqres.CreateUser;
import com.juanmaya.model.typicode.PostCreatedUser;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class PostUserCreateQuestion implements Question <PostCreatedUser> {
    @Override
    public PostCreatedUser answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(PostCreatedUser.class);
    }
}
