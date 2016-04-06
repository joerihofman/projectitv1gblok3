package yellowsparkle.parking.simulation;

import yellowsparkle.parking.model.Car;

import java.util.LinkedList;
import java.util.Queue;

@Deprecated
public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }
}
