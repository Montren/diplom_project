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
        val conn = DriverManager.getConnection(getUrl(), "app", "pass");
        PreparedStatement statementOrderEntity = conn.prepareStatement(deleteFromOrderEntity);
        PreparedStatement statementPaymentEntity = conn.prepareStatement(deleteFromPaymentEntity);
        PreparedStatement statementCreditEntity = conn.prepareStatement(deleteFromCreditRequestEntity);
        statementOrderEntity.executeUpdate();
        statementPaymentEntity.executeUpdate();
        statementCreditEntity.executeUpdate();
    }

    String tablePaymentSql = "SELECT * FROM payment_entity;";
    String tableCreditSQL = "SELECT * FROM credit_request_entity;";

    public void paymentStatus(Status status, String table) throws SQLException {
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection(getUrl(), "app", "pass");
        val paymentDataSQL = table;
        val payment = runner.query(conn, paymentDataSQL, new BeanHandler<>(Payment.class));
        assertEquals(status, payment.status);
    }

    private static String getUrl() {
        return System.getProperty("test.db.url");
    }
}
