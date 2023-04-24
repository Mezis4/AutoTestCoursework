package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormPage {
    private SelenideElement form = $("form");
    private SelenideElement numberField = form.$$(".input__inner").findBy(text("Номер карты"));
    private SelenideElement numberInput = numberField.$(".input__control");
    private SelenideElement monthField = form.$$(".input__inner").findBy(text("Месяц"));
    private SelenideElement monthInput = monthField.$(".input__control");
    private SelenideElement yearField = form.$$(".input__inner").findBy(text("Год"));
    private SelenideElement yearInput = yearField.$(".input__control");
    private SelenideElement holderField = form.$$(".input__inner").findBy(text("Владелец"));
    private SelenideElement holderInput = holderField.$(".input__control");
    private SelenideElement cvcField = form.$$(".input__inner").findBy(text("CVC/CVV"));
    private SelenideElement cvcInput = cvcField.$(".input__control");
    private SelenideElement continueButton = $$("button span").findBy(text("Продолжить"));

    public FormPage() {
        form.shouldBe(visible);
    }

    public void fillOutForm(String number, String month, String year, String holder, String cvc) {
        numberField.click();
        numberInput.setValue(number);
        monthField.click();
        monthInput.setValue(month);
        yearField.click();
        yearInput.setValue(year);
        holderField.click();
        holderInput.setValue(holder);
        cvcField.click();
        cvcInput.setValue(cvc);
        continueButton.click();
    }

    public void successNotification() {
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(text("Успешно"));
    }

    public void declineNotification() {
        $(".notification_status_error").shouldBe(visible, Duration.ofSeconds(15));
    }

    public void invalidCardNumber() {
        numberField.$(".input__sub").shouldHave(exactText("Неверный формат"), visible);
    }

    public void emptyCardNumber() {
        numberField.$(".input__sub").should(exactText("Поле обязательно для заполнения"), visible);
    }

    public void invalidMonthField() {
        monthField.$(".input__sub").shouldHave(text("Неверно указан срок действия карты"), visible);
    }

    public void emptyMonthField() {
        monthField.$(".input__sub").should(exactText("Поле обязательно для заполнения"), visible);
    }

    public void invalidYearField() {
        yearField.$(".input__sub").shouldHave(text("Истёк срок действия"), visible);
    }

    public void emptyYearField() {
        yearField.$(".input__sub").should(exactText("Поле обязательно для заполнения"), visible);
    }

    public void invalidHolderField() {
        holderField.$(".input__sub").should(text("может содержать только латинские буквы"), visible);
    }

    public void invalid1WordHolder() {
        holderField.$(".input__sub").should(text("Неверный формат"), visible);
    }

    public void emptyHolderField() {
        holderField.$(".input__sub").should(exactText("Поле обязательно для заполнения"), visible);
    }

    public void matchInsertValueHolder(String holder) {
        assertEquals(holder, holderInput.getValue());
    }

    public void invalidCvcField() {
        cvcField.$(".input__sub").shouldHave(exactText("Неверный формат"), visible);
    }

    public void emptyCvcField() {
        cvcField.$(".input__sub").should(exactText("Поле обязательно для заполнения"), visible);
    }
}
