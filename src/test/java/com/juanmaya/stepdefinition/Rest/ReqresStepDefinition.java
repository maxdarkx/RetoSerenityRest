package com.juanmaya.stepdefinition.Rest;

import com.juanmaya.model.Data;
import com.juanmaya.model.DataUser;
import com.juanmaya.question.soap.GetUserQuestion;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;


import java.util.HashMap;

import static com.google.common.base.StandardSystemProperty.USER_DIR;
import static com.juanmaya.question.soap.SoapResponse.response;
import static com.juanmaya.task.DoGet.doGet;
import static com.juanmaya.util.Log4jValues.LOG4J_LINUX_PROPERTIES_FILE_PATH;
import static com.juanmaya.util.Log4jValues.LOG4J_WINDOWS_PROPERTIES_FILE_PATH;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class ReqresStepDefinition {
    private String URL_BASE = "https://reqres.in";
    private String RESOURCE = "/api/users/{id}";
    private String EMPTY_USER= "23";
    private String CORRECT_USER= "2";
    private int CORRECT_USER_ID= 2;
    private String EMPTY_RESPONSE = "";
    private String USER_STRING = "{\n" +
            "    \"data\": {\n" +
            "        \"id\": 2,\n" +
            "        \"email\": \"janet.weaver@reqres.in\",\n" +
            "        \"first_name\": \"Janet\",\n" +
            "        \"last_name\": \"Weaver\",\n" +
            "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
            "    },\n" +
            "    \"support\": {\n" +
            "        \"url\": \"https://reqres.in/#support-heading\",\n" +
            "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
            "    }\n" +
            "}";


    private final HashMap<String, Object> headers = new HashMap<>();
    private final Actor actor = Actor.named("juan");
    private String idRequest;
    private static final Logger LOGGER  = Logger.getLogger(ReqresStepDefinition.class);

    @Given("que se esta probando la base de datos de usuario")
    public void queSeEstaProbandoLaBaseDeDatosDeUsuario() {
        setUpLog4j2();
        actor.whoCan(CallAnApi.at(URL_BASE));
        headers.put("Content-Type", ContentType.JSON);
        idRequest = EMPTY_USER;
        LOGGER.info(idRequest);
    }

    @When("se ingresa un id inexistente en el servicio")
    public void seIngresaUnIdInexistenteEnElServicio() {
        actor.attemptsTo(
                doGet()
                        .usingTheResource(RESOURCE)
                        .andIdRequest(idRequest)
        );
    }

    @Then("se retornara un codigo de error")
    public void seRetornaraUnCodigoDeError() {
        LastResponse.received().answeredBy(actor).prettyPrint();

        actor.should(
                seeThatResponse("El codigo de respuesta debe ser " + HttpStatus.SC_NOT_FOUND,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_NOT_FOUND)
                ),
                seeThat("Debe retornarse un String vacio : "+EMPTY_RESPONSE,
                        response(), containsString(EMPTY_RESPONSE))
        );
    }



    @Given("que se esta probando la base de datos de usuario existente")
    public void que_se_esta_probando_la_base_de_datos_de_usuario_existente() {
        setUpLog4j2();
        actor.whoCan(CallAnApi.at(URL_BASE));
        headers.put("Content-Type", ContentType.JSON);
        idRequest = CORRECT_USER;
        LOGGER.info(idRequest);
    }

    @When("se ingresa un id presente en el servicio")
    public void se_ingresa_un_id_presente_en_el_servicio() {
        actor.attemptsTo(
                doGet()
                        .usingTheResource(RESOURCE)
                        .andIdRequest(idRequest)
        );
    }

    @Then("se mostraran los datos del usuario")
    public void se_mostraran_los_datos_del_usuario() {
        Data usuario = new GetUserQuestion().answeredBy(actor);

        actor.should(
                seeThatResponse("El codigo de respuesta debe ser " + HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ));
        actor.should(
                seeThat("El Email del usuario ", act -> usuario.getEmail(),equalTo("janet.weaver@reqres.in")),
                seeThat("El nombre del usuario ", act -> usuario.getFirst_name(),equalTo("Janet")),
                seeThat("El apellido del usuario ", act -> usuario.getLast_name(),equalTo("Weaver"))
        );

    }

    private void setUpLog4j2(){
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
