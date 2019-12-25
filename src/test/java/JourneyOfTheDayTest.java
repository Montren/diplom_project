import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class JourneyOfTheDayTest {

    DataHelper dataHelper = new DataHelper();
    SQLHelper sqlHelper = new SQLHelper();

    @DisplayName("Сценарий №1. Покупка путешествия с действующей карты со своих денежных средств.")
    @Test
    void shouldBeApprovedStatusWhileBuyingTest() throws SQLException {
        sqlHelper.cleanDatabase();
        dataHelper.buyingForYourMoney();
        dataHelper.activeCardData();
        dataHelper.pushСontinueButton();
        dataHelper.successNotification();
        sqlHelper.checkLastPaymentStatus(Status.APPROVED);
        }

    @DisplayName("Сценарий №2. Покупка путешествия с действующей карты в кредит.")
    @Test
    void shouldBeApprovedStatusWhileTakeCreditTest() throws SQLException {
        sqlHelper.cleanDatabase();
        dataHelper.buyingOnCredit();
        dataHelper.activeCardData();
        dataHelper.pushСontinueButton();
        dataHelper.successNotification();
        sqlHelper.checkLastCreditPaymentStatus(Status.APPROVED);
    }

    @DisplayName("Сценарий №3. Сценарий покупки путешествия с заблокированной карты в кредит.")
    @Test
    void shouldBeDeclineStatusWhileTakeCreditWithLockedCardTest() throws SQLException {
        sqlHelper.cleanDatabase();
        dataHelper.buyingOnCredit();
        dataHelper.inactiveCardData();
        dataHelper.pushСontinueButton();
        dataHelper.unsuccessNotification();
        sqlHelper.checkLastCreditPaymentStatus(Status.DECLINED);
    }

    @DisplayName("Сценарий №4. Сценарий покупки путешествия с заблокированной карты со своих денег.")
    @Test
    void shouldBeDeclineStatusWhileBuyingWithLockedCardTest() throws SQLException {
        sqlHelper.cleanDatabase();
        dataHelper.buyingForYourMoney();
        dataHelper.inactiveCardData();
        dataHelper.pushСontinueButton();
        dataHelper.unsuccessNotification();
        sqlHelper.checkLastPaymentStatus(Status.DECLINED);
    }

    @DisplayName("Сценарий №5. Форма заявки без номера карты.")
    @Test
    void shouldBeErrorWhileBuyingWithoutCardNumberTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.enterMonth("08");
        dataHelper.enterYear("22");
        dataHelper.enterCardOwner("Александр");
        dataHelper.enterCvcOrCvvNumber("999");
        dataHelper.pushСontinueButton();
        dataHelper.formatNotification();
    }

    @DisplayName("Сценарий №6. Заявка с некорретным месяцем/годом окончания действия карты.")
    @Test
    void shouldBeErrorWhileBuyingWithIncorrectMonthAndYearTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.enterCardNumber("4444444444444441");
        dataHelper.enterMonth("15");
        dataHelper.enterYear("48");
        dataHelper.enterCardOwner("Александр");
        dataHelper.enterCvcOrCvvNumber("999");
        dataHelper.pushСontinueButton();
        dataHelper.cardExpiryDateFormatNotification();
    }

    @DisplayName("Сценарий №7. Форма заявки без заполненной графы владельца.")
    @Test
    void shouldBeErrorWhileBuyingWithoutCardOwnerTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.enterCardNumber("4444444444444441");
        dataHelper.enterMonth("08");
        dataHelper.enterYear("22");
        dataHelper.enterCvcOrCvvNumber("999");
        dataHelper.pushСontinueButton();
        dataHelper.fieldFormatNotification();
    }

    @DisplayName("Сценарий №8. Форма заявки без заполненной графы CVC/CVV.")
    @Test
    void shouldBeErrorWhileBuyingWithoutСvcOrCvvTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.enterCardNumber("4444444444444441");
        dataHelper.enterMonth("08");
        dataHelper.enterYear("22");
        dataHelper.enterCardOwner("Александр");
        dataHelper.pushСontinueButton();
        dataHelper.formatNotification();
    }

    @DisplayName("Сценарий №9. Форма заявки с истекшим сроком карты.")
    @Test
    void shouldBeErrorWhileBuyingWithExpiredCardTest() {
        dataHelper.buyingForYourMoney();
        dataHelper.enterCardNumber("4444444444444441");
        dataHelper.enterMonth("08");
        dataHelper.enterYear("17");
        dataHelper.enterCardOwner("Александр");
        dataHelper.enterCvcOrCvvNumber("999");
        dataHelper.pushСontinueButton();
        dataHelper.cardExpiryDateNotification();
    }
}
