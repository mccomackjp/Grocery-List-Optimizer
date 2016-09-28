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
 * Created by mccomackjp on 4/27/2016.
 */
public class FoodList {

    private List<Food> foodList;

    private static String[] departments;

    public FoodList(){
        foodList = new ArrayList<>();
        departments = new String[]{"pharmacy", "produce", "bulk", "floral", "hotfood", "bakery",
                "deli", "cheeseshop", "meats", "dairy", "frozen", "glutenfree", "liquor"};
    }

    public boolean contains(Food food){
        return foodList.contains(food);
    }

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

    public Food get(int index){
        return foodList.get(index);
    }

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

    public int size(){
        return foodList.size();
    }

    public int getPriority(Food food){
        return foodList.get(foodList.indexOf(food)).getPriority();
    }

    public int getPriority(String foodName){
        return get(foodName).getPriority();
    }

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

    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
