package com.juanmaya.question.reqres;

import com.juanmaya.model.reqres.Data;
import com.juanmaya.model.reqres.DataUser;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class GetUserQuestion implements Question<Data> {

    @Override
    public Data answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(DataUser.class).getData();
    }
}
