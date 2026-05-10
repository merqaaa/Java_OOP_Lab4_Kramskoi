package ua.edu.sumdu.j2se.pr4.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.pr4.*;

public class MainApp extends Application {
    private Store store = new Store();
    private ListView<String> listView = new ListView<>();

    @Override
    public void init() {
        store.setInventory(FileManager.loadFromFile());
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Магазин Одягу - JavaFX GUI");

        // --- ЛІВА ПАНЕЛЬ (Додавання об'єкта) ---
        VBox addBox = new VBox(10);
        addBox.setPadding(new Insets(10));
        addBox.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        
        Label lblAdd = new Label("Додати новий товар:");
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Штани", "Джинси", "Сорочка");
        typeCombo.setValue("Штани");

        TextField txtBrand = new TextField(); txtBrand.setPromptText("Бренд");
        ComboBox<Size> sizeCombo = new ComboBox<>();
        sizeCombo.getItems().addAll(Size.values());
        sizeCombo.setValue(Size.M);
        
        TextField txtPrice = new TextField(); txtPrice.setPromptText("Ціна");
        Button btnAdd = new Button("Додати");

        btnAdd.setOnAction(e -> {
            try {
                double price = Double.parseDouble(txtPrice.getText());
                Clothes item = null;
                switch (typeCombo.getValue()) {
                    case "Штани" -> item = new Pants(txtBrand.getText(), sizeCombo.getValue(), price, 100);
                    case "Джинси" -> item = new Jeans(txtBrand.getText(), sizeCombo.getValue(), price, 100, "Classic");
                    case "Сорочка" -> item = new Shirts(txtBrand.getText(), sizeCombo.getValue(), price, "Long");
                }
                if (item != null) {
                    store.addNewClothes(item, 1);
                    updateListView();
                    showAlert("Успіх", "Об'єкт автоматично отримав UUID:\n" + item.getUuid());
                }
            } catch (Exception ex) {
                showAlert("Помилка", "Некоректні дані: " + ex.getMessage());
            }
        });
        addBox.getChildren().addAll(lblAdd, typeCombo, txtBrand, sizeCombo, txtPrice, btnAdd);

        // --- ПРАВА ПАНЕЛЬ (Пошук за UUID) ---
        VBox searchBox = new VBox(10);
        searchBox.setPadding(new Insets(10));
        searchBox.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        
        Label lblSearch = new Label("Пошук за UUID:");
        TextField txtSearchUuid = new TextField(); txtSearchUuid.setPromptText("Введіть UUID");
        Button btnSearch = new Button("Знайти");
        TextArea txtResult = new TextArea(); txtResult.setEditable(false);

        btnSearch.setOnAction(e -> {
            try {
                StoreItem found = store.searchByUuid(txtSearchUuid.getText());
                if (found != null) {
                    txtResult.setText("ЗНАЙДЕНО:\n" + found.toString());
                } else {
                    txtResult.setText("Об'єкт не знайдено.");
                }
            } catch (IllegalArgumentException ex) {
                txtResult.setText("Помилка: Некоректний формат UUID.");
            }
        });
        searchBox.getChildren().addAll(lblSearch, txtSearchUuid, btnSearch, txtResult);

        // --- ЦЕНТР (Список) ---
        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(10));
        Label lblList = new Label("Список товарів (Коротко):");
        updateListView();
        centerBox.getChildren().addAll(lblList, listView);

        // --- КОМПОНУВАННЯ ---
        BorderPane root = new BorderPane();
        root.setLeft(addBox);
        root.setRight(searchBox);
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 900, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateListView() {
        listView.getItems().clear();
        for (StoreItem si : store.getInventory()) {
            Clothes c = si.getItem();
            // Короткий вивід за вимогами
            listView.getItems().add(c.getType() + " " + c.getBrand() + " | UUID: " + c.getUuid());
        }
    }

    @Override
    public void stop() {
        FileManager.saveToFile(store.getInventory());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
