package br.ce.redfort.rest.tests;

import static io.restassured.RestAssured.*;

import org.junit.Test;

public class BarrigaTest {
	
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
