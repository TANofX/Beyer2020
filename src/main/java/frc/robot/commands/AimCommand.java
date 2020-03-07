/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drives;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AimCommand extends PIDCommand {
  /**
   * Creates a new AimCommand.
   */
  private Limelight limelight;
  private Shooter shooter;
  private Drives drives;

  public AimCommand(Limelight eyes, Shooter gun, Drives foot) {
    super(
        // The controller that the command will use
        new PIDController(0.005, 0.00001, 0),
        // This should return the measurement
        () -> eyes.getTargetingValue("tx"),
        // This should return the setpoint (can also be a constant)
        () -> 0,
        // This uses the output
        output -> {
          foot.tankDrive(output, -1.0 * output);
          // Use the output here
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
