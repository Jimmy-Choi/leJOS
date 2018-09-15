import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.BasicMotorPort;
import lejos.hardware.port.Port;

public class MotorController {
    
	private UnregulatedMotor left = null;
	private UnregulatedMotor right = null;
	private int power = BasicMotorPort.MAX_POWER;
	private int hpower = BasicMotorPort.MAX_POWER / 2;
	private int qpower = (BasicMotorPort.MAX_POWER / 4) * 3;
	
	public MotorController(Port left, Port right) {
		this.left = new UnregulatedMotor(left);
		this.right = new UnregulatedMotor(right);
	}
	
	public void setPower(int p) {
		power = p;
		hpower = p / 2;
		qpower = (p / 4) * 3;
	}
	
	public void goStraight() {
		left.setPower(qpower);
		right.setPower(power);
	}
	
	public void goRight() {
		left.setPower(power);
		right.setPower(hpower);
	}
	
	public void goLeft() {
		left.setPower(hpower);
		right.setPower(power);
	}
	
	public void turnRight() {
		left.setPower(power);
		right.setPower(-hpower);
	}
	
	public void turnLeft() {
		left.setPower(0);
		right.setPower(power);
	}
	
	public void uTurn() {
		left.setPower(-hpower);
		right.setPower(qpower);
	}
	
	public void pause() {
		left.setPower(0);
		right.setPower(0);
	}
	
	public void stopMotor() {
		left.stop();
		right.stop();
		left.close();
		right.close();
	}
}
