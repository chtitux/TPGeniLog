package fr_uha_ensisa_ff_gl_lift_soft_model.ElevatorController;

import fr_uha_ensisa_ff_gl_lift_soft_model.InstancesDeclaration;

public interface InstancesDefinition {
	
	public enum Actor implements InstancesDeclaration.Actor {
		door,
		cb0,
		fb0,
		cb1,
		fb1,
		cb2,
		fb2,
		motor,
		clock
	}
	
	public enum Cabin implements InstancesDeclaration.Cabin {
		cabin
	}
	
	public enum ElevatorController implements InstancesDeclaration.ElevatorController {
		sut
	}
	
	public enum Floor implements InstancesDeclaration.Floor {
		_0,
		_1,
		_2
	}
	
}