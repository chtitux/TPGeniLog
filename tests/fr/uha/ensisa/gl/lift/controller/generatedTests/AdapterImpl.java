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
	
	private Button cb2;
	private Button fb2;
	private FloorSensor fs2;
	
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
		// Étage 2
		this.cb2 = createMock(Button.class);
		this.fb2 = createMock(Button.class);
		this.fs2 = createMock(FloorSensor.class);
		
		//Création du système
		this.createSut();
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
		// Etage 2
		this.sut.setCabinButton(2, cb2);
		this.sut.setFloorButton(2, fb2);
		this.sut.setFloorSensor(2, fs2);

		
		reset(door);
		reset(timer);
		reset(motor);
		reset(cb0);
		reset(fb0);
		reset(fs0);
		reset(cb1);
		reset(fb1);
		reset(fs1);
		reset(cb2);
		reset(fb2);
		reset(fs2);
	}
	
	@Override
	public void ActorgetLastSentMessage(Actor ltd_receiverInstance, Messages message) throws Exception {
		
	}

	@Override
	public void ElevatorControllercabinAtFloor(ElevatorController ltd_receiverInstance, Floor floor) throws Exception {
		int floorNum = this.getFloorNumber(floor);
		this.sut.cabinAtFloor(this.sut.getFloorSensor(floorNum), floorNum);
	}

	@Override
	public void ElevatorControllercabinLeftFloor(ElevatorController ltd_receiverInstance, Floor floor) throws Exception {		
		int floorNum = this.getFloorNumber(floor);
		this.sut.cabinLeftFloor(this.sut.getFloorSensor(floorNum), floorNum);
	}

	@Override
	public void ElevatorControllerdoorClosed(ElevatorController ltd_receiverInstance) throws Exception {
		this.sut.doorClosed(this.door);
	}

	@Override
	public void ElevatorControllerdoorOpened(ElevatorController ltd_receiverInstance) throws Exception {
		this.sut.doorOpened(this.door);
	}

	@Override
	public void ElevatorControllermotorStatusChanged(ElevatorController ltd_receiverInstance, MotorStatus newState) throws Exception {
		fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus state = null;
		switch (newState)
		{
			case goingDownward:
				state = fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus.GoingDownward;
				break;
			case goingUpward:
				state = fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus.GoingUpward;
				break;
			case stopped:
				state = fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus.Stopped;
				break;
		}
		this.sut.motorStatusChanged(this.motor, state);
	}

	@Override
	public void ElevatorControllerrequest(ElevatorController ltd_receiverInstance, Actor sender, Floor floor) throws Exception {
		int floorNum = this.getFloorNumber(floor);
		Button button = (Button) getObjectFromActor(sender);
		this.sut.request(button, floorNum);
	}

	@Override
	public void ElevatorControllertimeout(ElevatorController ltd_receiverInstance) throws Exception {
		this.sut.timeout(this.sut.getTimer());
	}

	@Override
	public void closeAdapter() throws Exception {
		//Pas besoin dans notre cas
	}
	
	private Object getObjectFromActor(Actor sender)	{
		Object actor = null;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.cb0)
			actor = this.cb0;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.fb0)
			actor = this.fb0;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.cb1)
			actor = this.cb1;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.fb1)
			actor = this.fb1;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.cb2)
			actor = this.cb2;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.fb2)
			actor = this.fb2;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.motor)
			actor = this.motor;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.clock)
			actor = this.timer;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.door)
			actor = this.door;
		
		return actor;
	}
	
	private int getFloorNumber(Floor floor)	{
		int floorNum = -1;
		if (floor == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Floor._0)
			floorNum = 0;
		if (floor == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Floor._1)
			floorNum = 1;
		if (floor == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Floor._2)
			floorNum = 2;
		return floorNum;
	}

}
