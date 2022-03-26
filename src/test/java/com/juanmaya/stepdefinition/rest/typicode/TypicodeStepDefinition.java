package com.juanmaya.stepdefinition.rest.typicode;

import com.juanmaya.model.typicode.PostCreatedUser;
import com.juanmaya.question.typicode.PostUserCreateQuestion;
import com.juanmaya.stepdefinition.rest.reqres.ReqresStepDefinition;
import com.juanmaya.util.LoginKey;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.HashMap;

import static com.google.common.base.StandardSystemProperty.USER_DIR;
import static com.juanmaya.task.reqres.DoPost.doPost;
import static com.juanmaya.util.FileUtilities.readFile;
import static com.juanmaya.util.Log4jValues.LOG4J_LINUX_PROPERTIES_FILE_PATH;
import static com.juanmaya.util.Log4jValues.LOG4J_WINDOWS_PROPERTIES_FILE_PATH;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TypicodeStepDefinition {
    private final static String URL_BASE = "https://jsonplaceholder.typicode.com";
    private final static String RESOURCE_GET_USER = "/posts/{id}";
    private final static String RESOURCE_CREATE_USER = "/posts";
    private final static String BODY_POST_USER = "bar";
    private final static String TITLE_POST_USER = "foo";
    private final static String USERID_POST_USER = "1";
    private final static String JAVA_DESCRIPTION_FILE_PATH = "src/test/resources/files/soap/createUser.json";

    private final HashMap<String, Object> headers = new HashMap<>();
    private final Actor actor = Actor.named("max");
    private String idRequest;
    private String bodyRequest;
    private String resource;
    private static final Logger LOGGER  = Logger.getLogger(TypicodeStepDefinition.class);

    @Given("que se esta ordenando la base de datos del servicio")
    public void queSeEstaOrdenandoLaBaseDeDatosDelServicio() {
        setUpLog4j2();
        actor.whoCan(CallAnApi.at(URL_BASE));
        headers.put("Content-Type", ContentType.JSON);
        bodyRequest = defineBodyRequest();
        resource = RESOURCE_CREATE_USER;
        LOGGER.info(idRequest);
    }



    @When("se ingresan los datos personales de un usuario en el servicio")
    public void seIngresanLosDatosPersonalesDeUnUsuarioEnElServicio() {
        actor.attemptsTo(
                doPost().usingTheResource(resource)
                        .withHeaders(headers)
                        .andBodyRequest(bodyRequest)
        );
    }

    @Then("se retornara un codigo de aceptacion y el usuario creado")
    public void seRetornaraUnCodigoDeAceptacionYElUsuarioCreado() {
        PostCreatedUser tempUser = new PostUserCreateQuestion().answeredBy(actor);
        actor.should(
                seeThatResponse("El codigo de respuesta debe ser " + HttpStatus.SC_CREATED,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_CREATED)
                )
        );
        actor.should(
                seeThat("El titulo del usuario creado debe ser", actor1 -> tempUser.getTitle(), equalTo(TITLE_POST_USER)),
                seeThat("El cuerpo del usuario creado debe ser", actor1 -> tempUser.getBody(),equalTo(BODY_POST_USER)),
                seeThat("El id no debe ser nulo", actor1 -> tempUser.getUserId(), equalTo(USERID_POST_USER))
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

    private String defineBodyRequest() {
        return readFile(JAVA_DESCRIPTION_FILE_PATH)
                .replace(LoginKey.TITLE.getValue(), TITLE_POST_USER)
                .replace(LoginKey.BODY.getValue(), BODY_POST_USER)
                .replace(LoginKey.USERID.getValue(), USERID_POST_USER);
    }
}
