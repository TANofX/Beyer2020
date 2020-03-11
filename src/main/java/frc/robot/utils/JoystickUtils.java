/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utils;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class JoystickUtils {

 public static double scaleDeadband(double input){

    if (Math.abs(input) < Constants.DEAD_ZONE) {

        return 0.0;

    }
    else return Math.signum(input) * (Math.abs(input) - Constants.DEAD_ZONE) / (1.0 - Constants.DEAD_ZONE);
 }

}
