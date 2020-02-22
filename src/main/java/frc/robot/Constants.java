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

    //CAN ID For Intake Transit (Spark D)
    public static int INTAKE_TRANSIT = 13;

    //CAN ID for Intake Omni wheels (Spark B)
    public static int INTAKE_OMNI_WHEEL = 14;

    //CAN ID For Intake Rollers (Talon AK)
    public static int INTAKE_ROLLERS = 11;
    
	//CAN ID'S For the Falcons (A,B,C,D) in the Drive Base
    public static int LEFT_PRIMARY_MOTOR = 5;
    public static int RIGHT_PRIMARY_MOTOR = 6;
    public static int LEFT_SECONDARY_MOTOR = 4;
    public static int RIGHT_SECONDARY_MOTOR = 7;

     //CAN ID'S For the two Falcons on shooter (G,H)
     public static int PRIMARY_SHOOTER_MOTOR = 18;
     public static int SECONDARY_SHOOTER_MOTOR = 19;
 
     //CAN ID For hood Neo 550 (Spark A)
     public static int HOOD_MOTOR = 17;

     //CAN Shooter Transit  neo 550
     public static int SHOOTER_TRANSIT = 12;

     //Can id for the two Climber falcons (Talons E and F)
     public static int CLIMBER_PRIMARY = 9;
     public static int CLIMBER_SECONDARY = 8;

     //Can id for pcm
    public static int PCM = 15;

     //pcm slot of the solenoid on the climber
    public static int CLIMBER_Solenoid = 1 ;

     //climber wheel(falcon i)
   

     //speed for the shooter Transit motor
     public static double SHOOTER_TRANSIT_SPEED = 0.5;

     //the variable that you can be wrong for shooter spinny motors
     public static double SHOOTER_SPIN_ERROR = 1.0;
    
    //Voltage Limiting for Drive Base
    public static double DRIVE_CURRENT_LIMIT = 40;
    public static double THRESHOLD_CURRENT = 120;
    public static double THRESHOLD_TIMEOUT = 1;
        
    //Turn Rate for Curvature Drive
    public static double MINIMUM_TURN_RATE = 0.15;
    
    //Drive Base Stuff for InchesToTicksOnMotor
    public static double DRIVE_GEAR_RATIO = 12.7272727;
    public static double DRIVE_WHEEL_CIRCUMFERENCE = 6.125 * Math.PI;
    public static int FALCON_COUNTS_PER_REV = 2048;
       
    //Constants for the Revolver
    public static final int REVOLVER_MOTOR_ID = 8;
    public static final double REVOLVER_CURRENT_LIMIT = 40.0;

    // RoboRIO input id for calibration switch
    public static final int REVOLVER_COLLECTION_POSITION = 0;
    public static final int REVOLVER_SHOOTER_POSITION = REVOLVER_COLLECTION_POSITION + 2;


    //Button import for spinning the revolver
    public static final int SPIN_REVOLVER = 4;

    //Button import for spinning the shooter
    public static final int SPIN_SHOOTER_SPIN = 3;
    
    public static final int NEO550_COUNTS_PER_REV = 42;

    //Gear Ratios for shooter Motors
    public static double SHOOTER_MOTOR_GEAR_RATIO = 2.0;
    public static double HOOD_MOTOR_GEAR_RATIO = 300.0;

    //contants for hood angles
    public static double HOOD_MIN = 0.0;
    public static double HOOD_MAX = 80.0;

    //threshold for hood angle
    public static double HOOD_ANGLE_THRESHOLD = 0.5;
    
    //constants for Limelight
    public static double LIMELIGHT_HEIGHT = 0.0;
    public static final int LIMELIGHT_ANGLE = 20;
    public static double HEIGHT_OF_TARGET = 96.0;

    //Imports for controllers on Driverstation Laptop
    public static int STICK = 1;
    public static int STICK_2 = 2;
    public static int XBOX = 0;

    //imports for the two ReverseIt buttons
    public static int REVERSIT_TRUE = 5;
    public static int REVERSEIT_FALSE = 6;

    //Button Imputs for XBOX Controller
    public static int XBOX_MOVE = 1;
    public static int XBOX_TURN = 4;

    //Dead zone for (Xbox) joystick i'mput
    public static double DEAD_ZONE = 0.15;

    //Tan[x] Drive flight sticks
    public static final int LEFT_AXIS = 1;
    public static final int RIGHT_AXIS = 1;
    
    //Climber Motor Information
    public static final double CLIMBER_MOTOR_GEAR_RATIO = 33.4;
    public static double CLIMBER_WHEEL_CIRCUMFERENCE = 3.5325;
    public static final double CLIMER_THRESHOLD_CURRENT = 120;
    public static final double CLIMBER_THRESHOLD_TIMEOUT = 0.5;

    //Revolver Motor Information
    public static final double REVOLVER_GEAR_RATIO = (84.0 / 48.0)*100.0;
    public static final int REVOLVER_WHEEL_CIRCUMFERENCE = 1;

    //revolver analog chanal
    public static final int FUEL_CELL_SENSOR_PORT = 0;


    
}