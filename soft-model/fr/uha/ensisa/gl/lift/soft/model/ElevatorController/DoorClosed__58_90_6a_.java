package fr.uha.ensisa.gl.lift.soft.model.ElevatorController;

import junit.framework.TestCase;
import fr.uha.ensisa.gl.lift.soft.model.AdapterFactory;
import fr.uha.ensisa.gl.lift.soft.model.AdapterInterface;
import fr.uha.ensisa.gl.lift.soft.model.States;

import static fr.uha.ensisa.gl.lift.soft.model.ElevatorController.InstancesDefinition.*;
import static fr.uha.ensisa.gl.lift.soft.model.Enumerations.*;
/*
REQUIREMENTS:
*/
public class DoorClosed__58_90_6a_ extends TestCase {
	
	private AdapterInterface adapter;
	
	public void setUp() throws Exception {
		adapter = AdapterFactory.getForSuite("fr.uha.ensisa.gl.lift.soft.model", "ElevatorController");
	}
	
	public void testDoorClosed__58_90_6a_() throws Exception {
		adapter.ElevatorControllerrequest(ElevatorController.sut, Actor.fb0, Floor._0);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.openDoors);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.none);
		adapter.ElevatorControllerdoorOpened(ElevatorController.sut);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.countdown);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.openDoors);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.none);
		adapter.ElevatorControllertimeout(ElevatorController.sut);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.countdown);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.closeDoors);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.none);
		adapter.ElevatorControllerdoorClosed(ElevatorController.sut);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.cancel);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.closeDoors);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.none);
	}
	
	public void tearDown() throws Exception {
		adapter.closeAdapter();
	}
	
}
