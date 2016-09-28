package grocery;

/**
 * Created by mccomackjp on 4/24/2016.
 */
public class Food {

    public static final int MISC_PRIORITY = 99;

    private int priority;

    private String name;

    private int quantity;

    public Food(String name){
        this(name, MISC_PRIORITY);
        quantity = 1;
    }

    public Food(String name, int priority){
        setName(name);
        setPriority(priority);
        quantity = 1;
    }

    public Food(String name, String department){
        setName(name);
        setPriority(getDepartmentPriority(department.toLowerCase()));
        quantity = 1;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

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

    private int parseDepartment(String department) {
        return Integer.parseInt(department);
    }

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


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    @Override
    public boolean equals(Object o){
        boolean isEqual = false;
        if (o instanceof Food){
            Food newFood = (Food) o;
            isEqual = this.name.equals(newFood.getName());
        }
        return isEqual;
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }

}
