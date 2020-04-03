package controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import model.Client;
import model.Habitacio;
import model.Hotel;
import model.Reserva;
import view.View;

public class Main {
	
	static Hotel aquestHotel = new Hotel("Hotel De Tona");
	

	// EJECUTAR LA VIEW
    public static void main(String[] args) throws Exception {
        View f = new View();
		Files a = new Files();
    }
    
    
	// MATCHES DE CAMPOS DE TEXTO
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
	public static boolean matchesSomeChar(String nomHot) {
		if (!nomHot.matches("( ){0,300}") && !nomHot.matches("")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	// HABILITADOR DE EL BOTON DE RESERVAS PARA EL CLIENTE
	public static boolean comprovaClientValid(boolean nomB,boolean cogB,boolean dniB,boolean numNitsB,boolean numPersB){
		if ( nomB && cogB && dniB && numNitsB && numPersB) {
			return true;
		}
		else {
			return false;
		}
	}
	

	// VALIDADOR DE DNI
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
	
	
	// COMPROVADOR DE CLIENTE PARA EVITAR REPETICIONES
	public static boolean comprovaClient(Client newClient) {
		for (Client clientActual : aquestHotel.getClient()) {
			if (clientActual.getDni().equalsIgnoreCase(newClient.getDni())) {
				return true;
			}
		}
		return false;
	}
	
	
	// COMPROVADOR DE LA HABITACION PARA EVITAR REPETICIONES
	public static boolean comprovaHab(Habitacio newHab) {
		for (Habitacio habActual : aquestHotel.getHabs()) {
			if (habActual.getNumRoom() == newHab.getNumRoom()) {				
				return true;
			}
		}
		return false;
	}
	

	// AÑADIR CLIENTES A LA RESERVA
	public static void addClientReserva( JTextField nom, JTextField cog, JTextField dni, JTextField numPersones, JTextField numNits, JCalendar calendari) {
    	Client newClient = new Client(dni.getText(), nom.getText(), cog.getText());
		LocalDate dateIn =  Instant.ofEpochMilli(calendari.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		Reserva newReserva = new Reserva(newClient, Integer.parseInt(numPersones.getText()), dateIn, dateIn.plusDays(Integer.parseInt(numNits.getText())));		
		
		if (newReserva.comprovaHabAdecuada(aquestHotel, 0) || newReserva.comprovaHabAdecuada(aquestHotel, 1)) {
	    	if (Main.comprovaClient(newClient)) {
	    		// CLIENTE ANTIGUO
	    		aquestHotel.getreservesPendents().add(newReserva);
	    		View.addResTable(aquestHotel.getreservesPendents());
	    	}
	    	else {
	    		// CLIENTE NUEVO
	    		Files.omplirFileClie(newClient);
	    		aquestHotel.getClient().add(newClient);
	    		aquestHotel.getreservesPendents().add(newReserva);
	    		View.addResTable(aquestHotel.getreservesPendents());
	    	}
			View.notiResCorrecte();
		}	
		else {
			// NO HAY HAB
			View.notiResInCorrecte();
		} 	
	}
	
	
	// AÑADIR LA HABITACION A LA ARRAYLIST
	public static boolean addHab(JTextField numRegistre, JTextField persRegistre) {
    	Habitacio newHab = new Habitacio(Integer.parseInt(numRegistre.getText()), Integer.parseInt(persRegistre.getText()));
		
    	if (!Main.comprovaHab(newHab)) {
    		aquestHotel.getHabs().add(newHab);
    		Files.omplirFileHabs(newHab);
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
		Files.modificarFileHabs(newHab);
	}


	// CONFIRMAR RESERVAS
	public static void confirmarPreparaReserva(int row, boolean entSort) {
		Hotel.getReservaConf(row);
		View.addResTable(aquestHotel.getreservesPendents());
		Main.recargaResConf(entSort);
	}
	
	
	// ELIMINAR LA RESERVA PENDIENTE
	public static void eliminaResPen(int row) {
		Hotel.eliminaResPen(row);
		View.addResTable(aquestHotel.getreservesPendents());
		View.actSoloRes();
	}
	
	
	// ACTUALIZA LAS RESERVAS -----------------------------------------------------------------------------------------------------------
	public static void recargaResConf(boolean entSort) {
		View.addResTable(aquestHotel.getreservesPendents()); // -------------
		if (entSort) {
			View.addConfTableSortida(aquestHotel.getreservesConfirmades());
		}
		else {
			View.addConfTableEntrada(aquestHotel.getreservesConfirmades());
		}
	}
	


	public static ArrayList<Client> actualitzaLlista(String consultaReserva) {
		ArrayList<Client> clients = new ArrayList<Client>();
		
		for (Client clientActual : aquestHotel.getClient()) {
			if (clientActual.getName().toLowerCase().contains(consultaReserva.toLowerCase()) || clientActual.getSurname().toLowerCase().contains(consultaReserva.toLowerCase()) || clientActual.getDni().toLowerCase().contains(consultaReserva.toLowerCase()) ) {
				clients.add(clientActual);
			}
		}
		return clients;
	}
	public static ArrayList<Reserva> actialitzaLlistaReserves(String dni) {
		ArrayList<Reserva> reserves = new ArrayList<Reserva>();
		
		for (Reserva reservaActual : aquestHotel.getreservesConfirmades()) {
			if (reservaActual.getReservationGuy().getDni().toLowerCase().equalsIgnoreCase(dni)) {
				reserves.add(reservaActual);
			}
		}
		for (Reserva reservaActual : aquestHotel.getreservesPendents()) {
			if (reservaActual.getReservationGuy().getDni().toLowerCase().equalsIgnoreCase(dni)) {
				reserves.add(reservaActual);
			}
		}
		return reserves;
	}
	
	
	
	
	
	public static void eliminaReserva(Reserva res) {
		Hotel.removeReservaConf(res);
	}
	
	
	
	
	
	
    
    
	
    
    
    
    
    
    
}