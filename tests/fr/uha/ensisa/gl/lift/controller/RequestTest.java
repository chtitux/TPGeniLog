package fr.uha.ensisa.gl.lift.controller;

import static org.easymock.EasyMock.*;

import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus;


public class RequestTest extends CabinAtFloorTestsEM {
	@Test
	public void request0() {
		this.door.openDoors();
		replay(this.door);
		this.sut.request(cb0, 0);
		verify(this.door);
	}
	
	@Test
	public void request0OpenClose() {
		this.door.openDoors();
		replay(this.door);
		this.sut.request(cb0, 0);
		verify(this.door);
		reset(this.door);
		
		replay(this.door);
		this.sut.doorOpened(this.door);
		verify(this.door);
		reset(this.door);
		
		this.door.closeDoors();
		replay(this.door);
		this.sut.timeout(timer);
		verify(this.door);
	}
	
	@Test
	public void request1From0OpenCloseUpOpen() {
		//Demande d'étage 1 via le boutton de la cabine
		cb1.requestACK();
		motor.goUp();
		replay(cb1);
		replay(motor);
		
		sut.request(cb1, 1);
		
		verify(cb1);
		verify(motor);
		
		//Ensuite on notifie le changement de status du moteur
		sut.motorStatusChanged(motor, MotorStatus.GoingUpward);
		
		//Ensuite on a la notification du décolage
		sut.cabinLeftFloor(fs0, 0);
		
		verify(cb1);
		verify(motor);
		
		//On fait un cabinAtFloor
		reset(motor);
		reset(cb1);
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
		
		//On notifie l'ouverture des portes
		timer.countdown(5000);
		replay(timer);
		
		sut.doorOpened(door);
		
		verify(timer);
		reset(timer);
		
		//On notifie le timeout
		reset(door);
		door.closeDoors();
		replay(door);
		
		sut.timeout(timer);
		
		verify(door);
		
		//On notifie la fermerture des portes
		reset(motor);
		reset(timer);
		timer.cancel();
		replay(timer);
		
		sut.doorClosed(door);
		
		verify(timer);
	}
}
