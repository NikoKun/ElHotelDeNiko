package controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import model.Client;
import model.Habitacio;
import model.Hotel;
import model.Reserva;
import view.View;

public class Main {
	
	static Hotel aquestHotel = new Hotel("Hotel De Tona");
	
//	static ArrayList<Client> clients = new ArrayList<Client>();
//	static ArrayList<Habitacio> habitacions = new ArrayList<Habitacio>();
//	static ArrayList<Reserva> reservesPendents = new ArrayList<Reserva>();
//	static ArrayList<Reserva> reservesConfirmades = new ArrayList<Reserva>();
	
	
	// Ejecutar la pestaña
    public static void main(String[] args) throws Exception {
        View f = new View();
    }

    
	// Matches en los campos de CLIENTES
	public static boolean matchesOnlyChar(String contingut) {
		if (contingut.matches("^[ A-Za-zñÑáàéíóòçúÁÀÉÍÓÒÚ]+$")) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean matchesOnlyNumbers40(String contingut) {
		if (contingut.matches("[1-3]+[0-9]?") || contingut.matches("[1-9]")) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean matchesOnlyNumbers6(String contingut) {
		if (contingut.matches("[1-6]")) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean matchesOnlyNumbers7(String numRegistre) {
		if (Integer.parseInt(numRegistre) > 0 && Integer.parseInt(numRegistre) < 7) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean matchesPlus1(String numHab) {
		if (Integer.parseInt(numHab) > 0 && Integer.parseInt(numHab) < 9999999) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
	// Comprova booleans para habilitar el boton de reserva
	public static boolean comprovaClientValid(boolean nomB,boolean cogB,boolean dniB,boolean numNitsB,boolean numPersB){
		if ( nomB && cogB && dniB && numNitsB && numPersB) {
			return true;
		}
		else {
			return false;
		}
	}
	

	// Añadir la fecha en string a la tabla de reservas pendientes MODIFICAR --------------------------------------------------------------
	public static String dataString(JCalendar calendari) {
		long dataNano = calendari.getDate().getTime();
		LocalDate dataLocal = Instant.ofEpochMilli(dataNano).atZone(ZoneId.systemDefault()).toLocalDate();
		String dataString = dataLocal.getDayOfMonth()+"-"+dataLocal.getMonthValue()+"-"+dataLocal.getYear();
		return dataString;
	}
	
	
	// Validar DNI
	public static boolean validar(String dni) {
        boolean correcte = false;
        int i = 0;
        int caracterASCII = 0;
        char letra = ' ';
        int miDNI = 0;
        int resto = 0;
        char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X','B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
 
 
        if(dni.length() == 9 && Character.isLetter(dni.charAt(8))) {
 
            do {
                caracterASCII = dni.codePointAt(i);
                correcte = (caracterASCII > 47 && caracterASCII < 58);
                i++;
            } 
            while(i < dni.length() - 1 && correcte);     
        }
 
        if(correcte) {
            letra = Character.toUpperCase(dni.charAt(8));
            miDNI = Integer.parseInt(dni.substring(0,8));
            resto = miDNI % 23;
            correcte = (letra == asignacionLetra[resto]);
        }
 
        return correcte;
    }
	
	
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// Hace que el cliente no se repita al añadirlo a la array
	public static boolean comprovaClient(Client newClient) {
		for (Client clientActual : aquestHotel.getClient()) {
			if (clientActual.getDni().equalsIgnoreCase(newClient.getDni())) {
				return true;
			}
		}
		return false;
	}
	
	
	
	// Hace que la habitación no se repita al añadirlo a la array
	public static boolean comprovaHab(Habitacio newHab) {
		for (Habitacio habActual : aquestHotel.getHabs()) {
			if (habActual.getNumRoom() == newHab.getNumRoom()) {				
				return true;
			}
		}
		return false;
	}
	
	
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	// Añade el cliente a la reserva
	public static void addClientReserva( JTextField nom, JTextField cog, JTextField dni, JTextField numPersones, JTextField numNits, JCalendar calendari) {
    	Client newClient = new Client(dni.getText(), nom.getText(), cog.getText());
		LocalDate dateIn =  Instant.ofEpochMilli(calendari.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		Reserva newReserva = new Reserva(newClient, Integer.parseInt(numPersones.getText()), dateIn, dateIn.plusDays(Integer.parseInt(numNits.getText())));		
		
		if (newReserva.comprovaHabAdecuada(aquestHotel, 0) || newReserva.comprovaHabAdecuada(aquestHotel, 1)) {
			// Hab adecuada
			System.out.println("if");
	    	if (Main.comprovaClient(newClient)) {
	    		aquestHotel.getreservesPendents().add(newReserva);
	    		View.addResTable(aquestHotel.getreservesPendents(), newClient);
	    	}
	    	else {    		
	    		aquestHotel.getClient().add(newClient);
	    		aquestHotel.getreservesPendents().add(newReserva);
	    		View.addResTable(aquestHotel.getreservesPendents(), newClient);
	    	}
			View.notiResCorrecte();
		}	
		else {
			// No hay hab
			View.notiResInCorrecte();
		}
		
		
//		jtres.rowAtPoint(e.getPoint())
 	
	}
	
	
	// Añade la habitacion 
	public static boolean addHab(JTextField numRegistre, JTextField persRegistre) {
    	Habitacio newHab = new Habitacio(Integer.parseInt(numRegistre.getText()), Integer.parseInt(persRegistre.getText()));
		
    	if (!Main.comprovaHab(newHab)) {
    		aquestHotel.getHabs().add(newHab);
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	public static void modHab(JTextField numRegistre, JTextField persRegistre) {
    	Habitacio newHab = new Habitacio(Integer.parseInt(numRegistre.getText()), Integer.parseInt(persRegistre.getText()));
		for (Habitacio habActual : aquestHotel.getHabs()) {
			if (habActual.getNumRoom() == newHab.getNumRoom()) {				
				habActual.setNumPers(newHab.getNumPers());
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	


	
	
	
	
	
	
	
	
	
	
	
	
	
    
    
	
    
    
    
    
    
    
}