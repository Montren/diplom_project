import com.codeborne.selenide.SelenideElement;
import java.util.List;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class DataHelper {
    List<SelenideElement> input = $$(".input__control");
    SelenideElement cardNumber = input.get(0);
    SelenideElement month = input.get(1);
    SelenideElement year = input.get(2);
    SelenideElement cardOwner = input.get(3);
    SelenideElement cvcOrCvvNumber = input.get(4);

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
        cardNumber.setValue("4444444444444441");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("Александр");
        cvcOrCvvNumber.setValue("999");
    }

    public void inactiveCardData(){
        cardNumber.setValue("4444444444444442");
        month.setValue("08");
        year.setValue("22");
        cardOwner.setValue("Александр");
        cvcOrCvvNumber.setValue("999");
    }

    public void pushСontinueButton(){
        $$(".button__content").find(exactText("Продолжить")).click();
    }
}
