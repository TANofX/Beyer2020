/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
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




  public Intake() {

    intakeRollerMotor = new CANSparkMax(Constants.INTAKE_ROLLERS, MotorType.kBrushless);
    intakeOmniMotor = new CANSparkMax(Constants.INTAKE_OMNI_WHEEL, MotorType.kBrushless);
    intakeTransitMotor1 = new CANSparkMax(Constants.INTAKE_TRANSIT, MotorType.kBrushless);
    collectorArm = new DoubleSolenoid(Constants.PCM ,Constants.INTAKE_FOREWARD_SOLENOID, Constants.INTAKE_REVERSE_SOLENOID);
    fuelCellSensor = new AnalogInput(Constants.INTAKE_FUEL_CELL_SENSOR);

    configureTalon(intakeRollerMotor);

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
    intakeOmniMotor.set(Constants.INTAKE_OMNI_MOTOR_SPEED);
    intakeTransitMotor1.set(Constants.INTAKE_TRANSIT1_SPEED * 1.0);
    }

  }

  public void activateExtake() {

    if(collectorArm.get() == DoubleSolenoid.Value.kForward) {
    intakeRollerMotor.set(Constants.INTAKE_ROLLER_MOTOR_SPEED * 1.0);
    intakeOmniMotor.set(Constants.INTAKE_OMNI_MOTOR_SPEED * -1.0);
    intakeTransitMotor1.set(Constants.INTAKE_TRANSIT1_SPEED * -1.0);
    }
  }

  public void stopIntake() {

    
    intakeOmniMotor.stopMotor();
    intakeTransitMotor1.stopMotor();

  }

  public void activateIntakeRollers() {

   intakeRollerMotor.set(Constants.INTAKE_ROLLER_MOTOR_SPEED * -1.0);

  }

  public void stopIntakeRollers() {

  intakeRollerMotor.set(0);

  }


  public boolean checkForFuel() {

    return (fuelCellSensor.getVoltage() <= 1.0);
 
  }

  @Override
  public void periodic() {

    SmartDashboard.putBoolean("Intake Fuel Cell", checkForFuel());
    SmartDashboard.putNumber("Intake voltage", fuelCellSensor.getVoltage());
    // This method will be called once per scheduler run
  }
}
