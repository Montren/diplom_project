import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.sql.SQLException;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class JourneyOfTheDayTest {

    DataHelper dataHelper = new DataHelper();

    @Test
    void positiveBuyingTest() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 12000);
//        dataHelper.paymentStatus(Status.APPROVED);
        dataHelper.paymentStatusPostgres(Status.APPROVED);
        }

    @Test
    void positiveBuyingCreditTest() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 20000);
//        dataHelper.creditStatus(Status.APPROVED);
        dataHelper.creditStatusPostgres(Status.APPROVED);
    }

    @Test // Тут баг на проверке выскакивающего окна. Заведи Issue
    void lockedCardBuyingCreditTest() throws SQLException {
        dataHelper.buyingOnCredit();
        dataHelper.inactiveCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 12000);
//        dataHelper.creditStatus(Status.DECLINED);
        dataHelper.paymentStatusPostgres(Status.DECLINED);
    }

    @Test // Тут баг на проверке выскакивающего окна. Заведи Issue
    void lockedCardBuyingTest() throws SQLException {
        dataHelper.buyingForYourMoney();
        dataHelper.inactiveCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 12000);
        dataHelper.creditStatusPostgres(Status.DECLINED);
    }

    @Test
    void tryBuyWithoutCardNumberTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cardNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @Test
    void tryBuyWithIncorrectMonthAndYearTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.month.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.year.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.month.setValue("15");
        dataHelper.year.setValue("48");
        dataHelper.pushСontinueButton();
        $(byText("Неверно указан срок действия карты")).shouldBe(visible);

    }

    @Test
    void tryBuyWithoutCardOwnerTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cardOwner.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void tryBuyWithoutСvcOrCvvTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cvcOrCvvNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $(byText("Неверный формат")).shouldBe(visible);
    }

    @Test
    void tryBuyWithExpiredCardTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.year.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.year.setValue("17");
        dataHelper.pushСontinueButton();
        $(byText("Истёк срок действия карты")).shouldBe(visible);
    }
}
