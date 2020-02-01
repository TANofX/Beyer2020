/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {


	//CAN ID'S For the Falcons in the Drive Base
    public static int LEFT_PRIMARY_MOTOR = 2;
    public static int RIGHT_PRIMARY_MOTOR = 3;
    public static int LEFT_SECONDARY_MOTOR = 4;
    public static int RIGHT_SECONDARY_MOTOR = 5;

    //CAN ID'S For the two Falcons on shooter
    public static int PRIMARY_SHOOTER_MOTOR = 17;
    public static int SECONDARY_SHOOTER_MOTOR = 19;

    //CAN ID For hood Neo 550
    public static int HOOD_MOTOR = 8;
    
    //Turn Rate for Curvature Drive
    public static double MINIMUM_TURN_RATE = 0.15;
   
    //Voltage Limiting for Drive Base
    public static double DRIVE_CURRENT_LIMIT = 40;
    public static double THRESHOLD_CURRENT = 120;
    public static double THRESHOLD_TIMEOUT = 1;

    //Drive Base Stuff for InchesToTicksOnMotor
    public static double DRIVE_GEAR_RATIO = 12.7272727;
    public static double DRIVE_WHEEL_CIRCUMFERENCE = 6.125 * Math.PI;
    public static int DRIVE_MOTOR_TICKS_PER_REV = 2048;

    //Imports for controllers on Driverstation Laptop
    public static int STICK = 1;
    public static int XBOX = 0;

    //Button Imputs for XBOX Controller
    public static int XBOX_MOVE = 1;
    public static int XBOX_TURN = 4;

    //Dead zone for (Xbox) joystick i'mput
    public static double DEAD_ZONE = 0.15;

    //Tan[x] Drive flight sticks
    public static final int LEFT_AXIS = 1;
    public static final int RIGHT_AXIS = 1;
    
    //Limelight angle located on Shooter
    public static final int LIMELIGHT_ANGLE = 20;

    //Constants for the Revolver
    public static final int REVOLVER_MOTOR_ID = 8;
    public static final double REVOLVER_CURRENT_LIMIT = 40.0;
    public static final int REVOLVER_CALIBRATION_SWITCH_ID = 0;  // RoboRIO input id for calibration switch
    public static final int REVOLVER_COLLECTION_POSITION = 0;
    public static final int REVOLVER_SHOOTER_POSITION = REVOLVER_COLLECTION_POSITION + 2;
    public static final double REVOLVER_GEAR_RATIO = 84.0 / 48.0;

    public static final int NEO550_COUNTS_PER_REV = 42;
}
