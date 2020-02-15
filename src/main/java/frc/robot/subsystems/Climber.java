/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import javax.print.DocFlavor.READER;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

  private TalonSRX primaryClimberTalonSRX;
  private TalonSRX secondaryClimberTalonSRX;
  private Solenoid climberBrake;

  /**
   * Creates a new Climber.
   */
  public Climber() {

    primaryClimberTalonSRX = new TalonSRX(Constants.CLIMBER_PRIMARY);
    secondaryClimberTalonSRX = new TalonSRX(Constants.CLIMBER_SECONDARY);
    climberBrake = new Solenoid(Constants.PCM ,Constants.CLIMBER_Solenoid);

    secondaryClimberTalonSRX.follow(primaryClimberTalonSRX, FollowerType.PercentOutput);
    secondaryClimberTalonSRX.setInverted(InvertType.FollowMaster);

    configureTalon(primaryClimberTalonSRX);
    configureTalon(secondaryClimberTalonSRX);

  }

  private void configureTalon(TalonSRX talon) {

    talon.setNeutralMode(NeutralMode.Brake);
    talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, Constants.DRIVE_CURRENT_LIMIT, Constants.CLIMER_THRESHOLD_CURRENT, Constants.CLIMBER_THRESHOLD_TIMEOUT));

    talon.selectProfileSlot(0,0);

    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);

    talon.configMotionCruiseVelocity(20600, 0);
    talon.configMotionAcceleration(5000, 0);
    talon.config_kF(0, 0.0496, 0);
    talon.config_kP(0, 0.05, 0);
    talon.config_kI(0, 0, 0);

    talon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);

  }

  private double inchesToEncoderCounts(double inches) {

    double returnValue = (inches) * Constants.CLIMBER_MOTOR_GEAR_RATIO * Constants.FALCON_COUNTS_PER_REV / Constants.CLIMBER_WHEEL_CIRCUMFERENCE;
    return returnValue;

  }

  public void goToHeight(double inches) {
    
    releaseBrake();
    double requiredCounts = inchesToEncoderCounts(inches);
    primaryClimberTalonSRX.set(ControlMode.MotionMagic, requiredCounts);

  }

  private void moveClimber(double climberSpeed) {

    releaseBrake();
    primaryClimberTalonSRX.set(ControlMode.PercentOutput, climberSpeed);

}

  public void setBrake(){

    climberBrake.set(false);

  }

  public void releaseBrake(){

    climberBrake.set(true);

  }

  public void stopClimber() {

    primaryClimberTalonSRX.set(ControlMode.PercentOutput, 0);
    setBrake();

  }

  public boolean calibrateClimber() {

    if (primaryClimberTalonSRX.getSensorCollection().isRevLimitSwitchClosed()){
      
      primaryClimberTalonSRX.setSelectedSensorPosition(0);
      return true;
    }
    
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Climber Speed", primaryClimberTalonSRX.getSelectedSensorVelocity(0));
  }
}
