package mincho.petkov;

import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.restassured.RestAssured;
import mincho.petkov.model.dto.Account;
import mincho.petkov.model.dto.TransferRequest;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static mincho.petkov.model.Currency.EUR;
import static mincho.petkov.model.Currency.GBP;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTest {

    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE = new DropwizardAppRule<>(TransferApplication.class);

    @BeforeClass
    public static void config() {
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
    }


    @Test
    public void test_1_GetAccountsSize() {
        when()
                .get("/accounts")
                .then()
                .statusCode(OK.getStatusCode())
                .assertThat()
                .body("size()", is(511));
    }


    @Test
    public void test_2_InvalidUrl() {

        when()
                .get("/non-existing-url")
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void test_3_GetAccount() {

        checkAccount(new Account("EUR000000003", EUR, new BigDecimal(3000)));
        checkAccount(new Account("GBP000000001", GBP, new BigDecimal(10)));
    }

    @Test
    public void test_4_GetMissingAccount() {

        when()
                .get("/accounts/non-existing-account")
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void test_5_InvalidTransferNoAccount() {

        given().
                when()
                .body(new TransferRequest("nonExisting", "GBP000000004", new BigDecimal("2")))
                .contentType("application/json")
                .post("/accounts/transfer")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode());

    }

    @Test
    public void test_6_InvalidTransferInsufficientFunds() {

        given().
                when()
                .body(new TransferRequest("GBP000000001", "GBP000000004", new BigDecimal("200")))
                .contentType("application/json")
                .post("/accounts/transfer")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode());

        checkAccount(new Account("GBP000000001", GBP, new BigDecimal(10)));
        checkAccount(new Account("GBP000000004", GBP, new BigDecimal(40000)));

    }

    @Test
    public void test_7_ValidTransfer() {

        given().
                when()
                .body(new TransferRequest("GBP000000001", "GBP000000004", new BigDecimal("2.5")))
                .contentType("application/json")
                .post("/accounts/transfer")
                .then().statusCode(OK.getStatusCode());

        checkAccount(new Account("GBP000000001", GBP, new BigDecimal(7.5)));
        checkAccount(new Account("GBP000000004", GBP, new BigDecimal(40002.5)));
    }

    private void checkAccount(Account expected) {
        when()
                .get("/accounts/" + expected.getAccountNumber())
                .then()
                .statusCode(OK.getStatusCode())
                .body("accountNumber", is(expected.getAccountNumber()))
                .body("currency", is(expected.getCurrency().name()))
                .body("accountBalance", comparesEqualTo(expected.getAccountBalance()));
    }

}