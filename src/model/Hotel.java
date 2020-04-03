package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.Files;

public class Hotel {
	String hotelName;
	static ArrayList<Habitacio> habs = new ArrayList<Habitacio>();
	static ArrayList<Client> client = new ArrayList<Client>();
	static ArrayList<Reserva> reservesPendents = new ArrayList<Reserva>();
	static ArrayList<Reserva> reservesConfirmades = new ArrayList<Reserva>();


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
		Hotel.habs = habs;
	}
	
	
	public ArrayList<Client> getClient() {
		return client;
	}
	public void setClient(ArrayList<Client> client) {
		Hotel.client = client;
	}
	
	
	public ArrayList<Reserva> getreservesPendents() {
		return reservesPendents;
	}
	public void setreservesPendents(ArrayList<Reserva> reservesPendents) {
		Hotel.reservesPendents = reservesPendents;
	}
	
	
	public ArrayList<Reserva> getreservesConfirmades() {
		return reservesConfirmades;
	}
	public void setreservesConfirmades(ArrayList<Reserva> reservesConfirmades) {
		Hotel.reservesConfirmades = reservesConfirmades;
	}


	
	public static void getReservaConf(int row) {
		Hotel.reservesConfirmades.add(reservesPendents.get(row));
		Files.omplirFileRes(Hotel.reservesPendents.get(row), true);
		Files.eliminaFileRes(Hotel.reservesPendents.get(row), false);
		Hotel.reservesPendents.remove(row);
	}
	public static void eliminaResPen(int row) {
		Files.eliminaFileRes(Hotel.reservesPendents.get(row), false);
		Hotel.reservesPendents.remove(row);
	}
	

	
	public static void removeReservaConf(Reserva resActual) {
		if (reservesConfirmades.contains(resActual)) {
			Files.eliminaFileRes(resActual, true);
			reservesConfirmades.remove(resActual);
		}
		if (reservesPendents.contains(resActual)) {
			Files.eliminaFileRes(resActual, false);
			reservesPendents.remove(resActual);
		}
	}
	
	
	// FILES -----------------------------------------------------------------------------------------------------
    static String currentLine="";

	
	public static void omplirHabs(BufferedReader buffReader) {
	    while (true) {
	        try {
	            if (!((currentLine = buffReader.readLine()) != null)) break;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			String[] splitCurrentLine=currentLine.split("_");

	        Habitacio hab = new Habitacio(Integer.parseInt(splitCurrentLine[0]), Integer.parseInt(splitCurrentLine[1]));
	        Hotel.habs.add(hab);
	    }
	}
	
	
	
	public static void omplirClie(BufferedReader buffReader) {
	    while (true) {
	        try {
	            if (!((currentLine = buffReader.readLine()) != null)) break;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			String[] splitCurrentLine=currentLine.split("_");

	        Client clie = new Client(splitCurrentLine[0], splitCurrentLine[1], splitCurrentLine[2]);
	        Hotel.client.add(clie);
	    }
	}
	
	
	
	public static void omplirResP(BufferedReader buffReader) {
    	// DNI, HabRes, NumPers, FirstDate, LastDate
	    while (true) {
	        try {
	            if (!((currentLine = buffReader.readLine()) != null)) break;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			String[] splitCurrentLine=currentLine.split("_");
			String[] splitFirstDate=splitCurrentLine[3].split("-");
			String[] splitLastDate=splitCurrentLine[4].split("-");
			Reserva resP = null;

			for (Client client : Hotel.client) {
				if (splitCurrentLine[0].equalsIgnoreCase(client.getDni())) {
					LocalDate first = LocalDate.of(Integer.parseInt(splitFirstDate[0]), Integer.parseInt(splitFirstDate[1]), Integer.parseInt(splitFirstDate[2]));
					LocalDate last = LocalDate.of(Integer.parseInt(splitLastDate[0]), Integer.parseInt(splitLastDate[1]), Integer.parseInt(splitLastDate[2]));
					
			        resP = new Reserva(client, Integer.parseInt(splitCurrentLine[2]), first, last);
			        for (Habitacio habit : habs) {
			        	if (splitCurrentLine[1].equals(String.valueOf(habit.getNumRoom()))) {
					        resP.sethab(habit);
			        	}
			        }
				}
			}
	        Hotel.reservesPendents.add(resP);
	    }
	}
	

	public static void omplirResC(BufferedReader buffReader) {
    	// DNI, HabRes, NumPers, FirstDate, LastDate
	    while (true) {
	        try {
	            if (!((currentLine = buffReader.readLine()) != null)) break;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			String[] splitCurrentLine=currentLine.split("_");
			String[] splitFirstDate=splitCurrentLine[3].split("-");
			String[] splitLastDate=splitCurrentLine[4].split("-");
			Reserva resP = null;

			for (Client client : Hotel.client) {
				if (splitCurrentLine[0].equalsIgnoreCase(client.getDni())) {
					LocalDate first = LocalDate.of(Integer.parseInt(splitFirstDate[0]), Integer.parseInt(splitFirstDate[1]), Integer.parseInt(splitFirstDate[2]));
					LocalDate last = LocalDate.of(Integer.parseInt(splitLastDate[0]), Integer.parseInt(splitLastDate[1]), Integer.parseInt(splitLastDate[2]));
					
			        resP = new Reserva(client, Integer.parseInt(splitCurrentLine[2]), first, last);
			        for (Habitacio habit : habs) {
			        	if (splitCurrentLine[1].equals(String.valueOf(habit.getNumRoom()))) {
					        resP.sethab(habit);
			        	}
			        }
				}
			}
	        Hotel.reservesConfirmades.add(resP);
	    }
	}
	
	
	
	
	
	
	


}
