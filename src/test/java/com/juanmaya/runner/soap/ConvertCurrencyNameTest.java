package com.juanmaya.runner.soap;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberSerenityRunner;
import org.junit.runner.RunWith;

@RunWith(CucumberSerenityRunner.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/features/soap/CurrencyName.feature"},
        glue = {"com.juanmaya.stepdefinition.soap"}
)
public class ConvertCurrencyNameTest {
}
