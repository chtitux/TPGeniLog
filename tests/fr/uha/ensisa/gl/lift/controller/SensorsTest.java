package fr.uha.ensisa.gl.lift.controller;

import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;

public class SensorsTest {
	public ElevatorControllerImpl sut;
	public Door door = createMock(Door.class);
	public Timer timer = createMock(Timer.class);
	public Motor motor = createMock(Motor.class);
	
	// Étage 0
	public FloorSensor fs0 = createMock(FloorSensor.class);
	// Étage 1
	public Button cb1 = createMock(Button.class);
	public Button fb1 = createMock(Button.class);
	public FloorSensor fs1 = createMock(FloorSensor.class);
	
	@Before
	public void createSut() {
		this.sut = new ElevatorControllerImpl();
		this.sut.setDoor(this.door);
		this.sut.setTimer(timer);
		this.sut.setMotor(this.motor);
		// Etage 0
		this.sut.setFloorSensor(0, fs0);
		// Etage 1
		this.sut.setCabinButton(1, cb1);
		this.sut.setFloorButton(1, fb1);
		this.sut.setFloorSensor(1, fs1);
		
		reset(door);
		reset(timer);
		reset(motor);
		reset(fs0);
		reset(cb1);
		reset(fb1);
		reset(fs1);
	}

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