package yellowsparkle.parking.model;

/**
 * @author ITV1G Group 1
 * @version 1.0
 */
public interface CarDecision {
    Car.Status decide(Car.Status status);
}
