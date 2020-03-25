package model;

import java.util.ArrayList;

public class Hotel {
	String hotelName;
	ArrayList<Habitacio> habs = new ArrayList<Habitacio>();
	ArrayList<Client> client = new ArrayList<Client>();
	ArrayList<Reserva> reservesPendents = new ArrayList<Reserva>();
	ArrayList<Reserva> reservesConfirmades = new ArrayList<Reserva>();


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
	
	
	public ArrayList<Habitacio> getHabs() {
		return habs;
	}
	public void sethabs(ArrayList<Habitacio> habs) {
		this.habs = habs;
	}
	
	
	public ArrayList<Client> getClient() {
		return client;
	}
	public void setClient(ArrayList<Client> client) {
		this.client = client;
	}
	
	
	public ArrayList<Reserva> getreservesPendents() {
		return reservesPendents;
	}
	public void setreservesPendents(ArrayList<Reserva> reservesPendents) {
		this.reservesPendents = reservesPendents;
	}
	
	
	public ArrayList<Reserva> getreservesConfirmades() {
		return reservesConfirmades;
	}
	public void setreservesConfirmades(ArrayList<Reserva> reservesConfirmades) {
		this.reservesConfirmades = reservesConfirmades;
	}



	
	
	
}
