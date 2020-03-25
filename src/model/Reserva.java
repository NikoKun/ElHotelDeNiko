package model;

import java.time.LocalDate;

public class Reserva {
	Client reservationGuy;
	Habitacio hab;
	int numPeople;
	LocalDate fitstDate, lastDate;
	
	// -----------------------------------------------------------------------
	
	public Reserva(Client reservationGuy, int numPeople, LocalDate fitstDate, LocalDate lastDate) {
		this.reservationGuy = reservationGuy;
		this.numPeople = numPeople;
		this.fitstDate = fitstDate;
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

	public LocalDate getFitstDate() {
		return fitstDate;
	}

	public void setFitstDate(LocalDate fitstDate) {
		this.fitstDate = fitstDate;
	}

	public LocalDate getLastDate() {
		return lastDate;
	}

	public void setLastDate(LocalDate lastDate) {
		this.lastDate = lastDate;
	}
	


	
	public boolean comprovaHabAdecuada(Hotel aquestHotel, int suma) {
		System.out.println(aquestHotel.getHabs().size());
		for (Habitacio habActual : aquestHotel.getHabs()) {
			if (habActual.getNumPers() == (this.getNumPeople()+suma)) {	// Coincide el numero de personas
				for (Reserva resActual : aquestHotel.getreservesPendents()) {
					if (resActual.gethab().getNumRoom() == habActual.getNumRoom() && !(
							(this.getFitstDate().isAfter(resActual.getFitstDate().minusDays(1)) && this.getFitstDate().isBefore(resActual.getLastDate())) ||
							(this.getLastDate().isAfter(resActual.getFitstDate().minusDays(1)) && this.getLastDate().isBefore(resActual.getLastDate())) ||
							(this.getFitstDate().isBefore(resActual.getFitstDate().minusDays(1)) && this.getLastDate().isAfter(resActual.getLastDate()))
							))  {
						this.sethab(resActual.gethab());
						System.out.println("1");
						return true;
					}
				}
				for (Reserva resConfActual : aquestHotel.getreservesConfirmades()) {
					if (resConfActual.gethab().getNumRoom() == habActual.getNumRoom() && !(
							(this.getFitstDate().isAfter(resConfActual.getFitstDate().minusDays(1)) && this.getFitstDate().isBefore(resConfActual.getLastDate())) ||
							(this.getLastDate().isAfter(resConfActual.getFitstDate().minusDays(1)) && this.getLastDate().isBefore(resConfActual.getLastDate())) ||
							(this.getFitstDate().isBefore(resConfActual.getFitstDate().minusDays(1)) && this.getLastDate().isAfter(resConfActual.getLastDate()))
							))  {
						this.sethab(resConfActual.gethab());
						System.out.println("2");
						return true;
					}
				}
				System.out.println("3");
				this.sethab(habActual);
				return true;
			}
		}
		return false;
	}


	
	
}
