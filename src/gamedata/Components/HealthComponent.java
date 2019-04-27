package Engine.src.Components;

public class HealthComponent extends Component {
    private int myHealth;
    private int myMaxHealth;

    public HealthComponent(int health, int maxHealth) {
        myHealth = health;
        myMaxHealth = maxHealth;
    }

    public HealthComponent(int health) {
        myHealth = health;
    }

    public int getHealth() {
        return myHealth;
    }

    public int getMaxHealth() {
        return myMaxHealth;
    }

    public void setHealth(int health) {
        myHealth = health;
    }

    public void setMaxHealth(int maxHealth) {
        myMaxHealth = maxHealth;
    }
}
