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
public class DoorOpened__58_97_f6_ extends TestCase {
	
	private AdapterInterface adapter;
	
	public void setUp() throws Exception {
		adapter = AdapterFactory.getForSuite("fr.uha.ensisa.gl.lift.soft.model", "ElevatorController");
	}
	
	public void testDoorOpened__58_97_f6_() throws Exception {
		adapter.ElevatorControllerrequest(ElevatorController.sut, Actor.fb1, Floor._1);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.requestACK);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.goUp);
		adapter.ElevatorControllerrequest(ElevatorController.sut, Actor.fb0, Floor._0);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.openDoors);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.requestACK);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.goUp);
		adapter.ElevatorControllermotorStatusChanged(ElevatorController.sut, MotorStatus.goingUpward);
		adapter.ElevatorControllercabinLeftFloor(ElevatorController.sut, Floor._0);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.openDoors);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.requestACK);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.stopMove);
		adapter.ElevatorControllerdoorOpened(ElevatorController.sut);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.openDoors);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.requestACK);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.stopMove);
	}
	
	public void tearDown() throws Exception {
		adapter.closeAdapter();
	}
	
}
