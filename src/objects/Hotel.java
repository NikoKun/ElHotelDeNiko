package objects;

import java.util.ArrayList;

public class Hotel {
	String hotelName;
	ArrayList<Integer> rooms = new ArrayList<Integer>();
	ArrayList<Client> client = new ArrayList<Client>();
	ArrayList<Reserva> pendingReservation = new ArrayList<Reserva>();
	ArrayList<Reserva> confirmedReservation = new ArrayList<Reserva>();


	// -----------------------------------------------------------------------
	
	public Hotel(String hotelName) {
		this.hotelName = hotelName;
	}

	// -----------------------------------------------------------------------

	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}


	public ArrayList<Integer> getRooms() {
		return rooms;
	}
	public void setRooms(ArrayList<Integer> rooms) {
		this.rooms = rooms;
	}
	
	
	
	
}
