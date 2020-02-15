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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.SpinRevolver;

public class Revolver extends SubsystemBase {
  private CANPIDController revolverController; 
  private CANSparkMax revolverMotor;
  private CANEncoder revolverEncoder;
  private AnalogInput fuelCellSensor;

  private double targetPosition;

  private static final double TICKS_PER_REV = (Constants.REVOLVER_GEAR_RATIO * Constants.NEO550_COUNTS_PER_REV);
  private static final double TICKS_PER_SLOT = (TICKS_PER_REV / 5.0);

  /**
   * Creates a new Revolver.
   */
  public Revolver() {
    revolverMotor = new CANSparkMax(Constants.REVOLVER_MOTOR_ID, MotorType.kBrushless);
    revolverController = revolverMotor.getPIDController();
    revolverEncoder = revolverMotor.getEncoder(EncoderType.kHallSensor, Constants.NEO550_COUNTS_PER_REV);

    fuelCellSensor = new AnalogInput(Constants.FUEL_CELL_SENSOR_PORT);
  }

  public void SpinRevolver() {

    revolverMotor.set(0.5);

  }

  private int currentRevolution() {

    return (int) (revolverEncoder.getPosition() / TICKS_PER_REV);

  }

  public int currentPosition() {
    return (int) ((revolverEncoder.getPosition() % TICKS_PER_REV) / TICKS_PER_SLOT);
  }

  public void rotateToPosition(int position) {

    if(position < 0  || position > 4) {
      throw new ArrayIndexOutOfBoundsException();
    }

    int Revolution = currentRevolution();
    if(position < currentPosition()) Revolution++;

    double ticksFromZeroRevolution = Math.round(position * TICKS_PER_SLOT);
    targetPosition = Revolution * TICKS_PER_REV + ticksFromZeroRevolution;
    revolverController.setReference(targetPosition, ControlType.kSmartMotion, 0);
    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Fuel Cell Sensor", fuelCellSensor.getVoltage());

  }
}
