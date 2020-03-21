package app;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import com.toedter.calendar.JCalendar;

import objects.Client;

public class Functions {

	
	
	
	
	
	
	
	
	
	
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
	
	
	
	public static boolean comprovaClientValid(boolean nomB,boolean cogB,boolean dniB,boolean numNitsB,boolean numPersB){
		if ( nomB && cogB && dniB && numNitsB && numPersB) {
			return true;
		}
		else {
			return false;
		}
	}
	

	
	public static String dataString(JCalendar calendari) {
		long dataNano = calendari.getDate().getTime();
		LocalDate dataLocal = Instant.ofEpochMilli(dataNano).atZone(ZoneId.systemDefault()).toLocalDate();
		String dataString = dataLocal.getDayOfMonth()+"-"+dataLocal.getMonthValue()+"-"+dataLocal.getYear();
		return dataString;
	}
	
	
	
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
	
	
	public static boolean comprovaClient(String dni, ArrayList<Client> clients) {
		for (Client clientActual : clients) {
			if (clientActual.getDni().equalsIgnoreCase(dni)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
}
