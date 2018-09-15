import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;

// drive waiting for touch sensor or escape key to stop driving.
//rgb.getRed(), rgb.getGreen(), rgb.getBlue()
/*
 * black 5 5 8
 * blue 22 25 33
 * white 49 50 69
 * red 49 6 11
 */

public class PathFinder {
	private MotorController mController;
	private ColorSensor cSensor;
	
	public PathFinder() {
		mController = new MotorController(MotorPort.A, MotorPort.B);
		mController.setPower(50);
		
		cSensor = new ColorSensor(SensorPort.S1);
		init();
	}
	
	public void init() {
        cSensor.setRGBMode();
        cSensor.setFloodLight(Color.WHITE);
	}
	
	public void goNext() {
		Color rgb;
		while (true) {
        	rgb = cSensor.getColor();
        	if (rgb.getRed() < 10 && rgb.getGreen() < 10 && rgb.getBlue() < 12) {
        		mController.goRight();
        	} else if (rgb.getRed() > 30 && rgb.getGreen() < 15 && rgb.getBlue() < 15) {
        		break;
        	} else if (rgb.getRed() > 40 && rgb.getGreen() > 40 && rgb.getBlue() > 50) {
        		mController.goLeft();//white
        	} else {
        		mController.goLeft();
        	}
        }
	}
	
	public void turnRight() {
		Color rgb = cSensor.getColor();
		while (!(rgb.getRed() > 30 && rgb.getGreen() > 30 && rgb.getBlue() > 40)) {
			rgb = cSensor.getColor();
			mController.turnRight();
		}
	}	

	public void turnLeft() {
		Color rgb = cSensor.getColor();
		while (!(rgb.getRed() < 10 && rgb.getGreen() < 10 && rgb.getBlue() < 12)) {
			rgb = cSensor.getColor();
			mController.turnLeft();
		}
	}
	
	public void goStraight() {
		Color rgb = cSensor.getColor();
		while (!(rgb.getRed() > 30 && rgb.getGreen() > 30 && rgb.getBlue() > 40)) {
			rgb = cSensor.getColor();
			mController.goRight();
		}
	}
	
	public void uTurn() {
		Color rgb = cSensor.getColor();
		while (!(rgb.getRed() < 10 && rgb.getGreen() < 10 && rgb.getBlue() < 12)) {
			rgb = cSensor.getColor();
			mController.uTurn();
		}
	}
	
	public void pause() {
		mController.pause();
	}
	
	public void stop() {
		mController.stopMotor();
        cSensor.close();
	}
	
}
