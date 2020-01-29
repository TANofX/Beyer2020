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
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
 
  private TalonFX primaryShooterTalonFX;
  private TalonFX secondaryShooterTalonFX;
 
  /**
   * Creates a new Shooter.
   */
  public Shooter() {

    primaryShooterTalonFX = new TalonFX(Constants.PRIMARY_SHOOTER_MOTOR);
    secondaryShooterTalonFX = new TalonFX(Constants.SECONDARY_SHOOTER_MOTOR);

    secondaryShooterTalonFX.follow(primaryShooterTalonFX);

    configureTalon(primaryShooterTalonFX);
    configureTalon(secondaryShooterTalonFX);

  }

  private void configureTalon(TalonFX talon) {

    talon.setNeutralMode(NeutralMode.Coast);

  }

  public double getprimaryShooterSpeed() {

    return primaryShooterTalonFX.getSelectedSensorVelocity(0);

  }

  public double getSecondaryShooterSpeed() {

    return secondaryShooterTalonFX.getSelectedSensorVelocity(0);

  }

  public void stop() {

    primaryShooterTalonFX.set(ControlMode.PercentOutput, 0);
    secondaryShooterTalonFX.set(ControlMode.PercentOutput, 0);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
