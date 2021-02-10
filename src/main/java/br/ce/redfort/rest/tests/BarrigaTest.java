package br.ce.redfort.rest.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.ce.redfort.rest.core.BaseTest;

public class BarrigaTest extends BaseTest{
	
	private String TOKEN;
	
	@Before
	public void login() {
		
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "tarciso.test@test.com");
		login.put("senha", "123456");
		
		TOKEN = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token")
		;
		
	}
	
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
		
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{\"nome\": \"Nome Qualquer\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			.body("nome", is("Nome Qualquer"))
			.body("visivel", is(true))
		;
	}
	
	@Test
	public void deveAlterarContaComSucesso() {
		
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{\"nome\": \"Nome Alterado\"}")
		.when()
			.put("/contas/406087")
		.then()
			.statusCode(200)
			.body("nome", is("Nome Alterado"))
		;
	}
	
	@Test
	public void naoDeveInserirContaComMesmoNome() {
		
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{\"nome\": \"Nome Alterado\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
		;
	}
	
	@Test
	public void deveInserirMovimentacaoComSucesso() {
		
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(406087);
		mov.setDescricao("Descricao da movimentacao");
		mov.setEnvolvido("Envolvido na mov");
		mov.setTipo("REC");
		mov.setData_transacao("01/01/2000");
		mov.setData_pagamento("01/05/2010");
		mov.setValor(100f);
		mov.setStatus(true);
		
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.log().all()
			.statusCode(201)
		;
	}

}
