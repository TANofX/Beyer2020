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

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

TalonSRX intakeRollerMotor;
CANSparkMax intakeOmniMotor;
CANSparkMax intakeTransitMotor1;
Solenoid  collectorArm;




  public Intake() {

    intakeRollerMotor = new TalonSRX(Constants.INTAKE_ROLLERS);
    intakeOmniMotor = new CANSparkMax(Constants.INTAKE_OMNI_WHEEL, MotorType.kBrushless);
    intakeTransitMotor1 = new CANSparkMax(Constants.INTAKE_TRANSIT, MotorType.kBrushless);
    collectorArm = new Solenoid(Constants.PCM ,Constants.INTAKE_solenoid);

    configureTalon(intakeRollerMotor);

  }

  private void configureTalon(TalonSRX talon) {

    talon.setNeutralMode(NeutralMode.Coast);

  }

  public void moveRollerDown() {

    collectorArm.set(true);

  }

  public void moveRollerUp() {

    collectorArm.set(false);

  }


  private void spinRoller() {

    if(collectorArm.get() == true) intakeRollerMotor.set(ControlMode.PercentOutput, 0.25);

  }

  private void activateIntake() {

    if(collectorArm.get() == true) { 
    intakeRollerMotor.set(ControlMode.PercentOutput, Constants.INTAKE_ROLLER_MOTOR_SPEED);
    intakeOmniMotor.set(Constants.INTAKE_OMNI_MOTOR_SPEED);
    intakeTransitMotor1.set(Constants.INTAKE_TRANSIT1_SPEED);
    }

  }

  public void activateExtake() {

    if(collectorArm.get() == true) 
    intakeRollerMotor.set(ControlMode.PercentOutput, Constants.INTAKE_ROLLER_MOTOR_SPEED * -1.0);
    intakeOmniMotor.set(Constants.INTAKE_OMNI_MOTOR_SPEED * -1.0);
    intakeTransitMotor1.set(Constants.INTAKE_TRANSIT1_SPEED * -1.0);

  }

  public void stopIntake() {

    intakeRollerMotor.set(ControlMode.PercentOutput, 0);
    intakeOmniMotor.stopMotor();
    intakeTransitMotor1.stopMotor();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
