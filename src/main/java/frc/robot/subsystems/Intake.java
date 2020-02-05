/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

TalonSRX intakeRollerMotor;
CANSparkMax intakeOmniMotor;
CANSparkMax intakeTransitMotor;


  public Intake() {

    intakeRollerMotor = new TalonSRX(Constants.INTAKE_ROLLERS);
    intakeOmniMotor = new CANSparkMax(Constants.INTAKE_OMNI_WHEEL, MotorType.kBrushless);
    intakeTransitMotor = new CANSparkMax(Constants.INTAKE_TRANSIT, MotorType.kBrushless);


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
