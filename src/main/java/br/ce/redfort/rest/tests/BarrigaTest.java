package br.ce.redfort.rest.tests;

import static io.restassured.RestAssured.given;

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

}
