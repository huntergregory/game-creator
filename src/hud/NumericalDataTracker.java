package hud;

/**
 * A DataTracker that's guaranteed to have numerical data.
 * @param <T>
 *
 * @author Hunter Gregory
 */
public class NumericalDataTracker<T extends Number> extends DataTracker {
    public NumericalDataTracker(String name) {
        super(name);
    }
}
