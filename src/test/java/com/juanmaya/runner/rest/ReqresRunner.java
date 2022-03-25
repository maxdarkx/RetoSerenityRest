package com.juanmaya.runner.rest;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberSerenityRunner;
import org.junit.runner.RunWith;

@RunWith(CucumberSerenityRunner.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/features/rest/Reqres.feature"},
        glue = {"com.juanmaya.stepdefinition.Rest"},
        tags = {""}
)
public class ReqresRunner{

}
