package grocery;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The FoodList class organizes a list of food items.
 */
public class FoodList {

    /**
     * List of food items.
     */
    private List<Food> foodList;

    /**
     * Array of available store departments.
     */
    private static String[] departments;

    /**
     * Constructs a FoodList object.
     */
    public FoodList(){
        foodList = new ArrayList<>();
        departments = new String[]{"pharmacy", "produce", "bulk", "floral", "hotfood", "bakery",
                "deli", "cheeseshop", "meats", "dairy", "frozen", "glutenfree", "liquor"};
    }

    /**
     * Checks if the FoodList contains a specific food item.
     * @param food item to be checked.
     * @return true if the FoodList contains the food item.
     */
    public boolean contains(Food food){
        return foodList.contains(food);
    }

    /**
     * Checks if the FoodList contains a food item by name.
     * @param foodName name of the food item to be checked.
     * @return true if the FoodList contains the food item.
     */
    public boolean contains (String foodName){
        foodName = foodName.trim();
        boolean found = false;
        for (int i = 0; i<foodList.size() && !found; i++){
            String name = foodList.get(i).getName().trim();
            if (name.equalsIgnoreCase(foodName)){
                found = true;
            }
        }
        return found;
    }

    /**
     * Adds a food item to the FoodList.
     * @param food item to be added.
     */
    public void add(Food food){
        if (foodList.size()==0){
            foodList.add(food);
        } else {
            int priority = food.getPriority();
            int index = 0;
            while (index < foodList.size() && priority > foodList.get(index).getPriority()){
                index++;
            }
                foodList.add(index, food);
        }
    }

    /**
     * Gets a Food object from the FoodList by index.
     * @param index of the Food object.
     * @return Food object.
     */
    public Food get(int index){
        return foodList.get(index);
    }

    /**
     * Gets a Food object from the FoodList by name.
     * @param foodName name of the Food object to be returned.
     * @return Food object within the list, or null if no Food object is found.
     */
    public Food get(String foodName){
        Food food = null;
        foodName = foodName.trim();
        for (int i=0; i<foodList.size(); i++){
            if (foodList.get(i).getName().equals(foodName)){
                food = foodList.get(i);
            }
        }
        return food;
    }

    /**
     * Gets the size of the FoodList.
     * @return int value of the size of the FoodList.
     */
    public int size(){
        return foodList.size();
    }

    /**
     * Gets the priority of the Food object within the FoodList.
     * @param food object to get priority.
     * @return int priority of the food object.
     */
    public int getPriority(Food food){
        return foodList.get(foodList.indexOf(food)).getPriority();
    }

    /**
     * Get the priority of the Food object by name.
     * @param foodName String of the food's name.
     * @return int priority of the Food object.
     */
    public int getPriority(String foodName){
        return get(foodName).getPriority();
    }

    /**
     * Loads the FoodList from a file.
     * @param file of the FoodList items.
     */
    public void loadFoodList(File file){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                Food food = parseLine(line);
                if (food != null){
                    foodList.add(food);
                }
            }
        } catch (FileNotFoundException e){
            errorMessage(e.getMessage());
        } catch (IOException e){
            errorMessage(e.getMessage());
        }
    }

    /**
     * Parses a String into a Food object.
     * @param line String information of a Food object.
     * @return Food Object.
     */
    public static Food parseLine(String line){
        Food food = null;
        int quantity = -1;
        String foodName = null;
        String department = null;
        line = line.trim();
        String[] segments = line.split(" ");
        for (int i = 0; i<segments.length; i++){
            if (i==0){
                if (FoodList.isInteger(segments[0])){
                    quantity = Integer.valueOf(segments[0]);
                } else {
                    foodName = segments[i] + " ";
                }
            } else {
                if (!FoodList.isDepartment(segments[i])){
                    if (foodName==null){
                        foodName = segments[i] + " ";
                    } else {
                        foodName += segments[i] + " ";
                    }
                } else {
                    department = segments[i];
                }
            }
        }
        if (foodName != null && department != null) {
            food = new Food(foodName, department);
            if (quantity > 1) {
                food.setQuantity(quantity);
            }
        } else if (foodName != null) {
            food = new Food(foodName);
            if (quantity > 1) {
                food.setQuantity(quantity);
            }
        }
        return food;
    }

    /**
     * Saves the FoodList to a file.
     * @param file to save the FoodList.
     */
    public void saveFoodList(File file){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Food food: foodList){
                writer.write(food.getName() + " " + food.getPriority());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the String is a valid department.
     * @param word String to be checked.
     * @return true if the word is a valid department.
     */
    public static boolean isDepartment(String word){
        boolean isDepartment = false;
        if (isInteger(word)){
            isDepartment = true;
        } else {
            for (String department : departments) {
                if (word.equals(department)) {
                    isDepartment = true;
                }
            }
        }
        return isDepartment;
    }

    /**
     * Checks if a string is an Integer.
     * @param word String to be checked.
     * @return true if the String has an Integer value.
     */
    public static boolean isInteger(String word){
        boolean isInteger = true;
        try {
            Integer.valueOf(word);
        } catch (NumberFormatException e){
            isInteger = false;
        } finally {
            return isInteger;
        }

    }

    /**
     * Shows a popup displaying any errors encountered.
     * @param message to be displayed.
     */
    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
