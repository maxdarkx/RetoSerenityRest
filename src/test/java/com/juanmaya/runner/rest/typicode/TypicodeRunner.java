package com.juanmaya.runner.rest.typicode;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/features/rest/typicode/typicode.feature"},
        glue = {"com.juanmaya.stepdefinition.rest.typicode"},
        tags = {""}

)
public class TypicodeRunner {
}
