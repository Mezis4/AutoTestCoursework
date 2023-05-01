package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.gson.Gson;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

import ru.netology.data.DbUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

@Epic("API-тесты для Payment Gate и Credit Gate")
public class APITests {
    private static DbUtils utils;
    private static Gson gson = new Gson();
    private static RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080/")
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL).build();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    public void tearDown() {
        DbUtils.clearData();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    @Order(1)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Появление в БД данных APPROVED-карты для Payment Gate")
    public void shouldSuccessPayment() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/payment")
                .then().statusCode(200);
        assertEquals("APPROVED", utils.getPaymentStatus());
    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Появление в БД данных APPROVED-карты для Credit Gate")
    public void shouldSuccessCredit() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/credit")
                .then().statusCode(200);
        assertEquals("APPROVED", utils.getCreditStatus());
    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Появление в БД данных DECLINED-карты для Payment Gate")
    public void shouldDeclinePayment() {
        CardInfo cardInfo = new CardInfo(getValidDeclinedDCard(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/payment")
                .then().statusCode(200);
        assertEquals("DECLINED", utils.getPaymentStatus());
    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Появление в БД данных DECLINED-карты для Credit Gate")
    public void shouldDeclineCredit() {
        CardInfo cardInfo = new CardInfo(getValidDeclinedDCard(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/credit")
                .then().statusCode(200);
        assertEquals("DECLINED", utils.getCreditStatus());
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым значением 'Номер карты' для Payment Gate")
    public void shouldNotSuccessPaymentNumberEmpty() {
        CardInfo cardInfo = new CardInfo(emptyField(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/payment")
                .then().statusCode(400);
        assertEquals(null, utils.getPaymentStatus());
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым значением 'Номер карты' для Credit Gate")
    public void shouldNotSuccessCreditNumberEmpty() {
        CardInfo cardInfo = new CardInfo(emptyField(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/credit")
                .then().statusCode(400);
        assertEquals(null, utils.getCreditStatus());
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым значением 'Месяц' для Payment Gate")
    public void shouldNotSuccessPaymentMonthEmpty() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), emptyField(),
                generateYear(1), generateHolder(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/payment")
                .then().statusCode(400);
        assertEquals(null, utils.getPaymentStatus());
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым значением 'Месяц' для Credit Gate")
    public void shouldNotSuccessCreditMonthEmpty() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), emptyField(),
                generateYear(1), generateHolder(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/credit")
                .then().statusCode(400);
        assertEquals(null, utils.getCreditStatus());
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым значением 'Год' для Payment Gate")
    public void shouldNotSuccessPaymentYearEmpty() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                emptyField(), generateHolder(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/payment")
                .then().statusCode(400);
        assertEquals(null, utils.getPaymentStatus());
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым значением 'Год' для Credit Gate")
    public void shouldNotSuccessCreditYearEmpty() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                emptyField(), generateHolder(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/credit")
                .then().statusCode(400);
        assertEquals(null, utils.getCreditStatus());
    }


    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым значением 'Владелец' для Payment Gate")
    public void shouldNotSuccessPaymentHolderEmpty() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), emptyField(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/payment")
                .then().statusCode(400);
        assertEquals(null, utils.getPaymentStatus());
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым значением 'Владелец' для Credit Gate")
    public void shouldNotSuccessCreditHolderEmpty() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), emptyField(), generateCvc());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/credit")
                .then().statusCode(400);
        assertEquals(null, utils.getCreditStatus());
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым значением 'CVC' для Payment Gate")
    public void shouldNotSuccessPaymentCvcEmpty() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolder(), emptyField());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/payment")
                .then().statusCode(400);
        assertEquals(null, utils.getPaymentStatus());
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым значением 'CVC' для Credit Gate")
    public void shouldNotSuccessCreditCvcEmpty() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolder(), emptyField());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post("/credit")
                .then().statusCode(400);
        assertEquals(null, utils.getCreditStatus());
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым телом для Payment Gate")
    public void shouldShowErrorIfPaymentBodyEmpty() {
        given().spec(spec)
                .when().post("/payment")
                .then().statusCode(400);
        assertEquals(null, utils.getPaymentStatus());
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Отсутствие в БД данных из запроса с пустым телом для Credit Gate")
    public void shouldShowErrorIfCreditBodyEmpty() {
        given().spec(spec)
                .when().post("/credit")
                .then().statusCode(400);
        assertEquals(null, utils.getCreditStatus());
    }
}
