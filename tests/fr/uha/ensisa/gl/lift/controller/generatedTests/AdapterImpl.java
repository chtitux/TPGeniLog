package fr.uha.ensisa.gl.lift.controller.generatedTests;

import static org.junit.Assert.*;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;
import fr.uha.ensisa.gl.lift.controller.bouchons.Bouchon;
import fr.uha.ensisa.gl.lift.controller.bouchons.ButtonBouchon;
import fr.uha.ensisa.gl.lift.controller.bouchons.DoorBouchon;
import fr.uha.ensisa.gl.lift.controller.bouchons.FloorSensorBouchon;
import fr.uha.ensisa.gl.lift.controller.bouchons.MotorBouchon;
import fr.uha.ensisa.gl.lift.controller.bouchons.TimerBouchon;
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
		//Initialisation du système
		this.initSut();
		//Création du système
		this.createSut();
	}

	public void initSut() {
		this.door = new DoorBouchon();
		this.timer = new TimerBouchon();
		this.motor = new MotorBouchon();
		
		// Étage 0
		this.cb0 = new ButtonBouchon();
		this.fb0 = new ButtonBouchon();
		this.fs0 = new FloorSensorBouchon();
		// Étage 1
		this.cb1 = new ButtonBouchon();
		this.fb1 = new ButtonBouchon();
		this.fs1 = new FloorSensorBouchon();
		// Étage 2
		this.cb2 = new ButtonBouchon();
		this.fb2 = new ButtonBouchon();
		this.fs2 = new FloorSensorBouchon();
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
	}
	
	@Override
	public void ActorgetLastSentMessage(Actor ltd_receiverInstance, Messages message) throws Exception {
		assertTrue((message==getObjectFromActor(ltd_receiverInstance).getLastSentMessage()));
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
	
	private Bouchon getObjectFromActor(Actor sender)	{
		Bouchon actor = null;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.cb0)
			actor = (Bouchon) this.cb0;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.fb0)
			actor = (Bouchon) this.fb0;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.cb1)
			actor = (Bouchon) this.cb1;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.fb1)
			actor = (Bouchon) this.fb1;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.cb2)
			actor = (Bouchon) this.cb2;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.fb2)
			actor = (Bouchon) this.fb2;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.motor)
			actor = (Bouchon) this.motor;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.clock)
			actor = (Bouchon) this.timer;
		if (sender == fr.uha.ensisa.gl.lift.controller.generatedTests.ElevatorController.InstancesDefinition.Actor.door)
			actor = (Bouchon) this.door;
		
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
