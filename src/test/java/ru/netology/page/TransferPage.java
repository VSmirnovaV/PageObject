package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;


public class TransferPage {
    private SelenideElement heading = $(byText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement button = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private SelenideElement error = $("[data-test-id='error-notification']");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage validTransfer(String amountToTransfer, DataHelper.CardsInfo info) {
        amount.setValue(amountToTransfer);
        from.setValue(info.getCardsNumber());
        button.click();
        return new DashboardPage();
    }

    public DashboardPage buttonCancel() {
        cancelButton.click();
        return new DashboardPage();
    }

    public void invalidTransfer(String amountToTransfer, String expectedText, DataHelper.CardsInfo info) {
        amount.setValue(amountToTransfer);
        from.setValue(info.getCardsNumber());
        button.click();
        error.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void clickButton() {
        button.click();
    }

    public void errorMassage(String expectedText) {
        error.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofSeconds(15));
    }
}
