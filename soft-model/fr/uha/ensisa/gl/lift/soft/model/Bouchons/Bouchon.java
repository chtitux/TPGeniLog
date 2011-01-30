package fr.uha.ensisa.gl.lift.soft.model.Bouchons;

import fr.uha.ensisa.gl.lift.soft.model.Enumerations.Messages;

public abstract class Bouchon {
	private Messages message;
	
	public Bouchon() {
		this.message = Messages.none;
	}
	
	public void setMessage(Messages message) {
		this.message = message;
	}
	
	public Messages getLastSentMessage() {
		return message;
	}
}
