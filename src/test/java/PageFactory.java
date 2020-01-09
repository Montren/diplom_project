import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PageFactory {
    private SelenideElement cardNumber = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private SelenideElement cardOwner = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcOrCvvNumber = $(byText("CVC/CVV")).parent().$(".input__control");


    public void enterCardNumber(String cardNumberValue){
        cardNumber.setValue(cardNumberValue);
    }

    public void enterMonth(String monthValue){
        month.setValue(monthValue);
    }

    public void enterYear(String yearValue){
        year.setValue(yearValue);
    }

    public void enterCardOwner(String cardOwnerValue){
        cardOwner.setValue(cardOwnerValue);
    }

    public void enterCvcOrCvvNumber(String cvcOrCvvValue){
        cvcOrCvvNumber.setValue(cvcOrCvvValue);
    }


    private String url = "http://localhost:8080";
    private SelenideElement buyButton = $$(".button__content").find(exactText("Купить"));
    private SelenideElement headingThemeOfBuying = $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте"));

    public void buyingForYourMoney(){
        open(url);
        buyButton.click();
        headingThemeOfBuying.shouldBe(visible);
    }

    private SelenideElement takeCreditButton = $$(".button__content").find(exactText("Купить в кредит"));
    private SelenideElement headingThemeOfCredit = $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты"));

    public void buyingOnCredit(){
        open(url);
        takeCreditButton.click();
        headingThemeOfCredit.shouldBe(visible);
    }

    public void activeCardData(){
        enterCardNumber("4444444444444441");
        enterMonth("08");
        enterYear("22");
        enterCardOwner("Александр");
        enterCvcOrCvvNumber("999");
    }

    public void inactiveCardData(){
        enterCardNumber("4444444444444442");
        enterMonth("08");
        enterYear("22");
        enterCardOwner("Александр");
        enterCvcOrCvvNumber("999");
    }

    private SelenideElement continueButton = $$(".button__content").find(exactText("Продолжить"));
    public void pushСontinueButton(){
        continueButton.click();
    }

    private SelenideElement successNotification = $$(".notification__title").find(exactText("Успешно"));
    public void findSuccessNotification() { successNotification.waitUntil(visible, 20000);}

    private SelenideElement unsuccessNotification = $$(".notification__title").find(exactText("Ошибка"));
    public void findUnsuccessNotification() { unsuccessNotification.waitUntil(visible, 4000);}

    private SelenideElement incorrectFormatNotification = $$(".input__sub").find(exactText("Неверный формат"));
    public void findIncorrectFormatNotification() {incorrectFormatNotification.shouldBe(visible);}

    private SelenideElement cardIncorrectDateFormatNotification = $(byText("Неверно указан срок действия карты"));
    public void findCardIncorrectDateFormatNotification() {cardIncorrectDateFormatNotification.shouldBe(visible);}

    private SelenideElement fieldFormatNotification = $(byText("Поле обязательно для заполнения"));
    public void findFieldFormatNotification(){fieldFormatNotification.shouldBe(visible);}

    private SelenideElement cardExpiryDateNotification = $(byText("Истёк срок действия карты"));
    public void findCardExpiryDateNotification() {cardExpiryDateNotification.shouldBe(visible);}
}
