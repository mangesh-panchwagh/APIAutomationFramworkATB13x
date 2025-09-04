package pojos.responsePOJO.restfulbooker;

import pojos.requestPOJO.restfulbooker.Booking;

/*
{
    "bookingid": 1948,
    "booking": {
        "firstname": "mangesh",
        "lastname": "Panchwagh",
        "totalprice": 3000,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2025-07-22",
            "checkout": "2025-07-27"
        },
        "additionalneeds": "Breakfast"
    }
}
*/

public class BookingResponse {

	private Integer bookingid;
	private Booking booking;
	public Integer getBookingid() {
		return bookingid;
	}
	public void setBookingid(Integer bookingid) {
		this.bookingid = bookingid;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	
}
