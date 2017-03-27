package grocery;

/**
 * Tracks a food item's name, quantity, and aile priority.
 */
public class Food {

    /**
     * Miscellaneous priority number assigned if no priority number is assigned.
     */
    public static final int MISC_PRIORITY = 99;

    /**
     * Priority number for sorting the food.
     */
    private int priority;

    /**
     * Name of the food.
     */
    private String name;

    /**
     * Amount of food items on the list.
     */
    private int quantity;

    /**
     * Constructs a Food object.
     * @param name of the food item.
     */
    public Food(String name){
        this(name, MISC_PRIORITY);
        quantity = 1;
    }

    /**
     * Constructs a Food object.
     * @param name of the food item.
     * @param priority of the food item for sorting.
     */
    public Food(String name, int priority){
        setName(name);
        setPriority(priority);
        quantity = 1;
    }

    /**
     * Constructs a Food object.
     * @param name of the food item.
     * @param department of the food item, used for assigning a priority number.
     */
    public Food(String name, String department){
        setName(name);
        setPriority(getDepartmentPriority(department.toLowerCase()));
        quantity = 1;
    }

    /**
     * Gets the quantity of food items for displaying on a list.
     * @return int value of the quantity of food items.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of food items for displaying on a list.
     * @param quantity int value of the quantity of food items.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Converts a department String into an int value for priority.
     * @param department String of the food department.
     * @return int value of the departments priority.
     */
    private int getDepartmentPriority(String department) {
        int priority;
        if (isInteger(department)){
            priority = parseDepartment(department);
        } else {
            switch (department) {
                case "pharmacy":
                    priority = -8;
                    break;
                case "produce":
                    priority = -7;
                    break;
                case "bulk":
                    priority = -6;
                    break;
                case "floral":
                    priority = -7;
                    break;
                case "hotfood":
                    priority = -5;
                    break;
                case "bakery":
                    priority = -4;
                    break;
                case "deli":
                    priority = -3;
                    break;
                case "cheeseshop":
                    priority = -2;
                    break;
                case "meats":
                    priority = -1;
                    break;
                case "dairy":
                    priority = 19;
                    break;
                case "frozen":
                    priority = 20;
                    break;
                case "glutenfree":
                    priority = 21;
                    break;
                case "liquor":
                    priority = 22;
                    break;
                default:
                    priority = MISC_PRIORITY;
            }
        }
        return priority;
    }

    /**
     * Returns an int value from a string.
     * @param department String value of an int.
     * @return int value from string.
     */
    private int parseDepartment(String department) {
        return Integer.parseInt(department);
    }

    /**
     * Checks if a string is an integer.
     * @param department String to be checked.
     * @return true if the string is an Integer.
     */
    private boolean isInteger(String department) {
        boolean isInteger = true;
        if (!Character.isDigit(department.charAt(0)) && department.charAt(0) != '-'){
            isInteger = false;
        } else {
            for (int i = 1; i < department.length(); i++) {
                if (!Character.isDigit(department.charAt(i))) {
                    isInteger = false;
                }
            }
        }
        return isInteger;
    }

    /**
     * Gets the priority of the food item.
     * @return priority of the food item.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the food item.
     * @param priority of the food item.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Gets the name of the food item.
     * @return String name of the food item.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the food item.
     * @param name String name of the food item.
     */
    public void setName(String name) {
        this.name = name.trim();
    }

    /**
     * Checks if a food item is equal to another by name.
     * @param o food item to be compared.
     * @return true if the food item names are equal.
     */
    @Override
    public boolean equals(Object o){
        boolean isEqual = false;
        if (o instanceof Food){
            Food newFood = (Food) o;
            isEqual = this.name.equals(newFood.getName());
        }
        return isEqual;
    }

    /**
     * Gets a hash code of the food item based on the name.
     * @return int value of the hashCode.
     */
    @Override
    public int hashCode(){
        return this.name.hashCode();
    }

}
