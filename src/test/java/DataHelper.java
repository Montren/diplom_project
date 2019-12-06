import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

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


    public void paymentStatus(Status status) throws SQLException {
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pass");
        val paymentDataSQL = "SELECT * FROM payment_entity WHERE created >= (now() - interval 5 minute) ORDER BY created DESC;";
        val payment = runner.query(conn, paymentDataSQL, new BeanHandler<>(Payment.class));
        assertEquals(status, payment.status);
    }

    public void creditStatus(Status status) throws SQLException {
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pass");
        val creditDataSQL = "SELECT * FROM credit_request_entity WHERE created >= (now() - interval 5 minute) ORDER BY created DESC;";
        val payment = runner.query(conn, creditDataSQL, new BeanHandler<>(Payment.class));
        assertEquals(status, payment.status);
    }
}
