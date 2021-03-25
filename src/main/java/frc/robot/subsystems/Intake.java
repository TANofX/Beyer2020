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
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

CANSparkMax intakeRollerMotor;
CANSparkMax intakeOmniMotor;
CANSparkMax intakeTransitMotor1;
DoubleSolenoid  collectorArm;
AnalogInput fuelCellSensor;
CANPIDController OmniController;
CANPIDController intakeTransitController;

GreenWheel transitWheel;




  public Intake(GreenWheel theWheel) {

    intakeRollerMotor = new CANSparkMax(Constants.INTAKE_ROLLERS, MotorType.kBrushless);
    intakeOmniMotor = new CANSparkMax(Constants.INTAKE_OMNI_WHEEL, MotorType.kBrushless);
    intakeTransitMotor1 = new CANSparkMax(Constants.INTAKE_TRANSIT, MotorType.kBrushless);

    intakeRollerMotor.restoreFactoryDefaults();
    intakeOmniMotor.restoreFactoryDefaults();
    intakeTransitMotor1.restoreFactoryDefaults();

    collectorArm = new DoubleSolenoid(Constants.PCM ,Constants.INTAKE_FOREWARD_SOLENOID, Constants.INTAKE_REVERSE_SOLENOID);
    fuelCellSensor = new AnalogInput(Constants.INTAKE_FUEL_CELL_SENSOR);
    OmniController = intakeOmniMotor.getPIDController();
    intakeTransitController = intakeTransitMotor1.getPIDController();

    transitWheel = theWheel;

    
    //configureTalon(intakeRollerMotor);

    intakeOmniMotor.setSmartCurrentLimit(50, 120);

    OmniController.setP(0.000075);
    OmniController.setI(0.0000004);
    OmniController.setD(0.0);
    OmniController.setFF(0.0000156);
    OmniController.setSmartMotionMaxVelocity(4200.0, 0);
    OmniController.setSmartMotionMinOutputVelocity(0.0, 0);
    OmniController.setSmartMotionMaxAccel(15000.0, 0);
    OmniController.setSmartMotionAllowedClosedLoopError(0.0, 0);
    OmniController.setOutputRange(-1.0, 1.0);

    intakeTransitController.setP(0.00000125);
    intakeTransitController.setI(0.00000025);
    intakeTransitController.setD(0.0);
    intakeTransitController.setFF(0.0);
    intakeTransitController.setSmartMotionMaxVelocity(4200.0, 0);
    intakeTransitController.setSmartMotionMinOutputVelocity(0.0, 0);
    intakeTransitController.setSmartMotionMaxAccel(15000.0, 0);
    intakeTransitController.setSmartMotionAllowedClosedLoopError(0.0, 0);
    intakeTransitController.setOutputRange(-1.0, 1.0);
  } 
  

  private void configureTalon(CANSparkMax talon) {



  }

  public void moveRollerDown() {

    collectorArm.set(DoubleSolenoid.Value.kForward);

  }

  public void moveRollerUp() {

    collectorArm.set(DoubleSolenoid.Value.kReverse);

  }




  private void spinRoller() {

   if(collectorArm.get() == DoubleSolenoid.Value.kForward) intakeRollerMotor.set(0.25 * -1.0);

  }

  public void activateIntake() {

    if(collectorArm.get() == DoubleSolenoid.Value.kForward) { 
    OmniController.setReference(Constants.INTAKE_OMNI_MOTOR_SPEED, ControlType.kVelocity);
    intakeTransitController.setReference(Constants.INTAKE_TRANSIT1_SPEED * 1.0, ControlType.kVelocity);
    transitWheel.runTransit();
    }

  }

  public void activateExtake() {

    if(collectorArm.get() == DoubleSolenoid.Value.kForward) {
    intakeRollerMotor.set(Constants.INTAKE_ROLLER_MOTOR_SPEED * 1.0);
    OmniController.setReference(Constants.INTAKE_OMNI_MOTOR_SPEED * -1.0, ControlType.kVelocity);
    intakeTransitController.setReference(Constants.INTAKE_TRANSIT1_SPEED * -1.0, ControlType.kVelocity);
    }
  }

  public void stopIntake() {

    
    intakeOmniMotor.stopMotor();
    intakeTransitMotor1.stopMotor();
    transitWheel.stopTransit();

  }

  public void activateIntakeRollers() {

   intakeRollerMotor.set(Constants.INTAKE_ROLLER_MOTOR_SPEED * -1.0);

  }

  public void stopIntakeRollers() {

  intakeRollerMotor.set(0);

  }


  public boolean checkForFuel() {

    return (fuelCellSensor.getVoltage() >= 1.0);
 
  }

  @Override
  public void periodic() {

    SmartDashboard.putBoolean("Intake Fuel Cell", checkForFuel());
    SmartDashboard.putNumber("Intake voltage", fuelCellSensor.getVoltage());
    // This method will be called once per scheduler run
  }
}
