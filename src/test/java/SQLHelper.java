import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLHelper {

    private String orderCountSQL = "SELECT COUNT(id) AS countID FROM order_entity;";
    private String orderDataSQL = "SELECT * FROM order_entity;";
    private String paymentDataSQL = "SELECT * FROM payment_entity;";
    private String paymentCountSQL = "SELECT COUNT(id) AS countID FROM payment_entity;";
    private String creditDataSQL = "SELECT * FROM credit_request_entity;";
    private String creditCountSQL = "SELECT COUNT(id) AS count FROM credit_request_entity;";

    public void cleanDatabase() throws SQLException {
        val deleteFromOrderEntity = "delete from order_entity;";
        val deleteFromPaymentEntity = "delete from payment_entity;";
        val deleteFromCreditRequestEntity = "delete from credit_request_entity;";
        try (
                val conn = DriverManager.getConnection(getUrl(), getUser(), getPassword());
        ) {
        PreparedStatement statementOrderEntity = conn.prepareStatement(deleteFromOrderEntity);
        PreparedStatement statementPaymentEntity = conn.prepareStatement(deleteFromPaymentEntity);
        PreparedStatement statementCreditEntity = conn.prepareStatement(deleteFromCreditRequestEntity);
        statementOrderEntity.executeUpdate();
        statementPaymentEntity.executeUpdate();
        statementCreditEntity.executeUpdate();
    }
}

    public void checkLastPaymentStatus(Status status) throws SQLException {
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(getUrl(), getUser(), getPassword());
        ) {
            val payment = runner.query(conn, paymentDataSQL, new BeanHandler<>(Payment.class));
            val paymentId = runner.query(conn, orderDataSQL, new BeanHandler<>(Order.class));
            val orderCount = runner.query(conn, orderCountSQL, new BeanHandler<>(Order.class));
            val paymentCount = runner.query(conn, paymentCountSQL, new BeanHandler<>(Payment.class));
            assertEquals(1, orderCount.countID);
            assertEquals(1, paymentCount.countID);
            assertEquals(status, payment.status);
            assertEquals(payment.transaction_id, paymentId.payment_id);
        }
    }

    public void checkLastCreditPaymentStatus(Status status) throws SQLException {
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(getUrl(), getUser(), getPassword());
        ) {
        val credit = runner.query(conn, creditDataSQL, new BeanHandler<>(Credit.class));
        val creditId = runner.query(conn, orderDataSQL, new BeanHandler<>(Order.class));
        val orderCount = runner.query(conn, orderCountSQL, new BeanHandler<>(Order.class));
        val creditCount = runner.query(conn, creditCountSQL, new BeanHandler<>(Credit.class));
        assertEquals(1, orderCount.countID);
        assertEquals(1, creditCount.countID);
        assertEquals(status, credit.status);
        assertEquals(credit.bank_id, creditId.credit_id);
    }
}

    private static String getUrl() { return System.getProperty("test.db.url"); }

    private static String getUser() { return "app"; }

    private static String getPassword() { return "pass"; }

}
