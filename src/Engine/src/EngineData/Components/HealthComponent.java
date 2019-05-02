package Engine.src.EngineData.Components;

import static java.lang.Integer.parseInt;

public class HealthComponent extends Component {
    private int myHealth;
    private int myMaxHealth;

    public HealthComponent(String health, String maxHealth) {
        myHealth = parseInt(health);
        myMaxHealth = parseInt(maxHealth);
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

    @Override
    public Component copy() {
        return new HealthComponent(Double.toString(myHealth), Double.toString(myMaxHealth));
    }

}
