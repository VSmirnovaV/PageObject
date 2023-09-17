package ru.netology.page;


import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement button = $("[data-test-id='action-verify'");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerification (DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        button.click();
        return new DashboardPage();
    }
}
