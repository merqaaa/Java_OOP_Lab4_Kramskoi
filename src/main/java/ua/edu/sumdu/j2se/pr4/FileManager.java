package ua.edu.sumdu.j2se.pr4;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String FILE_NAME = "input.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveToFile(List<StoreItem> inventory) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(inventory, writer);
            System.out.println("\n[СИСТЕМА] Дані успішно збережено у файл: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("\n[ПОМИЛКА] Помилка запису: " + e.getMessage());
        }
    }

    public static List<StoreItem> loadFromFile() {
        List<StoreItem> inventory = new ArrayList<>();
        File file = new File(FILE_NAME);
        
        if (!file.exists()) {
            System.out.println("[СИСТЕМА] Файл збережень не знайдено. Починаємо з порожнього списку.");
            return inventory;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonElement element = JsonParser.parseReader(reader);
            if (element != null && element.isJsonArray()) {
                JsonArray jsonArray = element.getAsJsonArray();
                for (JsonElement el : jsonArray) {
                    JsonObject wrapperObj = el.getAsJsonObject();
                    int quantity = wrapperObj.get("quantity").getAsInt();
                    
                    JsonObject itemObj = wrapperObj.getAsJsonObject("item");
                    String classType = itemObj.get("classType").getAsString();

                    Clothes item = switch (classType) {
                        case "Pants" -> gson.fromJson(itemObj, Pants.class);
                        case "Jeans" -> gson.fromJson(itemObj, Jeans.class);
                        case "Shirts" -> gson.fromJson(itemObj, Shirts.class);
                        case "DressShirts" -> gson.fromJson(itemObj, DressShirts.class);
                        default -> gson.fromJson(itemObj, Clothes.class);
                    };
                    inventory.add(new StoreItem(item, quantity));
                }
            }
            System.out.println("[СИСТЕМА] Дані успішно завантажено з файлу: " + FILE_NAME);
        } catch (Exception e) {
            System.out.println("[ПОМИЛКА] Помилка читання файлу: " + e.getMessage());
        }
        return inventory;
    }
}
