import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class JourneyOfTheDayTest {

    PageFactory pageFactory = new PageFactory();
    SQLHelper sqlHelper = new SQLHelper();

    @DisplayName("Сценарий №1. Покупка путешествия с действующей карты со своих денежных средств.")
    @Test
    void shouldBeApprovedStatusWhileBuyingTest() throws SQLException {
        sqlHelper.cleanDatabase();
        pageFactory.buyingForYourMoney();
        pageFactory.activeCardData();
        pageFactory.pushСontinueButton();
        pageFactory.findSuccessNotification();
        sqlHelper.checkLastPaymentStatus(Status.APPROVED);
        }

    @DisplayName("Сценарий №2. Покупка путешествия с действующей карты в кредит.")
    @Test
    void shouldBeApprovedStatusWhileTakeCreditTest() throws SQLException {
        sqlHelper.cleanDatabase();
        pageFactory.buyingOnCredit();
        pageFactory.activeCardData();
        pageFactory.pushСontinueButton();
        pageFactory.findSuccessNotification();
        sqlHelper.checkLastCreditPaymentStatus(Status.APPROVED);
    }

    @DisplayName("Сценарий №3. Сценарий покупки путешествия с заблокированной карты в кредит.")
    @Test
    void shouldBeDeclineStatusWhileTakeCreditWithLockedCardTest() throws SQLException {
        sqlHelper.cleanDatabase();
        pageFactory.buyingOnCredit();
        pageFactory.inactiveCardData();
        pageFactory.pushСontinueButton();
        pageFactory.findUnsuccessNotification();
        sqlHelper.checkLastCreditPaymentStatus(Status.DECLINED);
    }

    @DisplayName("Сценарий №4. Сценарий покупки путешествия с заблокированной карты со своих денег.")
    @Test
    void shouldBeDeclineStatusWhileBuyingWithLockedCardTest() throws SQLException {
        sqlHelper.cleanDatabase();
        pageFactory.buyingForYourMoney();
        pageFactory.inactiveCardData();
        pageFactory.pushСontinueButton();
        pageFactory.findUnsuccessNotification();
        sqlHelper.checkLastPaymentStatus(Status.DECLINED);
    }

    @DisplayName("Сценарий №5. Форма заявки без номера карты.")
    @Test
    void shouldBeErrorWhileBuyingWithoutCardNumberTest() {
        pageFactory.buyingForYourMoney();
        pageFactory.enterMonth("08");
        pageFactory.enterYear("22");
        pageFactory.enterCardOwner("Александр");
        pageFactory.enterCvcOrCvvNumber("999");
        pageFactory.pushСontinueButton();
        pageFactory.findIncorrectFormatNotification();
    }

    @DisplayName("Сценарий №6. Заявка с некорретным месяцем/годом окончания действия карты.")
    @Test
    void shouldBeErrorWhileBuyingWithIncorrectMonthAndYearTest() {
        pageFactory.buyingForYourMoney();
        pageFactory.enterCardNumber("4444444444444441");
        pageFactory.enterMonth("15");
        pageFactory.enterYear("48");
        pageFactory.enterCardOwner("Александр");
        pageFactory.enterCvcOrCvvNumber("999");
        pageFactory.pushСontinueButton();
        pageFactory.findCardIncorrectDateFormatNotification();
    }

    @DisplayName("Сценарий №7. Форма заявки без заполненной графы владельца.")
    @Test
    void shouldBeErrorWhileBuyingWithoutCardOwnerTest() {
        pageFactory.buyingForYourMoney();
        pageFactory.enterCardNumber("4444444444444441");
        pageFactory.enterMonth("08");
        pageFactory.enterYear("22");
        pageFactory.enterCvcOrCvvNumber("999");
        pageFactory.pushСontinueButton();
        pageFactory.findFieldFormatNotification();
    }

    @DisplayName("Сценарий №8. Форма заявки без заполненной графы CVC/CVV.")
    @Test
    void shouldBeErrorWhileBuyingWithoutСvcOrCvvTest() {
        pageFactory.buyingForYourMoney();
        pageFactory.enterCardNumber("4444444444444441");
        pageFactory.enterMonth("08");
        pageFactory.enterYear("22");
        pageFactory.enterCardOwner("Александр");
        pageFactory.pushСontinueButton();
        pageFactory.findIncorrectFormatNotification();
    }

    @DisplayName("Сценарий №9. Форма заявки с истекшим сроком карты.")
    @Test
    void shouldBeErrorWhileBuyingWithExpiredCardTest() {
        pageFactory.buyingForYourMoney();
        pageFactory.enterCardNumber("4444444444444441");
        pageFactory.enterMonth("08");
        pageFactory.enterYear("17");
        pageFactory.enterCardOwner("Александр");
        pageFactory.enterCvcOrCvvNumber("999");
        pageFactory.pushСontinueButton();
        pageFactory.findCardExpiryDateNotification();
    }
}
