package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class InitialPage {
    private SelenideElement heading = $("div h2");
    private SelenideElement paymentButton = $$("button .button__text").findBy(exactText("Купить"));
    private SelenideElement creditButton = $$("button .button__text").findBy(exactText("Купить в кредит"));

    public InitialPage() {
        heading.shouldBe(visible);
    }

    public FormPage payment() {
        paymentButton.click();
        return new FormPage();
    }

    public FormPage credit() {
        creditButton.click();
        return new FormPage();
    }
}
