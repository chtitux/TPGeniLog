package fr.uha.ensisa.gl.lift.controller.generatedTests;

import static org.easymock.EasyMock.*;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;
import fr.uha.ensisa.gl.lift.controller.generatedTests.Enumerations.Messages;
import fr.uha.ensisa.gl.lift.controller.generatedTests.Enumerations.MotorStatus;
import fr.uha.ensisa.gl.lift.controller.generatedTests.InstancesDeclaration.Actor;
import fr.uha.ensisa.gl.lift.controller.generatedTests.InstancesDeclaration.ElevatorController;
import fr.uha.ensisa.gl.lift.controller.generatedTests.InstancesDeclaration.Floor;
import fr.uha.ensisa.gl.lift.controller.ElevatorControllerImpl;

public class AdapterImpl implements AdapterInterface {
	private ElevatorControllerImpl sut;
	private Door door;
	private Timer timer;
	private Motor motor;
	
	private Button cb0;
	private Button fb0;
	private FloorSensor fs0;
	
	private Button cb1;
	private Button fb1;
	private FloorSensor fs1;
	
	public AdapterImpl() {
		this.door = createMock(Door.class);
		this.timer = createMock(Timer.class);
		this.motor = createMock(Motor.class);
		
		// Étage 0
		this.cb0 = createMock(Button.class);
		this.fb0 = createMock(Button.class);
		this.fs0 = createMock(FloorSensor.class);
		// Étage 1
		this.cb1 = createMock(Button.class);
		this.fb1 = createMock(Button.class);
		this.fs1 = createMock(FloorSensor.class);
	}

	
	public void createSut() {
		this.sut = new ElevatorControllerImpl();
		this.sut.setDoor(this.door);
		this.sut.setTimer(timer);
		this.sut.setMotor(this.motor);
		// Etage 0
		this.sut.setCabinButton(0, cb0);
		this.sut.setFloorButton(0, fb0);
		this.sut.setFloorSensor(0, fs0);
		// Etage 1
		this.sut.setCabinButton(1, cb1);
		this.sut.setFloorButton(1, fb1);
		this.sut.setFloorSensor(1, fs1);

		
		reset(door);
		reset(timer);
		reset(motor);
		reset(cb0);
		reset(fb0);
		reset(fs0);
		reset(cb1);
		reset(fb1);
		reset(fs1);
	}
	
	@Override
	public void ActorgetLastSentMessage(Actor ltd_receiverInstance,
			Messages message) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ElevatorControllercabinAtFloor(
			ElevatorController ltd_receiverInstance, Floor floor)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ElevatorControllercabinLeftFloor(
			ElevatorController ltd_receiverInstance, Floor floor)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ElevatorControllerdoorClosed(
			ElevatorController ltd_receiverInstance) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ElevatorControllerdoorOpened(
			ElevatorController ltd_receiverInstance) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ElevatorControllermotorStatusChanged(
			ElevatorController ltd_receiverInstance, MotorStatus newState)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ElevatorControllerrequest(
			ElevatorController ltd_receiverInstance, Actor sender, Floor floor)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ElevatorControllertimeout(ElevatorController ltd_receiverInstance) throws Exception {
		this.createSut();
		this.sut.timeout(this.sut.getTimer());
	}

	@Override
	public void closeAdapter() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
