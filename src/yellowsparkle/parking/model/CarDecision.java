package yellowsparkle.parking.model;

public interface CarDecision {
    Car.Status decide(Car.Status status);
}
