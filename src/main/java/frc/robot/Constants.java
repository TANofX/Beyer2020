/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//need constant for collector stationary w/ ID 12 and shootershoot is 16* 19-A and 19-C
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

    //CAN ID's
    public static int PIGEON_IMU = 2; //Pigeon IMU
    public static int INTAKE_TRANSIT = 13;  //Spark D
    public static int INTAKE_OMNI_WHEEL = 14;  //Spark B
    public static int INTAKE_ROLLERS = 12;  //Spark Roller
    public static int LEFT_PRIMARY_MOTOR = 5;  //Falcon A
    public static int RIGHT_PRIMARY_MOTOR = 6;  //Falcon B
    public static int LEFT_SECONDARY_MOTOR = 4;  //Falcon C
    public static int RIGHT_SECONDARY_MOTOR = 7;  //Falcon D
    public static int PRIMARY_SHOOTER_MOTOR = 18;  //Falcon G
    public static int SECONDARY_SHOOTER_MOTOR = 19;  //Falcon H
    public static int HOOD_MOTOR = 17;  //Spark A
    public static int SHOOTER_TRANSIT = 16;  //Spark 19-A
    public static int CLIMBER_PRIMARY = 9;  //Talon E
    public static int CLIMBER_SECONDARY = 8;  //TAlon F
    public static int PCM = 15;   //PCM
    public static final int COLLECTOR_TRANSIT_MOTOR = 11;  //Spark-19-C
    public static final int REVOLVER_MOTOR_ID = 10;  //Spark C2

    // PCM slot's 
    public static int INTAKE_solenoid = 0;
    public static final int INTAKE_FOREWARD_SOLENOID = 1;
    public static final int INTAKE_REVERSE_SOLENOID = 0;

    //LED stuff
    public static final int LED_PORT = 0;
    public static final int LED_BUFFER_LENGTH = 55; // = the sum of all LED strip lengths on the robot //

    //Sensors
    public static final int INTAKE_FUEL_CELL_SENSOR = 0;
    public static final int FUEL_CELL_SENSOR_PORT = 2;
    public static final int REVOLVER_POSITION_SENSOR = 0;

    // RoboRIO input id for Limit switches
    public static final int REVOLVER_COLLECTION_POSITION = 0;

    //Motor Constants
    public static int FALCON_COUNTS_PER_REV = 2048;
    public static final double NEO550_COUNTS_PER_REV = 1.0; 

    //Speeds
    public static double SHOOTER_TRANSIT_SPEED = 0.9;
    public static double INTAKE_OMNI_MOTOR_SPEED = 4000; //changed from 0.75
    public static double INTAKE_TRANSIT1_SPEED = 2500; //0.05
    public static final double INTAKE_ROLLER_MOTOR_SPEED = 0.75;
    public static final double REVOLVER_CALIBRATION_SPEED = 1501.5;
    public static final double COLLECTOR_TRANSIT_MOTOR_SPEED = 2200;// was .25

    
    
    //DRIVE BASE
    public static double DRIVE_CURRENT_LIMIT = 40;
    public static double THRESHOLD_CURRENT = 120;
    public static double THRESHOLD_TIMEOUT = 1;
    public static double MINIMUM_TURN_RATE = 0.15;
    public static double DRIVE_GEAR_RATIO = 12.7272727;
    public static double DRIVE_WHEEL_CIRCUMFERENCE = 6.125 * Math.PI;

          
    //SHOOTER  
    public static double SHOOTER_MOTOR_GEAR_RATIO = 2.0;
    public static double HOOD_MOTOR_GEAR_RATIO = 300.0;
    public static double HOOD_MIN = -80.0;
    public static double HOOD_MAX = 0.0;
    public static double HOOD_ANGLE_THRESHOLD = 0.5;
    public static double SHOOTER_SPIN_ERROR = 500.0;  // variable you can be wrog for shooter errors


    //LIMELIGHT
    public static double LIMELIGHT_HEIGHT = 0.0;
    public static final int LIMELIGHT_ANGLE = 20;
    public static double HEIGHT_OF_TARGET = 96.0;

    
    //CLIMBER
    public static final double CLIMBER_MOTOR_GEAR_RATIO = 33.4;
    public static double CLIMBER_WHEEL_CIRCUMFERENCE = 3.5325;
    public static final double CLIMER_THRESHOLD_CURRENT = 120;
    public static final double CLIMBER_THRESHOLD_TIMEOUT = 0.5;


    //REVOLVER
    public static final double REVOLVER_GEAR_RATIO = (84.0 / 48.0)*100.0;
    public static final double REVOLVER_THRESHOLD = 1.0;
    public static final double REVOLVER_CURRENT_LIMIT = 40.0; 
    public static final int REVOLVER_SHOOTER_POSITION = REVOLVER_COLLECTION_POSITION + 2;



    //BUTTON INPUTS FOR XBOX CONTROLLER
    public static int XBOX_MOVE = 1;  //axis
    public static int XBOX_TURN = 4;  //axis
    public static int REVERSIT_TOGGLE = 5;
    public static int UP_INTAKE = 4;
    public static int DOWN_INTAKE = 3;
    public static int EXTAKE = 6;
    public static int RUN_INTAKE = 3; //Trigger
    public static int SLOW_DOWN = 2;  //Trigger
    public static int CANCEL = 7;
    public static int SPIN_REVOLVER = 1;
    public static final int REVOLVER_BACK = 2;

    //BUTTON INPUTS FOR FLIGHT STICK CONTROLLER
    public static int SHOOT = 1;
    public static int LINE_UP = 2;
    public static int TURN_ON_LIMELIGHT = 5;
    public static int RAISE_LOWER_CLIMBER = 1; //axis
    public static int ON_OFF_CLIMBER = 3;  //slider
    public static int STOP_SHOOTER = 12;
    public static int HIGH_SHOOTER_SPEED = 8;
  //  public static int MEDIUM_SHOOTER_SPEED = 9;
    public static int LOW_SHOOTER_SPEED = 10;
    public static int SUPER_SHOOTER_SPEED = 6;
    public static int CLOSE_GOAL = 7;
    public static int MEDIUM_GOAL =9;
    public static int FAR_GOAL = 11;

    //FLIGHT STICK POV BUTTON
    public static int HOOD_UP = 0;
    public static int HOOD_DOWN = 180;

    //Tan[x] Drive flight sticks
    public static final int LEFT_AXIS = 1;
    public static final int RIGHT_AXIS = 1;
	public static final double SENSOR_TRIGGER_VOLTAGE = 0.9;
	public static final int OH_GOD_PLEASE_STOP_DONT_KILL_ME_BUTTON = 8;


    //Imports for controllers on Driverstation Laptop
    public static int XBOX = 0;
    public static int STICK = 1;
    public static int STICK_2 = 2;

    //Dead zone for (Xbox) joystick i'mput
    public static double DEAD_ZONE = 0.15;
    
}