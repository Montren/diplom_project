import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DataHelper {
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



    public void buyingForYourMoney(){
        open("http://localhost:8080");
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
    }

    public void buyingOnCredit(){
        open("http://localhost:8080");
        $$(".button__content").find(exactText("Купить в кредит")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты")).shouldBe(visible);
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

    public void pushСontinueButton(){
        $$(".button__content").find(exactText("Продолжить")).click();
    }

    public void successNotification() { $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 20000);}

    public void unsuccessNotification() { $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 4000);}

    public void formatNotification() {$$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);}

    public void cardExpiryDateFormatNotification() {$(byText("Неверно указан срок действия карты")).shouldBe(visible);}

    public void fieldFormatNotification(){$(byText("Поле обязательно для заполнения")).shouldBe(visible);}

    public void cardExpiryDateNotification() {$(byText("Истёк срок действия карты")).shouldBe(visible);}

}
