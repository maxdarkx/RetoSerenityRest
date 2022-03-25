package com.juanmaya.runner.rest;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/features/rest/Reqres.feature"},
        glue = {"com.juanmaya.stepdefinition.Rest"},
        tags = {""}
)
public class ReqresRunner{

}
