package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtils {
    private static QueryRunner runner;
    private static Connection conn;

    @SneakyThrows
    public static void setUp() {
        runner = new QueryRunner();
        conn = DriverManager.
                getConnection("jdbc:mysql://localhost:3306/app", "user", "pass");
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        setUp();
        var statusSQL = "SELECT status FROM payment_entity";
        return runner.query(conn, statusSQL, new ScalarHandler<String>());
    }

    @SneakyThrows
    public static String getCreditStatus() {
        setUp();
        var statusSQL = "SELECT status FROM credit_request_entity";
        return runner.query(conn, statusSQL, new ScalarHandler<String>());
    }

    @SneakyThrows
    public static void clearData() {
        setUp();
        var deletePayment = "DELETE FROM payment_entity";
        var deleteCredit = "DELETE FROM credit_request_entity";
        var deleteOrder = "DELETE FROM order_entity";
        runner.update(conn, deletePayment);
        runner.update(conn, deleteCredit);
        runner.update(conn, deleteOrder);
    }

}
