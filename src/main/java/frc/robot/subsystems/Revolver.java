/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.SpinRevolver;
import frc.robot.utils.CircularArrayList;

public class Revolver extends SubsystemBase {
  private CANPIDController revolverController; 
  private CANSparkMax revolverMotor;
  private CANSparkMax collectorTransit;
  private CANEncoder revolverEncoder;
  private AnalogInput fuelCellSensor;
  private DigitalInput revolverPositionSensor;
  private CircularArrayList<Integer> revolverArray; 
  private boolean runtransit = false;

  private double targetPosition = 0.0;

  private static final double TICKS_PER_REV = Constants.REVOLVER_GEAR_RATIO * Constants.NEO550_COUNTS_PER_REV;
  private static final double TICKS_PER_SLOT = (Math.ceil(TICKS_PER_REV / 5.0));

  /**
   * Creates a new Revolver.
   */
  public Revolver() {
    revolverMotor = new CANSparkMax(Constants.REVOLVER_MOTOR_ID, MotorType.kBrushless);
    collectorTransit = new CANSparkMax(Constants.COLLECTOR_TRANSIT_MOTOR, MotorType.kBrushless);
    revolverController = revolverMotor.getPIDController();
    revolverEncoder = revolverMotor.getEncoder(EncoderType.kHallSensor, (int)Constants.NEO550_COUNTS_PER_REV);
    revolverMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    fuelCellSensor = new AnalogInput(Constants.FUEL_CELL_SENSOR_PORT);
    revolverPositionSensor = new DigitalInput(Constants.REVOLVER_POSITION_SENSOR);
    revolverArray = new CircularArrayList<Integer>(5);
    for (int i = 0; i < 5; i++) {

      revolverArray.add(i, 0);

    }
    revolverMotor.setSmartCurrentLimit(10, 11000);   //.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, Constants.DRIVE_CURRENT_LIMIT, Constants.THRESHOLD_CURRENT, Constants.THRESHOLD_TIMEOUT));
    revolverController.setP(0.000001);
    revolverController.setI(0.000000004);
    revolverController.setD(0.0);
    revolverController.setFF(0.00009);
    revolverController.setSmartMotionMaxVelocity(4200.0, 0);
    revolverController.setSmartMotionMinOutputVelocity(0.0, 0);
    revolverController.setSmartMotionMaxAccel(15000.0, 0);
    revolverController.setSmartMotionAllowedClosedLoopError(0.0, 0);
    revolverController.setOutputRange(0.0, 1.0);
  }

  public void spinRevolver() {

    revolverController.setReference(Constants.REVOLVER_CALIBRATION_SPEED, ControlType.kVelocity);

  }

  public void runTransit(){

    runtransit = true;
    collectorTransit.set(Constants.COLLECTOR_TRANSIT_MOTOR_SPEED);
  }

  public void stopTransit() {

    //if (! runtransit) {

      collectorTransit.stopMotor();
    //}

  }

  public void stopIntake() {

    runtransit = false;
    stopTransit();
  }

  public void stopRevolver() {

    revolverController.setReference(0.0, ControlType.kVelocity);
    stopTransit();
  }

 
  private int currentRevolution() {

    return (int) Math.floor(revolverEncoder.getPosition() / TICKS_PER_REV);

  }

  public int currentPosition() {
    return (int) Math.round((revolverEncoder.getPosition() % TICKS_PER_REV) / TICKS_PER_SLOT);
  }

  public boolean positionCheck() {

  double revolverError = Math.abs(revolverEncoder.getPosition() - targetPosition);
  return revolverError < Constants.REVOLVER_THRESHOLD;

  }
  
  public void rotateToPosition(int position) {

    if(position < 0  || position > 4) {
     // throw new ArrayIndexOutOfBoundsException();
     position = position % 5;
    }

    collectorTransit.set(Constants.COLLECTOR_TRANSIT_MOTOR_SPEED);

    int Revolution = currentRevolution();
    if(position < currentPosition()) Revolution++;

    double ticksFromZeroRevolution = Math.round(position * TICKS_PER_SLOT);
    targetPosition = Revolution * TICKS_PER_REV + ticksFromZeroRevolution;
    revolverController.setReference(targetPosition, ControlType.kSmartMotion, 0);
    
  }

  public int nextEmptyPosition() {

    int cp = currentPosition();
    for (int i = cp; i < cp + 4; i++) {

     if (revolverArray.get(i) == 0) {

      return i % 5;
     }
    }

    return 0;
  }

  public boolean calibrateRevolver() {



      if (revolverPositionSensor.get()) {

        stopRevolver();
        stopTransit();
        revolverEncoder.setPosition(0.0);

        return true;

      }
    
      else return false;

  }

  public boolean checkForFuel() {

    return (fuelCellSensor.getVoltage() >= Constants.SENSOR_TRIGGER_VOLTAGE);
 
  }

  public boolean intakeFuelCell() {

    if (checkForFuel()){

      revolverArray.set(this.currentPosition(), 1);

      return true;

    }

    else {
      
      revolverArray.set(this.currentPosition(), 0);

      return false;

    }

  }

  public boolean isRotating() {

    return ( ! positionCheck());

  }

  public void shootFuelCell(){

    revolverArray.set(this.currentPosition() + 2, 0);

  }

  public void assumeEmpty(){

    for (int i = 0; i <= 4; i ++ ){

      revolverArray.set(i , 0);
    }

  }
  public int sumFuelCells() {

    int sum = 0;

    for (int i = 0; i < 5; i++){

      sum = sum + revolverArray.get(i);

    }

    return sum;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Revolver Position", revolverEncoder.getPosition());
    SmartDashboard.putBoolean("Fuel Cell Sensor", checkForFuel());
    SmartDashboard.putNumber("Number of Fuel Cell", sumFuelCells());
    SmartDashboard.putBoolean("Revoler Calibrate", revolverPositionSensor.get());
    SmartDashboard.putNumber("Counts per Revolution", revolverEncoder.getCountsPerRevolution());
    SmartDashboard.putNumber("Position Conversion Factor", revolverEncoder.getPositionConversionFactor());
    SmartDashboard.putNumber("fuel cell slot", currentPosition());
    SmartDashboard.putNumber("voltage for fuel cell sensor", fuelCellSensor.getVoltage());
    SmartDashboard.putNumber("Revolver Target Position", targetPosition);
    SmartDashboard.putNumber("Next Empty Postion", nextEmptyPosition());
    SmartDashboard.putNumber("Revolver Motor Voltage", revolverMotor.getOutputCurrent());

    for (int x = 0; x < 5; x++) {
      SmartDashboard.putBoolean("Fuel Cell " + x, (revolverArray.get(x) == 1));
    }

  }
}
