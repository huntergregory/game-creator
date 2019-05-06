package Engine.src.EngineData.Components;

import static java.lang.Integer.parseInt;

/**
 * Component that keeps track of the health and max health of an EngineInstance
 * @author David Liu
 */
public class HealthComponent extends Component {
    private int myHealth;
    private int myMaxHealth;

    /**
     * Initializes amount of health, and max health possible
     * @param health amount of health
     * @param maxHealth max health possible
     */
    public HealthComponent(String health, String maxHealth) {
        myHealth = parseInt(health);
        myMaxHealth = parseInt(maxHealth);
    }

    /**
     * Initializes amount of health
     * @param health amount of health
     */
    public HealthComponent(int health) {
        myHealth = health;
    }

    /**
     * Gets health
     * @return health
     */
    public int getHealth() {
        return myHealth;
    }

    /**
     * Gets max health
     * @return max health
     */
    public int getMaxHealth() {
        return myMaxHealth;
    }

    /**
     * Sets new health
     * @param health new health
     */
    public void setHealth(int health) {
        myHealth = health;
    }

    /**
     * Sets max health
     * @param maxHealth new max health
     */
    public void setMaxHealth(int maxHealth) {
        myMaxHealth = maxHealth;
    }

    /**
     * Gives a copy of the HealthComponent
     * @return copy of the HealthComponent
     */
    @Override
    public Component copy() {
        return new HealthComponent(Integer.toString(myHealth), Integer.toString(myMaxHealth));
    }

}
