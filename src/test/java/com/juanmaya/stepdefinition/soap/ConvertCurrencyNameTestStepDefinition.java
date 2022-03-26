package com.juanmaya.stepdefinition.soap;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.HashMap;

import static com.juanmaya.question.soap.SoapResponse.response;
import static com.juanmaya.task.reqres.DoPost.doPost;
import static com.juanmaya.util.CurrencyISOCode.MONEY_ISO_CODE;
import static com.juanmaya.util.FileUtilities.readFile;
import static com.juanmaya.util.Log4jValues.LOG4J_LINUX_PROPERTIES_FILE_PATH;
import static com.juanmaya.util.Log4jValues.LOG4J_WINDOWS_PROPERTIES_FILE_PATH;
import static com.google.common.base.StandardSystemProperty.USER_DIR;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.containsString;

public class ConvertCurrencyNameTestStepDefinition {
    private String XML_FILE_PATH = "/src/test/resources/files/soap/currencyISOCode.xml";
    private String CURRENCY_ISO_CODE_XML = System.getProperty("user.dir")+ XML_FILE_PATH;
    private String URL_BASE = "http://webservices.oorsprong.org";
    private String RESOURCE = "/websamples.countryinfo/CountryInfoService.wso";
    private final HashMap <String, Object> headers = new HashMap<>();
    private final Actor actor = Actor.named("max");
    private String bodyRequest;
    private static final Logger LOGGER =Logger.getLogger(ConvertCurrencyNameTestStepDefinition.class);


    @Given("dado que el usuario esta en el recurso web indicando el nombre de moneda ISO {string}")
    public void dadoQueElUsuarioEstaEnElRecursoWebIndicandoElNombreDeMonedaISO(String moneyIsoCode) {
        setUpLog4j2();
        actor.whoCan(CallAnApi.at(URL_BASE));
        headers.put("Content-Type","text/xml;charset=UTF-8");
        headers.put("SOAPAction","");
        bodyRequest = defineBodyRequest(moneyIsoCode);
        LOGGER.info(bodyRequest);
    }

    @When("el usuario genera la consulta")
    public void elUsuarioGeneraLaConsulta() {
        actor.attemptsTo(
                doPost()
                        .usingTheResource(RESOURCE)
                        .withHeaders(headers)
                        .andBodyRequest(bodyRequest)
        );
    }

    @Then("visualizara el nombre formal de la moneda como {string}")
    public void visualizaraElNombreFormalDeLaMonedaComo(String formalName) {
        LastResponse.received().answeredBy(actor).prettyPrint();

        actor.should(
                        seeThatResponse("El codigo de respuesta debe ser " + HttpStatus.SC_OK,
                                validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                        ),
                        seeThat("El nombre formal de la moneda debe ser : "+formalName,
                            response(), containsString(formalName))
                    );
    }

    private String defineBodyRequest(String moneyIsoCode)
    {
        return readFile(CURRENCY_ISO_CODE_XML)
                .replace(MONEY_ISO_CODE.getValue(), moneyIsoCode);
    }

    protected void setUpLog4j2(){
        String os = System.getProperty("os.name").toLowerCase().substring(0,3);
        switch (os)
        {
            case "win":
                PropertyConfigurator.configure(USER_DIR.value() + LOG4J_WINDOWS_PROPERTIES_FILE_PATH.getValue());
                break;
            case "lin":
                PropertyConfigurator.configure(USER_DIR.value() + LOG4J_LINUX_PROPERTIES_FILE_PATH.getValue());
                break;
        }
    }

}

