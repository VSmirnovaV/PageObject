package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;

public class PageObjectTest {

    DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        open ("http://localhost:9999");
        var LoginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        dashboardPage = verificationPage.validVerification(verificationCode);
    }

    @Test
    public void shouldMakeValidTransferFromFirstCardToSecond() { // следует сделать успешный перевод с первой карты на вторую
      var firstCards = getNumberFirstCards();
      var secondCards = getNumberSecondCards();
      var firstCardBalance = dashboardPage.getCardBalance(0);
      var secondCardBalance = dashboardPage.getCardBalance(1);
      var amount = generateValidAddCards(firstCardBalance);
      var expectedFirstCardBalance = firstCardBalance - amount;
      var expectedSecondCardBalance = secondCardBalance + amount;
      var transferPage = dashboardPage.selectCard(secondCards);
      dashboardPage = transferPage.validTransfer(String.valueOf(amount),firstCards);
      var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
      var actualBalanceSecondCard = dashboardPage.getCardBalance(1);
      Assertions.assertEquals(expectedFirstCardBalance, actualBalanceFirstCard);
      Assertions.assertEquals(expectedSecondCardBalance, actualBalanceSecondCard);
    }

    @Test
    public void shouldMakeValidTransferFromSecondCardToFirst() { // следует сделать успешный перевод со второй карты на первую
        var firstCards = getNumberFirstCards();
        var secondCards = getNumberSecondCards();
        var firstCardBalance = dashboardPage.getCardBalance(0);
        var secondCardBalance = dashboardPage.getCardBalance(1);
        var amount = generateValidAddCards(secondCardBalance);
        var expectedFirstCardBalance = firstCardBalance + amount;
        var expectedSecondCardBalance = secondCardBalance - amount;
        var transferPage = dashboardPage.selectCard(firstCards);
        dashboardPage = transferPage.validTransfer(String.valueOf(amount),secondCards);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(expectedFirstCardBalance, actualBalanceFirstCard);
        Assertions.assertEquals(expectedSecondCardBalance, actualBalanceSecondCard);
    }

    @Test
    public void shouldMakeInValidTransferFromFirstCardToSecond() { // следует сделать неуспешный перевод со второй карты на первую
        var firstCards = getNumberFirstCards();
        var secondCards = getNumberSecondCards();
        var firstCardBalance = dashboardPage.getCardBalance(0);
        var secondCardBalance = dashboardPage.getCardBalance(1);
        var amount = generateInvalidAddCards(firstCardBalance);
        var transferPage = dashboardPage.selectCard(secondCards);
        transferPage.invalidTransfer(String.valueOf(amount),"Ошибка\n" +
                "Ошибка! Произошла ошибка", firstCards);
    }

    @Test
    public void shouldMakeInValidTransferFromSecondCardToFirst() { // следует сделать неуспешный перевод со первой карты на вторую
        var firstCards = getNumberFirstCards();
        var secondCards = getNumberSecondCards();
        var firstCardBalance = dashboardPage.getCardBalance(0);
        var secondCardBalance = dashboardPage.getCardBalance(1);
        var amount = generateInvalidAddCards(secondCardBalance);
        var transferPage = dashboardPage.selectCard(firstCards);
        transferPage.invalidTransfer(String.valueOf(amount),"Ошибка\n" +
                "Ошибка! Произошла ошибка", secondCards);
    }

    @Test
    public void shouldSubmitTheFormWithoutData() { // следует отправить форму без заполненных данных
        var firstCards = getNumberFirstCards();
        var secondCards = getNumberSecondCards();
        var firstCardBalance = dashboardPage.getCardBalance(1);
        var secondCardBalance = dashboardPage.getCardBalance(0);
        var transferPage = dashboardPage.selectCard(secondCards);
        transferPage.clickButton();
        transferPage.errorMassage("Ошибка\n" +
                "Ошибка! Произошла ошибка");
    }
    @Test
    public void shouldClickButtonCancel() { // следует нажать кнопку отменить
        var firstCards = getNumberFirstCards();
        var secondCards = getNumberSecondCards();
        var firstCardBalance = dashboardPage.getCardBalance(1);
        var secondCardBalance = dashboardPage.getCardBalance(0);
        var transferPage = dashboardPage.selectCard(secondCards);
        dashboardPage = transferPage.buttonCancel();
    }
}
