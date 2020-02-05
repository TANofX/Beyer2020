/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

public class Shooter extends SubsystemBase {
 
  private TalonFX primaryShooterTalonFX;
  private TalonFX secondaryShooterTalonFX;

  private CANSparkMax hoodMotor;

  private CANSparkMax transitMotor;

  private CANPIDController hoodController;
  private CANEncoder hoodEncoder;

  private double targetHoodPosition = 0.0;

  private ShooterSpeeds targetShooterSpeed;
 
  /**
   * Creates a new Shooter.
   */
  public Shooter() {

    primaryShooterTalonFX = new TalonFX(Constants.PRIMARY_SHOOTER_MOTOR);
    secondaryShooterTalonFX = new TalonFX(Constants.SECONDARY_SHOOTER_MOTOR);

    hoodMotor = new CANSparkMax(Constants.HOOD_MOTOR, MotorType.kBrushless);
    hoodController = hoodMotor.getPIDController();
    hoodEncoder = hoodMotor.getEncoder(EncoderType.kHallSensor, Constants.NEO550_COUNTS_PER_REV);

    transitMotor = new CANSparkMax(Constants.SHOOTER_TRANSIT, MotorType.kBrushless);
    

    secondaryShooterTalonFX.follow(primaryShooterTalonFX);

    configureTalon(primaryShooterTalonFX);
    configureTalon(secondaryShooterTalonFX);

  }

  private void configureTalon(TalonFX talon) {

    talon.setNeutralMode(NeutralMode.Coast);

  }

  private double angleToEncoderCounts(double angle) {
    
    double returnValue = (double)Constants.NEO550_COUNTS_PER_REV * Constants.HOOD_MOTOR_GEAR_RATIO * angle / 360.0;
    return returnValue;

  }

  public boolean moveHood(double angle) {
    // protecting the shooter from us just destroying it
    double clampedAngle = angle;
    if (clampedAngle < Constants.HOOD_MIN) {
      return false;
    }
    else if (clampedAngle > Constants.HOOD_MAX) {
      return false;
    }
    targetHoodPosition = angleToEncoderCounts(clampedAngle);

    hoodController.setReference(targetHoodPosition, ControlType.kSmartMotion);

    return true;
  }

  public void lowerHood() {
    // Moves hood to lowest possible point to go under the trench
    moveHood(Constants.HOOD_MIN);

  }

  public boolean hoodInPosition() {
    // Makes sure that the hood is in the correct position is was given
    if (Math.abs(targetHoodPosition / Constants.NEO550_COUNTS_PER_REV - hoodEncoder.getPosition()) < Constants.HOOD_ANGLE_THRESHOLD) {
      return true;
    }

    return false;
  }

  public void shoot() {

    transitMotor.set(Constants.SHOOTER_TRANSIT_SPEED);

  }

  public void stopShoot() {

    transitMotor.set(0);

  }


  public double getprimaryShooterSpeed() {

    return primaryShooterTalonFX.getSelectedSensorVelocity(0);

  }

  public double getSecondaryShooterSpeed() {

    return secondaryShooterTalonFX.getSelectedSensorVelocity(0);

  }

public void spinPrimaryMotor(ShooterSpeeds Speed) {

  primaryShooterTalonFX.set(ControlMode.Velocity, Speed.getMotorSpeed());
  targetShooterSpeed = Speed;

}  

public boolean atSpeed() {

  if (Math.abs(targetShooterSpeed.getMotorSpeed() - getprimaryShooterSpeed()) < Constants.SHOOTER_SPIN_ERROR){
    return true;
  }
  return false;
}


  public void stop() {
    // This is the emergency stop button for the shooter
    primaryShooterTalonFX.set(ControlMode.PercentOutput, 0);
    secondaryShooterTalonFX.set(ControlMode.PercentOutput, 0);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

