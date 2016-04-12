/**
 * @author ITV1G Group 1
 * @version 1.0
 * @since 4/4/16
 */
package yellowsparkle.parking.model;

/**
 * Makes car's decisions
 */
public interface CarDecision {
    Car.Status decide(Car.Status status);
}
