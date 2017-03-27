package grocery;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The GroceryListOptimizer will optimize a grocery list based on the order of aile.
 */
public class GroceryListOptimizer extends JFrame {

    private String clearedList;

    private File foodListFile;

    private FoodList foodList;

    private JTextArea listInput;

    private List<Food> shoppingList;

    private JButton undoClearListButton;

    public GroceryListOptimizer(){
        shoppingList = new ArrayList<>();
        foodList = new FoodList();
        foodListFile = new File("foodList");
        loadFoodList();
        buildGUI();
    }

    private void loadFoodList() {
        foodList.loadFoodList(foodListFile);
    }

    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void clearListInput() {
        if (!listInput.getText().equals("")) {
            undoClearListButton.setEnabled(true);
            clearedList = listInput.getText();
            listInput.setText("");
        }
    }

    private void saveFood() {
        foodList.saveFoodList(foodListFile);
    }

    private void saveAndExit() {
        loadShoppingList();
        saveFood();
        saveOldShoppingList();
        System.exit(0);
    }

    private void saveOldShoppingList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("oldShoppingList"))) {
            for (Food food : shoppingList){
                if (food.getQuantity() > 1){
                    writer.write(String.valueOf(food.getQuantity()));
                    writer.write(" ");
                }
                writer.write(food.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadOldShoppingList(){
        String oldShoppingList = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("oldShoppingList"))) {
            String line;
            while ((line = reader.readLine()) != null){
                oldShoppingList += line + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oldShoppingList;
    }

    private void undoClearListInput() {
        listInput.setText(clearedList);
        undoClearListButton.setEnabled(false);
    }

    private void optimize() {
        loadShoppingList();
        syncLists();
        sortShoppingList();
        printShoppingList();
    }

    private void printShoppingList() {
        String output = "";
        int quantity;
        for (Food food : shoppingList){
            if ((quantity = food.getQuantity()) > 1){
                output += quantity + " ";
            }
            output += food.getName() + "\n";
        }
        listInput.setText(output);
    }

    private void sortShoppingList() {
        List<Food> newList = new ArrayList<>();
        for (int i=0; i<foodList.size(); i++){
            for (int j=0; j<shoppingList.size(); j++){
                if (foodList.get(i).equals(shoppingList.get(j))){
                    newList.add(shoppingList.get(j));
                }
            }
        }
        shoppingList = newList;
    }

    private void syncLists() {
        for (int i=0; i<shoppingList.size(); i++) {
            if (!foodList.contains(shoppingList.get(i).getName())){
                foodList.add(shoppingList.get(i));
            } else {
                shoppingList.get(i).setPriority(foodList.getPriority(shoppingList.get(i).getName()));
            }
        }
    }

    private void loadShoppingList() {
        shoppingList = new ArrayList<>();
        Scanner input = new Scanner(listInput.getText());
        while (input.hasNextLine()){
            Food food = FoodList.parseLine(input.nextLine().toLowerCase());
            if (food != null){
                shoppingList.add(food);
            }
        }
    }

    private void buildGUI() {
        setTitle("Grocery List Optimizer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(900,700);

        String oldShoppingList = loadOldShoppingList();

        JLabel diagram = new JLabel(new ImageIcon("src/data/diagram.JPG"));
        JButton optimizeButton = new JButton("Optimize List");
        JButton saveAndExitButton = new JButton("Save and exit");
        JButton clearListButton = new JButton("Clear shopping list");
        undoClearListButton = new JButton("Undo clear list");
        undoClearListButton.setEnabled(false);
        listInput = new JTextArea(oldShoppingList);
        listInput.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(listInput);
        scrollPane.setSize(600, 600);
        JPanel buttonsPanel = new JPanel();

        optimizeButton.addActionListener(e-> optimize());
        saveAndExitButton.addActionListener(e-> saveAndExit());
        clearListButton.addActionListener(e-> clearListInput());
        undoClearListButton.addActionListener(e-> undoClearListInput());

        buttonsPanel.add(optimizeButton);
        buttonsPanel.add(clearListButton);
        buttonsPanel.add(undoClearListButton);
        buttonsPanel.add(saveAndExitButton);

        add(diagram, BorderLayout.WEST);
        add(buttonsPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

}

