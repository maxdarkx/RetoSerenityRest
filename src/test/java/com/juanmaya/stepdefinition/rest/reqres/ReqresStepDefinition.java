package com.juanmaya.stepdefinition.rest.reqres;

import com.juanmaya.model.reqres.CreateUser;
import com.juanmaya.model.reqres.Data;
import com.juanmaya.question.reqres.GetUserQuestion;
import com.juanmaya.question.reqres.PostUserCreateQuestion;
import com.juanmaya.util.LoginKey;
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
import static com.juanmaya.task.reqres.DoGet.doGet;
import static com.juanmaya.task.reqres.DoPost.doPost;
import static com.juanmaya.util.FileUtilities.readFile;
import static com.juanmaya.util.Log4jValues.LOG4J_LINUX_PROPERTIES_FILE_PATH;
import static com.juanmaya.util.Log4jValues.LOG4J_WINDOWS_PROPERTIES_FILE_PATH;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class ReqresStepDefinition {
    private final static String URL_BASE = "https://reqres.in";
    private final static String RESOURCE_GET_USER = "/api/users/{id}";
    private final static String RESOURCE_CREATE_USER ="/api/users";
    private final static String EMPTY_USER= "23";
    private final static String CORRECT_USER= "2";
    private final static int CORRECT_USER_ID= 2;
    private final static String EMPTY_RESPONSE = "";
    private final static String JAVA_DESCRIPTION_FILE_PATH = "src/test/resources/files/soap/login.json";
    private final static String NAME_POST_USER = "morpheus";
    private final static String JOB_POST_USER = "leader";
    private final static String ID_POST_USER = "23";


    private final HashMap<String, Object> headers = new HashMap<>();
    private final Actor actor = Actor.named("juan");
    private String idRequest;
    private String bodyRequest;
    private String resource;
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
                        .usingTheResource(RESOURCE_GET_USER)
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
                        .usingTheResource(RESOURCE_GET_USER)
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

    @Given("que se esta creando la base de datos del servicio")
    public void queSeEstaCreandoLaBaseDeDatosDelServicio() {
        setUpLog4j2();
        actor.whoCan(CallAnApi.at(URL_BASE));
        headers.put("Content-Type", ContentType.JSON);
        bodyRequest = defineBodyRequest();
        resource = RESOURCE_CREATE_USER;
        LOGGER.info("Creando el usuario con name = "+NAME_POST_USER+" y job = "+JOB_POST_USER);
    }

    @When("se ingresan los datos de un usuario en el servicio")
    public void seIngresanLosDatosDeUnUsuarioEnElServicio() {
        actor.attemptsTo(
                doPost()
                        .usingTheResource(resource)
                        .withHeaders(headers)
                        .andBodyRequest(bodyRequest)
        );
    }

    @Then("se retornara un codigo de aceptacion y el usuario creado")
    public void seRetornaraUnCodigoDeAceptacionYElUsuarioCreado() {
        CreateUser usuarioNuevo = new PostUserCreateQuestion().answeredBy(actor);
        actor.should(
                seeThatResponse("El codigo de respuesta debe ser " + HttpStatus.SC_CREATED,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_CREATED)
                )
        );
        actor.should(
                seeThat("El nombre del usuario creado debe fue", actor1 -> usuarioNuevo.getName(), equalTo(NAME_POST_USER)),
                seeThat("El trabajo del usuario creado debe fue", actor1 -> usuarioNuevo.getJob(),equalTo(JOB_POST_USER)),
                seeThat("El id no debe ser nulo", actor1 -> usuarioNuevo.getId(), notNullValue())
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

    private String defineBodyRequest()
    {
        return readFile(JAVA_DESCRIPTION_FILE_PATH)
                .replace(LoginKey.NAME.getValue(), NAME_POST_USER)
                .replace(LoginKey.JOB.getValue(), JOB_POST_USER);
    }
}
