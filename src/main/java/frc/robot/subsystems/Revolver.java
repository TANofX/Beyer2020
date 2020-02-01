/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Revolver extends SubsystemBase {
  private CANSparkMax revolverMotor;
  private CANEncoder revolverEncoder;

  private static final int TICKS_PER_SLOT = Math.floorDiv((int)Constants.REVOLVER_GEAR_RATIO * Constants.NEO550_COUNTS_PER_REV, 5);

  /**
   * Creates a new Revolver.
   */
  public Revolver() {
    revolverMotor = new CANSparkMax(Constants.REVOLVER_MOTOR_ID, MotorType.kBrushless);
    revolverEncoder = revolverMotor.getEncoder(EncoderType.kHallSensor, 42);
  }

  public int currentPosition() {
    return Math.floorMod((int)revolverEncoder.getPosition(), TICKS_PER_SLOT);
  }

  public void rotateToPosition(int position) {
    int ticksFromZero = position * TICKS_PER_SLOT;

    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
