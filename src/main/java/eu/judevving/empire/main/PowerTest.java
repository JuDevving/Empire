package eu.judevving.empire.main;

public class PowerTest {

    public static final int POWER_DIVISOR = 200; // the smaller, the quicker squares cost more power
    public static final int POWER_FACTOR = 10; // the bigger, the less valuable power is

    public static void main(String[] args) {
        System.out.println(getMaxSize(200000));
    }

    public static int getMaxSize(int power) {
        int maxSize = (int) (-POWER_DIVISOR
                + Math.sqrt(POWER_DIVISOR * POWER_DIVISOR + 2 * POWER_DIVISOR * (long) power / (double) POWER_FACTOR));
        if (getEntirePowerCost(maxSize + 1) <= power) maxSize++;
        return maxSize;
    }

    public static int getEntirePowerCost(int size) {
        double x = size;
        return (int) (POWER_FACTOR * x + POWER_FACTOR * x * x / (2 * POWER_DIVISOR));
    }

}
