import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLHelper {

    public void cleanDatabase() throws SQLException {
        val deleteFromOrderEntity = "delete from order_entity;";
        val deleteFromPaymentEntity = "delete from payment_entity;";
        val deleteFromCreditRequestEntity = "delete from credit_request_entity;";
        val conn = DriverManager.getConnection(getUrl(), getUser(), getPassword());
        PreparedStatement statementOrderEntity = conn.prepareStatement(deleteFromOrderEntity);
        PreparedStatement statementPaymentEntity = conn.prepareStatement(deleteFromPaymentEntity);
        PreparedStatement statementCreditEntity = conn.prepareStatement(deleteFromCreditRequestEntity);
        statementOrderEntity.executeUpdate();
        statementPaymentEntity.executeUpdate();
        statementCreditEntity.executeUpdate();
    }

    public void checkLastPaymentStatus(Status status) throws SQLException {
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection(getUrl(), getUser(), getPassword());
        val paymentDataSQL = "SELECT * FROM payment_entity;";
        val orderDataSQL = "SELECT * FROM order_entity;";
        val orderCountSQL = "SELECT COUNT(id) AS countID FROM order_entity;";
        val paymentCountSQL = "SELECT COUNT(id) AS countID FROM payment_entity;";
        val payment = runner.query(conn, paymentDataSQL, new BeanHandler<>(Payment.class));
        val transactionId = runner.query(conn, orderDataSQL, new BeanHandler<>(Payment.class));
        val orderCount = runner.query(conn, orderCountSQL, new BeanHandler<>(Payment.class));
        val paymentCount = runner.query(conn, paymentCountSQL, new BeanHandler<>(Payment.class));
        assertEquals(1, orderCount.countID);
        assertEquals(1, paymentCount.countID);
        assertEquals(status, payment.status);
        assertEquals(payment.transaction_id, transactionId.payment_id);
        conn.close();
    }

    public void checkLastCreditPaymentStatus(Status status) throws SQLException {
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection(getUrl(), getUser(), getPassword());
        val paymentDataSQL = "SELECT * FROM credit_request_entity;";
        val orderDataSQL = "SELECT * FROM order_entity;";
        val orderCountSQL = "SELECT COUNT(id) AS count FROM order_entity;";
        val creditCountSQL = "SELECT COUNT(id) AS count FROM credit_request_entity;";
        val payment = runner.query(conn, paymentDataSQL, new BeanHandler<>(Payment.class));
        val transactionId = runner.query(conn, orderDataSQL, new BeanHandler<>(Payment.class));
        val orderCount = runner.query(conn, orderCountSQL, new BeanHandler<>(Payment.class));
        val creditCount = runner.query(conn, creditCountSQL, new BeanHandler<>(Payment.class));
        assertEquals(1, orderCount.countID);
        assertEquals(1, creditCount.countID);
        assertEquals(status, payment.status);
        assertEquals(payment.bank_id, transactionId.credit_id);
        conn.close();
    }

    private static String getUrl() {
        return System.getProperty("test.db.url");
    }

    private static String getUser() { return "app"; }

    private static String getPassword() { return "pass"; }

}
