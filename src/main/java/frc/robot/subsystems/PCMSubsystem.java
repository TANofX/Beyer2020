/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PCMSubsystem extends SubsystemBase {
  /**
   * Creates a new PCMSubsystem.
   */
  private Compressor compressor = new Compressor(0);

  public PCMSubsystem() {

  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Compressor Enabled", compressor.enabled());
    SmartDashboard.putBoolean("Pressure Switch", compressor.getPressureSwitchValue());
    // This method will be called once per scheduler run
  }
}
