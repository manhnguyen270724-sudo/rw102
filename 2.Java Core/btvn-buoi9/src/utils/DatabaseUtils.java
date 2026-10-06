package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Lớp tiện ích quản lý kết nối đến cơ sở dữ liệu MySQL.
 */
public class DatabaseUtils {
    private static String url = "jdbc:mysql://localhost:3306/testing_system_assignment_1?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "root";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    static {
        Properties properties = new Properties();
        // Đọc từ file database.properties trong resources hoặc thư mục gốc
        try (InputStream input = DatabaseUtils.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                try (FileInputStream fileInput = new FileInputStream("resources/database.properties")) {
                    properties.load(fileInput);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }

        if (properties.containsKey("db.url")) {
            url = properties.getProperty("db.url");
        }
        if (properties.containsKey("db.user")) {
            user = properties.getProperty("db.user");
        }
        if (properties.containsKey("db.password")) {
            password = properties.getProperty("db.password");
        }
        if (properties.containsKey("db.driver")) {
            driver = properties.getProperty("db.driver");
        }

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy JDBC Driver: " + e.getMessage());
        }
    }

    /**
     * Mở kết nối mới đến CSDL.
     *
     * @return Connection đối tượng kết nối
     * @throws SQLException nếu xảy ra lỗi kết nối
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Đóng các tài nguyên JDBC (Connection, Statement, ResultSet,...).
     */
    public static void close(AutoCloseable... closeables) {
        for (AutoCloseable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                    // Bỏ qua lỗi khi đóng tài nguyên
                }
            }
        }
    }
}
