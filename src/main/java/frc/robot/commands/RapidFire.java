/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.lang.annotation.Retention;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Revolver;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterSpeeds;
import frc.robot.subsystems.Limelight.LEDMode;

public class RapidFire extends CommandBase {
  private Shooter shooter;
  private Revolver revolver;
  private Limelight limelight;
  private int mightBeFinished = 0;
  private int initialDelay = 0;
  private int slotTracker = 0;
  private int finalSlot = 4;// Because Noah wanted it to be 4.

  private boolean nateProofed = false;
  /**
   * Creates a new Shoot.
   */
  public RapidFire(Shooter shooterSubsytem, Revolver revolverSubsteym, Limelight limelightSubsystes) {
    // Use addRequirements() here to declare subsystem dependencies.

    shooter = shooterSubsytem;
    revolver = revolverSubsteym;
    limelight = limelightSubsystes;

    addRequirements(shooter, revolver);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (!shooter.atSpeed()) {

      nateProofed = true;

    }
    else {
      nateProofed = false;

      shooter.shoot();
      slotTracker = revolver.currentPosition();
      finalSlot = slotTracker + 4;
      mightBeFinished = 0;
    }

    initialDelay = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

      if ((revolver.positionCheck() &&(slotTracker < finalSlot)) && initialDelay > 2){

        slotTracker++;
        revolver.rotateToPosition(slotTracker);
      }

      initialDelay++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (!nateProofed) {
    shooter.stopShoot();
    revolver.assumeEmpty();
    shooter.moveHood(0.0);
    revolver.stopTransit();
    shooter.spinPrimaryMotor(ShooterSpeeds.OFF);
    limelight.setLEDMode(LEDMode.OFF);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (nateProofed) return true;

    if (revolver.positionCheck() &&(slotTracker == finalSlot)){
     mightBeFinished++;
    }
    return mightBeFinished > 25;
  }
}
