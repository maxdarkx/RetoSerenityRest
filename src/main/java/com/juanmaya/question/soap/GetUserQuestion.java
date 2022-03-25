package com.juanmaya.question.soap;

import com.juanmaya.model.Data;
import com.juanmaya.model.DataUser;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class GetUserQuestion implements Question<Data> {

    @Override
    public Data answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(DataUser.class).getData();
    }
}
