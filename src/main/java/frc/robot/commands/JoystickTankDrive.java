/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drives;

public class JoystickTankDrive extends CommandBase {
  private Joystick LeftFlightstick;
  private Joystick RightFlightstick;
  private Drives Drivebase;
  /**
   * Creates a new JoystickTankDrive.
   */
  public JoystickTankDrive(Joystick leftJoystick, Joystick rightJoystick, Drives drives) {
    LeftFlightstick = leftJoystick;
    RightFlightstick = rightJoystick;
    Drivebase = drives;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Drivebase);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double leftSpeed = 0.0;
    double rightSpeed = 0.0;
    leftSpeed = LeftFlightstick.getRawAxis(Constants.LEFT_AXIS) * -1.0;
    rightSpeed = RightFlightstick.getRawAxis(Constants.RIGHT_AXIS) * -1.0;
    if (Math.abs(leftSpeed) < Constants.DEAD_ZONE) {
      leftSpeed = 0.0;
    }
    if (Math.abs(rightSpeed) < Constants.DEAD_ZONE) {
      rightSpeed = 0.0;
    }
    Drivebase.tankDrive(leftSpeed, rightSpeed);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Drivebase.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
