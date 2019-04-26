package ca.simplestep.api;

public interface CabBookingService {
    Booking bookRide(String pickUpLocation) throws BookingException;
}
