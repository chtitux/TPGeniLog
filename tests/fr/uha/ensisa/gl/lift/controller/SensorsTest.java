package fr.uha.ensisa.gl.lift.controller;

import static org.easymock.EasyMock.*;
import org.junit.Test;
import fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus;


public class SensorsTest extends CabinAtFloorTestsEM {
	@Test
	public void leftFloor() {
		//D'abord on request l'étage 1
		fb1.requestACK();
		motor.goUp();
		replay(fb1);
		replay(motor);
		
		sut.request(fb1, 1);
		
		verify(fb1);
		verify(motor);
		
		//Ensuite on notifie le changement de status du moteur
		sut.motorStatusChanged(motor, MotorStatus.GoingUpward);
		
		//Ensuite on a la notification du décolage
		sut.cabinLeftFloor(fs0, 0);
		
		verify(fb1);
		verify(motor);
	}

	@Test
	public void atFloor() {
		//Pour ne pas réécrire le scénario précédent
		this.leftFloor();
		reset(motor);
		reset(fb1);
		
		//On fait un cabinAtFloor
		cb1.requestServiced();
		fb1.requestServiced();
		motor.stopMove();
		door.openDoors();
		
		replay(cb1);
		replay(fb1);
		replay(motor);
		replay(door);
		
		sut.cabinAtFloor(fs1, 1);
		
		verify(cb1);
		verify(fb1);
		verify(motor);
		verify(door);
	}
}