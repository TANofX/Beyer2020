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
import frc.robot.utils.JoystickUtils;

public class JoystickCurvatureDrive extends CommandBase {
  private Joystick xbox;
  private Drives driveBase; 

  /**
   * Creates a new JoystickCurvatureDrive.
   */
  public JoystickCurvatureDrive(Joystick xboxController, Drives drives) {
    xbox = xboxController;
    driveBase = drives;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveBase);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = 0.0;
    double turnRate = 0.0;
    speed = JoystickUtils.scaleDeadband(xbox.getRawAxis(Constants.XBOX_MOVE) * -1.0);
    turnRate = JoystickUtils.scaleDeadband(xbox.getRawAxis(Constants.XBOX_TURN));
    turnRate = turnRate/2.0;
    
    driveBase.curvatureDrive(speed, turnRate);

    
    //This SHOULD be the left trigger that is supposed to slow the robot down when held
    //It takes the trigger from 0-1 and cuts it in half so that we are going half the speed
    //It is basically the opposite of 2019's right trigger
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    driveBase.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
