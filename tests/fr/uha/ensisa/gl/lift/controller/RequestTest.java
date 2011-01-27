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
	
	@Test
	public void request0To2Then2To0Then0To3OpenCloseUpOpen() {
		
		//Demande d'étage 2 via le boutton de la cabine
		cb2.requestACK();
		motor.goUp();
		replay(cb2);
		replay(motor);
		
		sut.request(cb2, 2);
		
		verify(cb2);
		verify(motor);
		
		//Ensuite on notifie le changement de status du moteur
		sut.motorStatusChanged(motor, MotorStatus.GoingUpward);
		
		//Ensuite on a la notification du décolage
		sut.cabinLeftFloor(fs0, 0);
		
		verify(cb2);
		verify(motor);
		
		//On fait un cabinAtFloor
		reset(motor);
		reset(cb2);
		cb2.requestServiced();
		fb2.requestServiced();
		motor.stopMove();
		door.openDoors();
		
		replay(cb2);
		replay(fb2);
		replay(motor);
		replay(door);
		
		sut.cabinAtFloor(fs2, 2);
		
		verify(cb2);
		verify(fb2);
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
		
		
		// On fait descendre l'ascenceur
		reset(motor);
		reset(door);
		reset(timer);
		reset(cb0);
		reset(cb1);
		reset(cb2);
		
		//Demande d'étage 2->1 via le boutton de la cabine
		cb1.requestACK();
		motor.goDown();
		replay(cb1);
		replay(motor);
		
		sut.request(cb1, 1);
		
		verify(cb1);
		verify(motor);
		
		//Ensuite on notifie le changement de status du moteur
		sut.motorStatusChanged(motor, MotorStatus.GoingDownward);
		
		//Ensuite on a la notification du décolage
		sut.cabinLeftFloor(fs2, 2);
		
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

		// On fait remonter l'ascenceur vers l'etage 1->3
		reset(motor);
		reset(door);
		reset(timer);
		reset(cb0);
		reset(cb1);
		reset(cb2);
		reset(cb3);
		
		//Demande d'étage 3 via le boutton de la cabine
		cb3.requestACK();
		motor.goUp();
		replay(cb3);
		replay(motor);
		
		sut.request(cb3, 3);
		
		verify(cb3);
		verify(motor);
		
		//Ensuite on notifie le changement de status du moteur
		sut.motorStatusChanged(motor, MotorStatus.GoingUpward);
		
		//Ensuite on a la notification du décolage
		sut.cabinLeftFloor(fs1, 1);
		
		verify(cb3);
		verify(motor);
		
		//On fait un cabinAtFloor
		reset(motor);
		reset(cb3);
		cb3.requestServiced();
		fb3.requestServiced();
		motor.stopMove();
		door.openDoors();
		
		replay(cb3);
		replay(fb3);
		replay(motor);
		replay(door);
		
		sut.cabinAtFloor(fs3, 3);
		
		verify(cb3);
		verify(fb3);
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
		
		
		// Descente vers 3->0
		reset(motor);
		reset(door);
		reset(timer);
		reset(cb0);
		reset(cb1);
		reset(cb2);
		reset(cb3);
		
		//Demande d'étage 0 via le boutton de la cabine
		cb0.requestACK();
		motor.goDown();
		replay(cb0);
		replay(motor);
		
		sut.request(cb0, 0);
		
		verify(cb0);
		verify(motor);
		
		//Ensuite on notifie le changement de status du moteur
		sut.motorStatusChanged(motor, MotorStatus.GoingDownward);
		
		//Ensuite on a la notification du décolage
		sut.cabinLeftFloor(fs3, 3);
		
		verify(cb0);
		verify(motor);
		
		//On fait un cabinAtFloor à 1
		reset(motor);
		sut.cabinAtFloor(fs1, 1);
		sut.cabinLeftFloor(fs1, 1);
		replay(motor);
		verify(motor);
		
		//On fait un cabinAtFloor à 0
		reset(motor);
		reset(cb0);
		cb0.requestServiced();
		fb0.requestServiced();
		motor.stopMove();
		door.openDoors();
		
		replay(cb0);
		replay(fb0);
		replay(motor);
		replay(door);
		
		sut.cabinAtFloor(fs0, 0);
		
		verify(cb0);
		verify(fb0);
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
