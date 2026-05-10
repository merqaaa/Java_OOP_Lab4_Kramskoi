package ua.edu.sumdu.j2se.pr4;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private String url;
    private String user;
    private String password;

    public DatabaseManager(String propertiesFilePath) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
            props.load(fis);
            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");
            System.out.println("[БД] Конфігурацію завантажено з файлу: " + propertiesFilePath);
        } catch (IOException e) {
            System.out.println("[ПОМИЛКА] Не вдалося зчитати конфігурацію БД: " + e.getMessage());
        }
    }

    public void insertClothes(Clothes item) {
        String sql = "INSERT INTO clothes_inventory (type, brand, size, price, length, wash_type, sleeve_type, collar_size) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getClassType());
            pstmt.setString(2, item.getBrand());
            pstmt.setString(3, item.getSize().name());
            pstmt.setDouble(4, item.getPrice());

            // Поліморфне заповнення полів
            if (item instanceof Pants pants) {
                pstmt.setInt(5, pants.getLength());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }

            if (item instanceof Jeans jeans) {
                pstmt.setString(6, jeans.getWashType());
            } else {
                pstmt.setNull(6, Types.VARCHAR);
            }

            if (item instanceof Shirts shirts) {
                pstmt.setString(7, shirts.getSleeveType());
            } else {
                pstmt.setNull(7, Types.VARCHAR);
            }

            if (item instanceof DressShirts dressShirts) {
                pstmt.setDouble(8, dressShirts.getCollarSize());
            } else {
                pstmt.setNull(8, Types.NUMERIC);
            }

            pstmt.executeUpdate();
            System.out.println("[БД] Об'єкт (" + item.getClassType() + ") успішно збережено в PostgreSQL!");

        } catch (SQLException e) {
            System.out.println("[ПОМИЛКА БД] " + e.getMessage());
        }
    }
}
