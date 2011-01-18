package fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController;

import junit.framework.TestCase;
import fr.uha.ensisa.gl.lift.controller.generatedTests.AdapterInterface;
import fr.uha.ensisa.gl.lift.controller.generatedTests.AdapterFactory;
import fr.uha.ensisa.gl.lift.controller.generatedTests.States;

import static fr.uha.ensisa.gl.lift.controller.generatedTests.Enumerations.*;
import static fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.*;
/*
REQUIREMENTS:
*/
public class MotorStatusChanged__58_da_84_ extends TestCase {
	
	private AdapterInterface adapter;
	
	public void setUp() throws Exception {
		adapter = AdapterFactory.getForSuite("fr_uha_ensisa_ff_gl_lift_soft_model", "ElevatorController");
	}
	
	public void testMotorStatusChanged__58_da_84_() throws Exception {
		adapter.ElevatorControllerrequest(ElevatorController.sut, Actor.cb1, Floor._1);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.requestACK);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.goUp);
		adapter.ElevatorControllerrequest(ElevatorController.sut, Actor.fb0, Floor._0);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.requestACK);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.openDoors);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.goUp);
		adapter.ElevatorControllermotorStatusChanged(ElevatorController.sut, MotorStatus.goingUpward);
		adapter.ElevatorControllercabinLeftFloor(ElevatorController.sut, Floor._0);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.requestACK);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.openDoors);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.stopMove);
		adapter.ElevatorControllermotorStatusChanged(ElevatorController.sut, MotorStatus.stopped);
	}
	
	public void tearDown() throws Exception {
		adapter.closeAdapter();
	}
	
}
