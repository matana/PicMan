package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.exception;

/**
 * Created by matana on 15.12.14.
 */
public class LocationNotAvailableException extends Throwable {

    public LocationNotAvailableException() {
        super("location is not available! See if location providers are enabled...");
    }

    public LocationNotAvailableException(String detailMessage) {
        super(detailMessage);
    }

    public LocationNotAvailableException(String detailMessage, Throwable cause) {
        super(detailMessage, cause);
    }

}
