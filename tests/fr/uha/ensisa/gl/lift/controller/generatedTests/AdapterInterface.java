package fr_uha_ensisa_ff_gl_lift_soft_model;

import static fr_uha_ensisa_ff_gl_lift_soft_model.InstancesDeclaration.*;
import static fr_uha_ensisa_ff_gl_lift_soft_model.Enumerations.*;

public interface AdapterInterface {
	
	public void ActorgetLastSentMessage(Actor ltd_receiverInstance, Messages message) throws Exception;
	
	public void ElevatorControllercabinAtFloor(ElevatorController ltd_receiverInstance, Floor floor) throws Exception;
	
	public void ElevatorControllercabinLeftFloor(ElevatorController ltd_receiverInstance, Floor floor) throws Exception;
	
	public void ElevatorControllerdoorClosed(ElevatorController ltd_receiverInstance) throws Exception;
	
	public void ElevatorControllerdoorOpened(ElevatorController ltd_receiverInstance) throws Exception;
	
	public void ElevatorControllermotorStatusChanged(ElevatorController ltd_receiverInstance, MotorStatus newState) throws Exception;
	
	public void ElevatorControllerrequest(ElevatorController ltd_receiverInstance, Actor sender, Floor floor) throws Exception;
	
	public void ElevatorControllertimeout(ElevatorController ltd_receiverInstance) throws Exception;
	
	public void closeAdapter() throws Exception;
	
}
