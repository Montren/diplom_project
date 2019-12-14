import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.sql.SQLException;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class JourneyOfTheDayWithMySQLTest {

    DataHelper dataHelper = new DataHelper();
    SQLHelper sqlHelper = new SQLHelper();

    @DisplayName("Сценарий №1. Покупка путешествия с действующей карты со своих денежных средств.")
    @Test
    void shouldBeApprovedStatusWhileBuyingTest() throws SQLException {
        sqlHelper.cleanDatabase();
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 12000);
        sqlHelper.paymentStatus(Status.APPROVED, sqlHelper.tablePaymentSql);
        }

    @DisplayName("Сценарий №2. Покупка путешествия с действующей карты в кредит.")
    @Test
    void shouldBeApprovedStatusWhileTakeCreditTest() throws SQLException {
        sqlHelper.cleanDatabase();
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 20000);
        sqlHelper.paymentStatus(Status.APPROVED, sqlHelper.tableCreditSQL);
    }

    @DisplayName("Сценарий №3. Сценарий покупки путешествия с заблокированной карты в кредит.")
    @Test
    void shouldBeDeclineStatusWhileTakeCreditWithLockedCardTest() throws SQLException {
        sqlHelper.cleanDatabase();
        dataHelper.buyingOnCredit();
        dataHelper.inactiveCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 12000);
        sqlHelper.paymentStatus(Status.DECLINED, sqlHelper.tableCreditSQL);
    }

    @DisplayName("Сценарий №4. Сценарий покупки путешествия с заблокированной карты со своих денег.")
    @Test
    void shouldBeDeclineStatusWhileBuyingWithLockedCardTest() throws SQLException {
        sqlHelper.cleanDatabase();
        dataHelper.buyingForYourMoney();
        dataHelper.inactiveCardData();
        dataHelper.pushСontinueButton();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 12000);
        sqlHelper.paymentStatus(Status.DECLINED, sqlHelper.tablePaymentSql);
    }

    @DisplayName("Сценарий №5. Форма заявки без номера карты.")
    @Test
    void shouldBeErrorWhileBuyingWithoutCardNumberTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cardNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    @DisplayName("Сценарий №6. Заявка с некорретным месяцем/годом окончания действия карты.")
    @Test
    void shouldBeErrorWhileBuyingWithIncorrectMonthAndYearTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.month.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.year.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.month.setValue("15");
        dataHelper.year.setValue("48");
        dataHelper.pushСontinueButton();
        $(byText("Неверно указан срок действия карты")).shouldBe(visible);

    }

    @DisplayName("Сценарий №7. Форма заявки без заполненной графы владельца.")
    @Test
    void shouldBeErrorWhileBuyingWithoutCardOwnerTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cardOwner.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @DisplayName("Сценарий №8. Форма заявки без заполненной графы CVC/CVV.")
    @Test
    void shouldBeErrorWhileBuyingWithoutСvcOrCvvTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.cvcOrCvvNumber.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.pushСontinueButton();
        $(byText("Неверный формат")).shouldBe(visible);
    }

    @DisplayName("Сценарий №9. Форма заявки с истекшим сроком карты.")
    @Test
    void shouldBeErrorWhileBuyingWithExpiredCardTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.year.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dataHelper.year.setValue("17");
        dataHelper.pushСontinueButton();
        $(byText("Истёк срок действия карты")).shouldBe(visible);
    }
}
