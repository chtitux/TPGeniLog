package fr_uha_ensisa_ff_gl_lift_soft_model.ElevatorController;

import junit.framework.TestCase;
import fr_uha_ensisa_ff_gl_lift_soft_model.AdapterInterface;
import fr_uha_ensisa_ff_gl_lift_soft_model.AdapterFactory;
import fr_uha_ensisa_ff_gl_lift_soft_model.States;

import static fr_uha_ensisa_ff_gl_lift_soft_model.Enumerations.*;
import static fr_uha_ensisa_ff_gl_lift_soft_model.ElevatorController.InstancesDefinition.*;
/*
REQUIREMENTS:
*/
public class CabinLeftFloor__58_81_d2_ extends TestCase {
	
	private AdapterInterface adapter;
	
	public void setUp() throws Exception {
		adapter = AdapterFactory.getForSuite("fr_uha_ensisa_ff_gl_lift_soft_model", "ElevatorController");
	}
	
	public void testCabinLeftFloor__58_81_d2_() throws Exception {
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
		adapter.ElevatorControllermotorStatusChanged(ElevatorController.sut, MotorStatus.goingUpward);
		adapter.ElevatorControllercabinLeftFloor(ElevatorController.sut, Floor._0);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.requestACK);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.goUp);
		adapter.ElevatorControllercabinLeftFloor(ElevatorController.sut, Floor._0);
		adapter.ActorgetLastSentMessage(Actor.cb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb1, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.cb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.clock, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.door, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb0, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.fb1, Messages.requestACK);
		adapter.ActorgetLastSentMessage(Actor.fb2, Messages.none);
		adapter.ActorgetLastSentMessage(Actor.motor, Messages.goUp);
	}
	
	public void tearDown() throws Exception {
		adapter.closeAdapter();
	}
	
}
