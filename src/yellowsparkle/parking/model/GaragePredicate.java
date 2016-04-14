package yellowsparkle.parking.model;

import java.util.Date;

public class GaragePredicate {

    private final PredicateType type;
    private final String corporation;

    public GaragePredicate(PredicateType type, String corporation) {
        this.type = type;
        this.corporation = corporation;
    }

    public GaragePredicate(PredicateType type) {
        this(type, null);
        if (type.consumesCorporation())
            throw new IllegalArgumentException("Corporation required for this PredicateType");
    }

    public boolean test(Car car) {
        return type.test(car, corporation);
    }

    public PredicateType getType() {
        return type;
    }

    public String getCorporation() {
        return corporation;
    }

    public interface PredicateType {
        boolean test(Car car, String garage);
        boolean consumesCorporation();
    }

    public enum EnumType implements PredicateType {
        ANY {
            @Override
            public boolean test(Car car, String garage) {
                return true;
            }

            @Override
            public boolean consumesCorporation() {
                return false;
            }
        },
        TICKET_ONLY {
            @Override
            public boolean test(Car car, String corporation) {
                for (Ticket ticket : car.getTickets()) {
                    if (ticket.getType() == Ticket.TicketType.REGULAR) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean consumesCorporation() {
                return false;
            }
        },
        SUBSCRIPTION_ONLY {
            @Override
            public boolean test(Car car, String corporation) {
                for (Ticket ticket : car.getTickets()) {
                    if (ticket.getType() == Ticket.TicketType.SUBSCRIPTION && ticket.isValid(new Date())) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean consumesCorporation() {
                return false;
            }
        },
        RESERVATION_ONLY {
            @Override
            public boolean test(Car car, String corporation) {
                for (Ticket ticket : car.getTickets()) {
                    if (ticket.getType() == Ticket.TicketType.RESERVATION && ticket.isValid(new Date())) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean consumesCorporation() {
                return false;
            }
        },
        CORPORATE_ONLY {
            @Override
            public boolean consumesCorporation() {
                return true;
            }

            @Override
            public boolean test(Car car, String corporation) {
                for (Ticket ticket : car.getTickets()) {
                    if (ticket.getType() == Ticket.TicketType.CORPORATE_PARKING && ticket.isValid(new Date()) && corporation.equals(ticket.getCorporation())) {
                        return true;
                    }
                }
                return false;
            }
        }
    }
}
