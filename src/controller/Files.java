package controller;

import java.io.*;

import model.Client;
import model.Habitacio;
import model.Hotel;
import model.Reserva;

import static java.lang.System.*;

public class Files {

	static File Habitacio,Client,ReservaP,ReservaC,directoriData;
    static FileWriter fWriter;
    static BufferedWriter buffWriter;
    static FileReader fReader;
    static BufferedReader buffReader;

    public Files(){
        creaDirectori();
        crearFitxers();
        
        omplirArrayHab();
        omplirArrayClie();
        omplirArrayResP();
        omplirArrayResC();
    }

    
    
    private void creaDirectori() {
        directoriData = new File("src"+ File.separator +"data");
        if (directoriData.mkdir()) {
            out.println("Carpeta creada...");
        } else {
            err.println("No s'ha creat el directori...");
        }
    }
    
    
    
    private void crearFitxers() {
    	Habitacio = new File(directoriData.getAbsolutePath() + File.separator + "Habitacio.txt");
    	Client = new File(directoriData.getAbsolutePath() + File.separator + "Client.txt");
    	ReservaP = new File(directoriData.getAbsolutePath() + File.separator + "ReservaP.txt");
    	ReservaC = new File(directoriData.getAbsolutePath() + File.separator + "ReservaC.txt");

        if (Habitacio.exists() && Client.exists() && ReservaP.exists() && ReservaC.exists()) {
            out.println("Fitxer ja existent...");
        }else{
            try {
                if(Habitacio.createNewFile() && Client.createNewFile() && ReservaP.createNewFile() && ReservaC.createNewFile()) {
                    out.println("Fitxers creats...");
                }else {
                    out.println("Error al crear el fitxer...");
                }
            } catch (IOException e) {
                out.println("Error al crear el fitxer: "+e);
            }
        }
    }
    
    
    
    // HABS
    public static void omplirFileHabs(Habitacio hab) {
    	String stHab = hab.getNumRoom()+"_"+hab.getNumPers();
        try {
            fWriter = new FileWriter(Habitacio,false);
            buffWriter = new BufferedWriter(fWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffWriter.write(stHab);
            buffWriter.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	    try {
	        buffWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    

    
    public static void modificarFileHabs(Habitacio hab) {
        File tempFile = new File(directoriData.getAbsolutePath() + File.separator + "tmp.txt");
    	String stHab = hab.getNumRoom()+"_"+hab.getNumPers();
        try {
            fWriter = new FileWriter(tempFile,false);
            buffWriter = new BufferedWriter(fWriter);
            
            fReader = new FileReader(Habitacio);
            buffReader = new BufferedReader(fReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
	    String currentLine="";
	    while (true) {
	        try {
	            if (!((currentLine = buffReader.readLine()) != null)) break;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			String[] splitCurrentLine=currentLine.split("_");
			if (splitCurrentLine[0].equals(String.valueOf(hab.getNumRoom()))) {
		        try {
		            buffWriter.write(stHab);
		            buffWriter.write(System.lineSeparator());
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
			else {
		        try {
		            buffWriter.write(currentLine);
		            buffWriter.write(System.lineSeparator());
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
	    }
	    try {
	        buffReader.close();
	        buffWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }  
	    
	    Habitacio.delete();
	    tempFile.renameTo(Habitacio);
    }
    
   
    
    private void omplirArrayHab() {
        try {
            fReader = new FileReader(Habitacio);
            buffReader = new BufferedReader(fReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Hotel.omplirHabs(buffReader);
	    try {
	        buffReader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }   
    

    
    // CLIENTS
    public static void omplirFileClie(Client client) {
    	String stClie = client.getDni()+"_"+client.getName()+"_"+client.getSurname();
        try {
            fWriter = new FileWriter(Client,false);
            buffWriter = new BufferedWriter(fWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffWriter.write(stClie);
            buffWriter.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
  	    try {
	        buffWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

    
    
    private void omplirArrayClie() {
        try {
            fReader = new FileReader(Client);
            buffReader = new BufferedReader(fReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Hotel.omplirClie(buffReader);
	    try {
	        buffReader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
    
    
    // RESERVES
    public static void omplirFileRes(Reserva res, boolean confOno) {
    	String stRes = res.getReservationGuy().getDni()+"_"+res.gethab().getNumRoom()+"_"+res.getNumPeople()+"_"+res.getFirstDate()+"_"+res.getLastDate();
        try {
            if (!confOno) {
                fWriter = new FileWriter(ReservaP,false);
        	}
            else {
                fWriter = new FileWriter(ReservaC,false);
        	}
            buffWriter = new BufferedWriter(fWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffWriter.write(stRes);
            buffWriter.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	    try {
	        buffWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    

    
    public static void eliminaFileRes(Reserva res, boolean confOno) {
        File tempFile = new File(directoriData.getAbsolutePath() + File.separator + "tmp.txt");
        try {
            fWriter = new FileWriter(tempFile,false);
            buffWriter = new BufferedWriter(fWriter);
            
            if (!confOno) {
                fReader = new FileReader(ReservaP);
            }
            else {
                fReader = new FileReader(ReservaC);
            }
            buffReader = new BufferedReader(fReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
	    String currentLine="";
	    while (true) {
	        try {
	            if (!((currentLine = buffReader.readLine()) != null)) break;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			String[] splitCurrentLine=currentLine.split("_");
			if (splitCurrentLine[0].equalsIgnoreCase(res.getReservationGuy().getDni()) && 
					splitCurrentLine[1].equals(String.valueOf(res.gethab().getNumRoom())) && 
					splitCurrentLine[2].equals(String.valueOf(res.getNumPeople())) && 
					splitCurrentLine[3].equals(String.valueOf(res.getFirstDate())) && 
					splitCurrentLine[4].equals(String.valueOf(res.getLastDate()))) {
				System.out.println("Res Pend eliminada...");
			}
			else {
		        try {
		            buffWriter.write(currentLine);
		            buffWriter.write(System.lineSeparator());
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
	    }
	    try {
	        buffReader.close();
	        buffWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
        if (!confOno) {
    	    ReservaP.delete();
    	    tempFile.renameTo(ReservaP);
    	}
        else {
    	    ReservaC.delete();
    	    tempFile.renameTo(ReservaC);
    	}

    }
    
   
    
    private void omplirArrayResP() {
        try {
            fReader = new FileReader(ReservaP);
            buffReader = new BufferedReader(fReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Hotel.omplirResP(buffReader);
	    try {
	        buffReader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
    private void omplirArrayResC() {
        try {
            fReader = new FileReader(ReservaC);
            buffReader = new BufferedReader(fReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Hotel.omplirResC(buffReader);
	    try {
	        buffReader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
    
    
    
    
    
    



}