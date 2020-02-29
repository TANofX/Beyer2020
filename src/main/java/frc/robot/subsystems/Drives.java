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
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.DriveMotor;


public class Drives extends SubsystemBase {
  /**
   * Creates a new Drives.
   */

  private DriveMotor leftPrimaryTalonFX;
  private DriveMotor rightPrimaryTalonFX;
  private DriveMotor leftSecondaryTalonFX;
  private DriveMotor rightSecondaryTalonFX;
  //These are Falcon Motors - TalonFX 

  private DifferentialDrive differentialDrive;
  private DifferentialDrive rDifferentialDrive;
  private DifferentialDrive currentDifferentialDrive;
  private boolean isReversed;

  public Drives() {

    //Setting up the Drive base with Primary and Secondary Motors
    leftPrimaryTalonFX = new DriveMotor(Constants.LEFT_PRIMARY_MOTOR);
    rightPrimaryTalonFX = new DriveMotor(Constants.RIGHT_PRIMARY_MOTOR);
    leftSecondaryTalonFX = new DriveMotor(Constants.LEFT_SECONDARY_MOTOR);
    rightSecondaryTalonFX = new DriveMotor(Constants.RIGHT_SECONDARY_MOTOR);

    leftSecondaryTalonFX.follow(leftPrimaryTalonFX);
    rightSecondaryTalonFX.follow(rightPrimaryTalonFX);

    configureTalon(leftPrimaryTalonFX);
    configureTalon(rightPrimaryTalonFX);
    configureTalon(leftSecondaryTalonFX);
    configureTalon(rightSecondaryTalonFX);

    differentialDrive = new DifferentialDrive(leftPrimaryTalonFX, rightPrimaryTalonFX);
    rDifferentialDrive = new DifferentialDrive(rightPrimaryTalonFX, leftPrimaryTalonFX);
    //This makes our starting configuration not flipped based on revereIt program
    reverseIt(false);
  }

  private void configureTalon(TalonSRX talon) {

    //neutral Mode means Coasting instead of braking after ... 
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

  public void playMusic() {

    


  }

  public void curvatureDrive(double speed, double turnRate) {

    boolean isQuickTurn = Math.abs(speed) <= Constants.MINIMUM_TURN_RATE; 
    currentDifferentialDrive.curvatureDrive(speed, turnRate, isQuickTurn);

    

  }

  //Conversion so we can actually do Automonous Code
  private double inchesToEncoderCounts(double inches) {

    double returnValue = (inches / Constants.DRIVE_WHEEL_CIRCUMFERENCE) * Constants.DRIVE_GEAR_RATIO * Constants.FALCON_COUNTS_PER_REV;
    return returnValue;

  }

  public void moveXInches(double inches) {

    double requiredCounts = inchesToEncoderCounts(inches);
    double rightTargetCounts = rightPrimaryTalonFX.getSelectedSensorPosition() + requiredCounts;
    double leftTargetCounts = leftPrimaryTalonFX.getSelectedSensorPosition() - requiredCounts;
    rightPrimaryTalonFX.set(ControlMode.MotionMagic, rightTargetCounts);
    leftPrimaryTalonFX.set(ControlMode.MotionMagic, leftTargetCounts);

  }

  public double getLeftPrimarySpeed() {

    return leftPrimaryTalonFX.getSelectedSensorVelocity(0);

  }

  public double getRightPrimarySpeed() {

    return rightPrimaryTalonFX.getSelectedSensorVelocity(0);

  }

  public double getLeftSecondarySpeed() {

    return leftSecondaryTalonFX.getSelectedSensorVelocity(0);

  }

  public double getRightSecondarySpeed() {

    return rightSecondaryTalonFX.getSelectedSensorVelocity(0);

  }
 


  public void tankDrive(double leftSpeed, double rightSpeed) {

    currentDifferentialDrive.tankDrive(leftSpeed, rightSpeed);

  }

  

  //This is the method that flips the driving
  public void reverseIt(boolean reversed) {

    isReversed = reversed;

    if (reversed) { 

      currentDifferentialDrive = rDifferentialDrive;
      differentialDrive.setSafetyEnabled(false);

    }

    else {

      currentDifferentialDrive = differentialDrive;
      rDifferentialDrive.setSafetyEnabled(false);

    }
    currentDifferentialDrive.setSafetyEnabled(true);
  }

  public boolean isReversed() {

    return isReversed;

  }

  public void stop() {

  currentDifferentialDrive.stopMotor();
    leftPrimaryTalonFX.set(ControlMode.PercentOutput, 0);
    rightPrimaryTalonFX.set(ControlMode.PercentOutput, 0);
 
  } 

  @Override
  public void periodic() {

    //SmartDashboard.putNumber("LeftPrimaryMotorRpm", leftPrimaryTalonFX.getSelectedSensorVelocity(0));
    //SmartDashboard.putNumber("RightPrimaryMotorRpm", rightPrimaryTalonFX.getSelectedSensorVelocity(0));

    SmartDashboard.putNumber("targetError", rightPrimaryTalonFX.getClosedLoopError());
    //SmartDashboard.putNumber("rightMotorPosition", rightPrimaryTalonFX.getSelectedSensorPosition(0));

    //SmartDashboard.putNumber("LeftPrimaryMotorVoltage", leftPrimaryTalonFX.getMotorOutputVoltage());
    //SmartDashboard.putNumber("LeftSecondaryMotorVoltage", leftSecondaryTalonFX.getMotorOutputVoltage());
    //SmartDashboard.putNumber("RightPrimaryMotorVoltage", rightPrimaryTalonFX.getMotorOutputVoltage());
    //SmartDashboard.putNumber("RigtSecondaryMotorVoltage", rightSecondaryTalonFX.getMotorOutputVoltage());

    // This method will be called once per scheduler run
  }
}
