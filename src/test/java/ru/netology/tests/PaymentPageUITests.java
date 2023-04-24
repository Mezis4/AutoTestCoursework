package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper.CardInfo;
import ru.netology.data.DbUtils;
import ru.netology.pages.InitialPage;
import ru.netology.pages.FormPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

@Epic("UI-тесты Payment Gate")
public class PaymentPageUITests {
    private static InitialPage initialPage;


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
        initialPage = new InitialPage();
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
    @Description("Успешная оплата по APPROVED-карте")
    public void shouldSuccessPayment() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.successNotification();
    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Отклонение оплаты по DECLINED-карте")
    public void shouldDeclinedPayment() {
        CardInfo cardInfo = new CardInfo(getValidDeclinedDCard(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.declineNotification();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Успешная оплата по APPROVED-карте с заполнением поля 'Владелец' значениями в нижнем регистре")
    public void shouldSuccessPaymentWithLowerCaseHolder() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolderLowerCase(), generateCvc());
        var holder = cardInfo.getHolder();
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                holder, cardInfo.getCvc());
        paymentPage.successNotification();
        paymentPage.matchInsertValueHolder(holder);
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Отклонение оплаты по несуществующей карте")
    public void shouldNotSuccessPaymentWithInvalidCardNumber() {
        CardInfo cardInfo = new CardInfo(generateCardNumber(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.declineNotification();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения о вводе невалидного номера карты из 15 цифр под полем ввода")
    public void shouldShowErrorIf15NumbersCard() {
        CardInfo cardInfo = new CardInfo(generateCard15Numbers(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalidCardNumber();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения о пустом поле 'Номер карты' под полем ввода")
    public void shouldShowErrorIfEmptyCardField() {
        CardInfo cardInfo = new CardInfo(emptyField(), generateMonth(1),
                generateYear(1), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.emptyCardNumber();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения об истекщем сроке карты при вводе данных карты с истекщем месяц назад сроком")
    public void shouldShowErrorIfExpiredOneMonthCard() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateLastMonth(),
                generateYear(0), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalidMonthField();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.MINOR)
    @Description("Появление предупреждения о неверном значении в поле 'Месяц' при вводе несуществующего месяца '00'")
    public void shouldShowErrorIfNonexistentMonth00() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), nonexistentMonth(0),
                generateYear(1), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalidMonthField();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.MINOR)
    @Description("Появление предупреждения о неверном значении в поле 'Месяц' при вводе несуществующего месяца '13'")
    public void shouldShowErrorIfNonexistentMonth13() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), nonexistentMonth(1),
                generateYear(0), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalidMonthField();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения о пустом поле 'Месяц' под полем ввода")
    public void shouldShowErrorIfEmptyMonthField() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), emptyField(),
                generateYear(1), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.emptyMonthField();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения об истекщем сроке карты при вводе данных карты с истекщем год назад сроком")
    public void shouldShowErrorIfExpiredOneYearCard() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateLastYear(), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalidYearField();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения о пустом поле 'Год' под полем ввода")
    public void shouldShowErrorIfEmptyYearField() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                emptyField(), generateHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.emptyYearField();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения о неверном формате значений в поле 'Владелец' при вводе кириллических символов")
    public void shouldShowErrorIfCyrillicHolder() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateCyrillicHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalidHolderField();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения о неверном формате значений в поле 'Владелец' при вводе одного слова")
    public void shouldShowErrorIf1WordHolder() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateOneWordHolder(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalid1WordHolder();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения о неверном формате значений в поле 'Владелец' при вводе дефиса между словами")
    public void shouldShowErrorIfHolderWithDashes() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolderWithDashes(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalidHolderField();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.MINOR)
    @Description("Появление предупреждения о неверном формате значений в поле 'Владелец' при вводе чисел")
    public void shouldShowErrorIfHolderWithNumbers() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolderWithNumbers(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalidHolderField();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.MINOR)
    @Description("Появление предупреждения о неверном формате значений в поле 'Владелец' при вводе символов")
    public void shouldShowErrorIfHolderWithSymbols() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolderSymbols(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalidHolderField();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.MINOR)
    @Description("Появление предупреждения о неверном формате значений в поле 'Владелец' при вводе только пробелов")
    public void shouldShowErrorIfHolderWithSpacerBar() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), spaceBarField(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.emptyHolderField();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения о пустом полем 'Владелец' под полем ввода")
    public void shouldShowErrorIfHolderEmpty() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), emptyField(), generateCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.emptyHolderField();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения о неверном формате значений в поле 'CVC' при вводе двух цифр")
    public void shouldShowErrorIfCVC2Numbers() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolder(), generate2NumbersCvc());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.invalidCvcField();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Появление предупреждения о пустом полем 'CVC' под полем ввода")
    public void shouldShowErrorIfCVCEmpty() {
        CardInfo cardInfo = new CardInfo(getValidApprovedNumberCard(), generateMonth(1),
                generateYear(1), generateHolder(), emptyField());
        initialPage.payment();
        var paymentPage = new FormPage();
        paymentPage.fillOutForm(cardInfo.getNumber(), cardInfo.getMonth(), cardInfo.getYear(),
                cardInfo.getHolder(), cardInfo.getCvc());
        paymentPage.emptyCvcField();
    }
}
