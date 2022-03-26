package com.juanmaya.runner.rest.reqres;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/features/rest/reqres/Reqres.feature"},
        glue = {"com.juanmaya.stepdefinition.rest.reqres"},
        tags = {""}
)
public class ReqresRunner{

}
