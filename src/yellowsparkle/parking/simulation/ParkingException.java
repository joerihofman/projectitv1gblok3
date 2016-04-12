/**
 * @author ITV1G Group 1
 * @version 1.0
 * @since 4/4/16
 */

package yellowsparkle.parking.simulation;

/**
 * Constructor parking exception
 */
public class ParkingException extends Exception {

    /**
     * Sends super message
     * @param message sents message
     */
    public ParkingException(String message) {
        super(message);
    }
}
