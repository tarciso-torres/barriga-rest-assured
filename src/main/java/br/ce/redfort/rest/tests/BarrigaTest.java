package br.ce.redfort.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.ce.redfort.rest.core.BaseTest;

public class BarrigaTest extends BaseTest {
	
	@Test
	public void naoDeveAcessarAPISemToken() {
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
		;
	}
	
	@Test
	public void deveIncluirContaComSucesso() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "tarciso.test@test.com");
		login.put("senha", "123456");
		
		String token = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token")
		;
		
		given()
			.header("Authorization", "JWT " + token)
			.body("{\"nome\": \"Nome Qualquer\"}")
		.when()
			.post("/contas")
		.then()
		.log().all()
			.statusCode(201)
			.body("nome", is("Nome Qualquer"))
			.body("visivel", is(true))
		;
	}

}
