package fr.uha.ensisa.gl.lift.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.ElevatorController;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;
import fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus;


public class AccessorsTest {
	@Test public void doorAccessor() {
		ElevatorControllerImpl sut = new ElevatorControllerImpl();
		Door d = new Door() {

			@Override
			public void openDoors() {
			}

			@Override
			public void closeDoors() {
			}};
		sut.setDoor(d);
		assertEquals(d, sut.getDoor());
	}
}
