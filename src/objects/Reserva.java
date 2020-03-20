package objects;


public class Reserva {
	Client reservationGuy;
	int numPeople, numNights;
	String date;
	
	// -----------------------------------------------------------------------

	public Reserva(Client reservationGuy, int numPeople, int numNights, String date) {
		this.reservationGuy = reservationGuy;
		this.numPeople = numPeople;
		this.numNights = numNights;
		this.date = date;
	}
	
	// -----------------------------------------------------------------------

	public Client getReservationGuy() {
		return reservationGuy;
	}

	public void setReservationGuy(Client reservationGuy) {
		this.reservationGuy = reservationGuy;
	}

	public int getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	public int getNumNights() {
		return numNights;
	}

	public void setNumNights(int numNights) {
		this.numNights = numNights;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	

	
	
}
