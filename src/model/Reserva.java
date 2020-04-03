package model;

import java.time.LocalDate;

import controller.Files;

public class Reserva {
	Client reservationGuy;
	Habitacio hab;
	int numPeople;
	LocalDate firstDate, lastDate;
	
	// -----------------------------------------------------------------------
	
	public Reserva(Client reservationGuy, int numPeople, LocalDate fitstDate, LocalDate lastDate) {
		this.reservationGuy = reservationGuy;
		this.numPeople = numPeople;
		this.firstDate = fitstDate;
		this.lastDate = lastDate;
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

	public Habitacio gethab() {
		return hab;
	}

	public void sethab(Habitacio hab) {
		this.hab = hab;
	}

	public LocalDate getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(LocalDate fitstDate) {
		this.firstDate = fitstDate;
	}

	public LocalDate getLastDate() {
		return lastDate;
	}

	public void setLastDate(LocalDate lastDate) {
		this.lastDate = lastDate;
	}
	
	
	@Override
	public String toString() {
		return "N.H."+this.hab.numRoom+" DateIn: "+this.firstDate;
	}
	
	
	
	public String firstDateToString() {
		return 	this.getFirstDate().getDayOfMonth()+"-"+this.getFirstDate().getMonthValue()+"-"+this.getFirstDate().getYear();
	}
	public String lastDateToString() {
		return 	this.getLastDate().getDayOfMonth()+"-"+this.getLastDate().getMonthValue()+"-"+this.getLastDate().getYear();
	}

	
	

	public boolean comprovaHabAdecuada(Hotel aquestHotel, int suma) {
		boolean pend = false;
		boolean pend2 = false;
		
		Reserva room1 = null;

		boolean conf = false;
		boolean conf2 = false;

		for (Habitacio habActual : aquestHotel.getHabs()) {
			if (habActual.getNumPers() == (this.getNumPeople()+suma)) {	// Coincide el numero de personas
				for (Reserva resActual : aquestHotel.getreservesPendents()) {
					if (resActual.gethab().getNumRoom() == habActual.getNumRoom()) {
						if (!((this.getFirstDate().isAfter(resActual.getFirstDate().minusDays(1)) && this.getFirstDate().isBefore(resActual.getLastDate())) ||
								(this.getLastDate().isAfter(resActual.getFirstDate().minusDays(1)) && this.getLastDate().isBefore(resActual.getLastDate())) ||
								(this.getFirstDate().isBefore(resActual.getFirstDate().minusDays(1)) && this.getLastDate().isAfter(resActual.getLastDate()))
								))  {	
							room1 = resActual;
							pend2 = true;
						}
						else {
							return false;
						}
					}
				}
				for (Reserva resConfActual : aquestHotel.getreservesConfirmades()) {
					if (resConfActual.gethab().getNumRoom() == habActual.getNumRoom()) {
						conf = true;
						if (!((this.getFirstDate().isAfter(resConfActual.getFirstDate().minusDays(1)) && this.getFirstDate().isBefore(resConfActual.getLastDate())) ||
								(this.getLastDate().isAfter(resConfActual.getFirstDate().minusDays(1)) && this.getLastDate().isBefore(resConfActual.getLastDate())) ||
								(this.getFirstDate().isBefore(resConfActual.getFirstDate().minusDays(1)) && this.getLastDate().isAfter(resConfActual.getLastDate()))
								))  {
							room1 = resConfActual;
							conf2 = true;
						}
						else {
							return false;
						}
					}					
				}	
				if (pend2) {
					this.reservaCorrecta(room1.gethab());
					return true;
				}
				else if (conf2) {
					this.reservaCorrecta(room1.gethab());
					return true;
				}
				
				
				if (aquestHotel.getreservesPendents().isEmpty() && aquestHotel.getreservesConfirmades().isEmpty()) {
					this.sethab(habActual);
					Files.omplirFileRes(this, false);
					return true;
				}
				if (pend || conf) {
					return false;
				}

			}
		}
		return false;
	}

	
	
	public void reservaCorrecta(Habitacio hab){
		this.sethab(hab);
		Files.omplirFileRes(this, false);
	}


	
	
}
