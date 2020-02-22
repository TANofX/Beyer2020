/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
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
  private DigitalInput fuelCellSensor;
  private DigitalInput revolverPositionSensor;
  private CircularArrayList<Integer> revolverArray; 


  private double targetPosition = 0.0;

  private static final double TICKS_PER_REV = (Constants.REVOLVER_GEAR_RATIO * Constants.NEO550_COUNTS_PER_REV);
  private static final double TICKS_PER_SLOT = (TICKS_PER_REV / 5.0);

  /**
   * Creates a new Revolver.
   */
  public Revolver() {
    revolverMotor = new CANSparkMax(Constants.REVOLVER_MOTOR_ID, MotorType.kBrushless);
    collectorTransit = new CANSparkMax(Constants.COLLECTOR_TRANSIT_MOTOR, MotorType.kBrushless);
    revolverController = revolverMotor.getPIDController();
    revolverEncoder = revolverMotor.getEncoder(EncoderType.kHallSensor, Constants.NEO550_COUNTS_PER_REV);
    revolverMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    fuelCellSensor = new DigitalInput(Constants.FUEL_CELL_SENSOR_PORT);
    revolverPositionSensor = new DigitalInput(Constants.REVOLVER_POSITION_SENSOR);
    revolverArray = new CircularArrayList<Integer>(5);
    for (int i = 0; i < 5; i++) {

      revolverArray.add(i, 0);

    }

  }

  public void spinRevolver() {

    collectorTransit.set(Constants.COLLECTOR_TRANSIT_MOTOR_SPEED);
    revolverMotor.set(0.10);

  }

  public void stopRevolver() {

    revolverMotor.stopMotor();
    collectorTransit.stopMotor();
  }

  private int currentRevolution() {

    return (int) (revolverEncoder.getPosition() / TICKS_PER_REV);

  }

  public int currentPosition() {
    return (int) ((revolverEncoder.getPosition() % TICKS_PER_REV) / TICKS_PER_SLOT);
  }

  public boolean positionCheck() {

  double revolverError = Math.abs(revolverEncoder.getPosition() - targetPosition);
  return revolverError < Constants.REVOLVER_THRESHOLD;

  }
  
  public void rotateToPosition(int position) {

    if(position < 0  || position > 4) {
      throw new ArrayIndexOutOfBoundsException();
    }

    collectorTransit.set(Constants.COLLECTOR_TRANSIT_MOTOR_SPEED);

    int Revolution = currentRevolution();
    if(position < currentPosition()) Revolution++;

    double ticksFromZeroRevolution = Math.round(position * TICKS_PER_SLOT);
    targetPosition = Revolution * TICKS_PER_REV + ticksFromZeroRevolution;
    revolverController.setReference(targetPosition, ControlType.kSmartMotion, 0);
    
  }

  public boolean calibrateRevolver() {



      if (revolverPositionSensor.get()) {

        stopRevolver();
        revolverEncoder.setPosition(0.0);
        return true;

      }
    
      else return false;

  }

  public boolean checkForFuel() {

    return !fuelCellSensor.get();
 
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

  public void shootFuelCell(){

    revolverArray.set(this.currentPosition() + 2, 0);

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

    SmartDashboard.putNumber("Fuel Cell Sensor", fuelCellSensor.getVoltage());

  }
}
