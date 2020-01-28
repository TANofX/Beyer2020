/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Drives extends SubsystemBase {
  /**
   * Creates a new Drives.
   */

  private TalonFX leftPrimaryTalonFX;
  private TalonFX rightPrimaryTalonFX;
  private TalonFX leftSecondaryTalonFX;
  private TalonFX rightSecondaryTalonFX;
  //These are Falcon Motors - TalonFX 

  private DifferentialDrive differentialDrive;
  private DifferentialDrive rDifferentialDrive;
  private DifferentialDrive currentDifferentialDrive;
  private boolean isReversed;

  public Drives() {

    //Setting up the Drive base with Primary and Secondary Motors
    leftPrimaryTalonFX = new TalonFX(Constants.LEFT_PRIMARY_MOTOR);
    rightPrimaryTalonFX = new TalonFX(Constants.RIGHT_PRIMARY_MOTOR);
    leftSecondaryTalonFX = new TalonFX(Constants.LEFT_SECONDARY_MOTOR);
    rightSecondaryTalonFX = new TalonFX(Constants.RIGHT_SECONDARY_MOTOR);

    leftSecondaryTalonFX.follow(leftPrimaryTalonFX);
    rightSecondaryTalonFX.follow(rightPrimaryTalonFX);

    configureTalon(leftPrimaryTalonFX);
    configureTalon(rightPrimaryTalonFX);
    configureTalon(leftSecondaryTalonFX);
    configureTalon(rightSecondaryTalonFX);

    differentialDrive = new DifferentialDrive((SpeedController)leftPrimaryTalonFX, (SpeedController)rightPrimaryTalonFX);
    rDifferentialDrive = new DifferentialDrive((SpeedController)rightPrimaryTalonFX, (SpeedController)leftPrimaryTalonFX);
    //This makes our starting configuration not flipped based on revereIt program
    reverseIt(false);
  }

  private void configureTalon(TalonFX talon) {

    //neutral Mode means Coasting instead of braking after ... 
    talon.setNeutralMode(NeutralMode.Coast);
    talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, Constants.DRIVE_CURRENT_LIMIT, Constants.THRESHOLD_CURRENT, Constants.THRESHOLD_TIMEOUT));

  }



  public void curvatureDrive(double speed, double turnRate) {

    boolean isQuickTurn = Math.abs(speed) <= Constants.MINIMUM_TURN_RATE; 
    currentDifferentialDrive.curvatureDrive(speed, turnRate, isQuickTurn);

    

  }

  //Conversion so we can actually do Automonous Code
  private double inchesToEncoderCounts(double inches) {

    double returnValue = (inches / Constants.DRIVE_WHEEL_CIRCUMFERENCE) * Constants.DRIVE_GEAR_RATIO * Constants.DRIVE_MOTOR_TICKS_PER_REV;
    return returnValue;

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

    }

    else {

      currentDifferentialDrive = differentialDrive;

    }

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



    // This method will be called once per scheduler run
  }
}
