/**
 * 
 */
package arpg.game.locations;

import arpg.game.*;
import arpg.game.events.*;

/**
 * @author Andrew
 * 
 */
public class Location {
	
	/**
	 * Name of the location
	 */
	public final String name;
	
	/**
	 * The list of possible events
	 */
	public final EventGroup events;
	
	/**
	 * @param name
	 * @param events
	 *            List of events
	 * 
	 */
	public Location (String name, EventGroup events) {
		
		// TODO Auto-generated constructor stub
		
		this.name = name;
		this.events = events;
		
	}
	
	/**
	 * @param name
	 * @param arguments
	 *            Event varargs
	 */
	public Location (String name, Event... arguments) {
		
		this.name = name;
		events = new EventGroup(arguments);
		
	}
	
}
