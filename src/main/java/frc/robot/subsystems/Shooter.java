/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.SparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

public class Shooter extends SubsystemBase {
 
  private static final CANSparkMax SpinShooterSpin = null;
  private TalonSRX primaryShooterTalonFX;
  private TalonSRX secondaryShooterTalonFX;

  private CANSparkMax hoodMotor;

  private CANSparkMax transitMotor;

  private CANPIDController hoodController;
  private CANEncoder hoodEncoder;

  private double targetHoodPosition = 0.0;

  private ShooterSpeeds targetShooterSpeed = ShooterSpeeds.OFF;
 
  /**
   * Creates a new Shooter.
   */
  public Shooter() {

    primaryShooterTalonFX = new TalonSRX(Constants.PRIMARY_SHOOTER_MOTOR);
    secondaryShooterTalonFX = new TalonSRX(Constants.SECONDARY_SHOOTER_MOTOR);




    hoodMotor = new CANSparkMax(Constants.HOOD_MOTOR, MotorType.kBrushless);
    hoodController = hoodMotor.getPIDController();
    hoodEncoder = hoodMotor.getEncoder(EncoderType.kHallSensor, Constants.NEO550_COUNTS_PER_REV);
    hoodMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed).enableLimitSwitch(true);
    hoodMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed).enableLimitSwitch(true);

    transitMotor = new CANSparkMax(Constants.SHOOTER_TRANSIT, MotorType.kBrushless);
    

    configureTalon(primaryShooterTalonFX);
    configureTalon(secondaryShooterTalonFX);
    hoodController.setP(0.0125, 0);
    hoodController.setI(0.00125, 0);

    

    secondaryShooterTalonFX.follow(primaryShooterTalonFX);
  }

  private void configureTalon(TalonSRX talon) {

    talon.setNeutralMode(NeutralMode.Coast);
    talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, Constants.DRIVE_CURRENT_LIMIT, Constants.THRESHOLD_CURRENT, Constants.THRESHOLD_TIMEOUT));

    talon.selectProfileSlot(0,0);

    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);

    talon.configMotionCruiseVelocity(20600, 0);
    talon.configMotionAcceleration(5000, 0);
    talon.config_kF(0, 0.0496, 0);
    talon.config_kP(0, 0.05, 0);
    talon.config_kI(0, 0, 0);

    talon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);

    talon.setSelectedSensorPosition(0);

  }

  private double angleToEncoderCounts(double angle) {
    
    double returnValue = (double)Constants.NEO550_COUNTS_PER_REV * Constants.HOOD_MOTOR_GEAR_RATIO * angle / 360.0;
    return returnValue;

  }

  public void playMusic() {


  }

  public void SpinShooterSpin() {

    primaryShooterTalonFX.set(ControlMode.PercentOutput, 1.0);

  }

  public double getAngle() {

  double position = hoodEncoder.getPosition();
  return (position * 360.0) / (Constants.NEO550_COUNTS_PER_REV * Constants.HOOD_MOTOR_GEAR_RATIO);

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

  public void hoodUp() {

    hoodMotor.set(-0.1);

  }

  public void hoodDown() {

    hoodMotor.set(0.1);
    
  }

  public void hoodStop() {

    hoodMotor.set(0.0);

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

    transitMotor.set(Constants.SHOOTER_TRANSIT_SPEED * -1.0);

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

    SmartDashboard.putNumber("Shooter Speed", getprimaryShooterSpeed()); 
    SmartDashboard.putNumber("Target Shooter Speed", targetShooterSpeed.getMotorSpeed());
    SmartDashboard.putNumber("Hood Angle", getAngle());
  }
}

