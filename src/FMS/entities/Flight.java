package FMS.entities;

import java.time.LocalDate;
import java.util.*;

import FMS.provided.Aircraft;
import FMS.provided.DateTime;
import FMS.provided.Passenger;
import FMS.provided.Staff;


public abstract class Flight implements Comparable<Flight> {

	private DateTime arrival;
	private Set<Staff> crew;
	private DateTime departure;
	private String destination;
	private String flightID;
	private String origin;
	private Set<Passenger> passengers;
	private Aircraft vessel;

	public Flight (String flightID, String origin, String destination, DateTime departure, DateTime arrival){

		setArrival(arrival);
		setDeparture(departure);
		setDestination(destination);
		setFlightID(flightID);
		setOrigin(origin);
	}

	public Flight(Flight f){
		setArrival(new DateTime(f.arrival));
		setDeparture(new DateTime(f.departure));
		setDestination(destination);
		setFlightID(flightID);
		setOrigin(origin);

	}

	private final String ensureNonNullNonEmpty(String str){
		if(str == null || str.isEmpty()){
			throw new IllegalArgumentException();
		}
		return str;
	}

	public final void setFlightID(String flightID){
		this.flightID = ensureNonNullNonEmpty(flightID);
	}
	public final void setDestination(String destination){
		this.destination = ensureNonNullNonEmpty(destination);
	}
	public String getOrigin(){
		return origin;
	}
	public final void setOrigin(String origin){
		this.origin = ensureNonNullNonEmpty(origin);
	}
	public DateTime getDeparture(){
		return new DateTime(departure);
	}
	public final void setDeparture(DateTime departure){
		this.departure = new DateTime(departure);
	}
	public final void setArrival(DateTime arrival){
		this.arrival = new DateTime(arrival);
	}
	public abstract int getBonusMiles();

		public int compareTo(Flight o){
			return this.flightID.compareTo(o.flightID);
	}

	public boolean add(Staff staff){
		return crew.add(staff);
	}
	public boolean add(Passenger passenger){
		return passengers.add(passenger);
	}
	public boolean readyToBoard(){
		return crew.size() > 0;
	}
	public boolean boardingCompleted(){
		if(passengers.size() < 1){
			return false;
		}
		for(Passenger p : passengers){
			if(this.equals(p.getBoarded()) == false){
				return false;
			}
		}
			return true;
	}
	public boolean board(Passenger p){
		if(readyToBoard() == false){
			return false;
		}
		if (passengers.contains(p) && p.getBoarded() == null){
			p.setBoarded(this);
			return true;
		}
		return false;
	}
	public String getFlightId(){
		return flightID;
	}
	public Flight setVessel(Aircraft vessel){
		this.vessel = vessel;
		return this;
	}

	@Override
	public String toString() {
		return String.format(
				"%5s from %3.3S (%s) to %3.3S (%s)" + " with a crew of %d and %d passengers "
						+ "<%s> boarding%scompleted \n%s\n%s",
				flightID, origin, departure, destination, arrival, 
				crew == null ? 0 : crew.size(),
				passengers == null ? 0 : passengers.size(),
				vessel == null ? "no vessel" : vessel.toString(),
				boardingCompleted() ? " " : " not ",
				crew,
				passengers);
	}

}
