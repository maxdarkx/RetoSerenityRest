package com.juanmaya.runner.soap;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/features/soap/CurrencyName.feature"},
        glue = {"com.juanmaya.stepdefinition.soap"},
        tags = {""}
)
public class ConvertCurrencyNameTest {
}
