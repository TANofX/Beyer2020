/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndicatorLights extends SubsystemBase {

  private static AddressableLED LEDStrip = null;
  private static AddressableLEDBuffer LEDBuffer;
  private static int nextPosition = 0;
  private int m_rainbowFirstPixelHue = 0;
  private int numPixels;
  private Revolver revolver = null;
  private Drives driveBase = null;
  private Limelight limelight = null;

  private DividedAddressableLEDBuffer theBuffer;

  private void initializeLEDStrip() {
    if (LEDStrip == null) {
      LEDStrip = new AddressableLED(Constants.LED_PORT);
      LEDBuffer = new AddressableLEDBuffer(Constants.LED_BUFFER_LENGTH);
      LEDStrip.setLength(LEDBuffer.getLength());
      LEDStrip.start();
    }
  }

  private DividedAddressableLEDBuffer getBuffer(int length) {
    if (nextPosition + length > LEDBuffer.getLength()) throw new IndexOutOfBoundsException(length);

    DividedAddressableLEDBuffer retVal = new DividedAddressableLEDBuffer(LEDBuffer, nextPosition, length);

    nextPosition = nextPosition + length;

    return retVal;
  }

  public static void setLights() {
    LEDStrip.setData(LEDBuffer);
  }

  public IndicatorLights(int numberOfPixels) {
  
    initializeLEDStrip();
    numPixels = numberOfPixels;
    theBuffer = getBuffer(numPixels);
  }

  public IndicatorLights(int numberOfPixels, Revolver revolver) {

    this(numberOfPixels);
    this.revolver = revolver;



  }

  public IndicatorLights(int numberOfPixels, Drives driveBase) {

    this(numberOfPixels);
    this.driveBase = driveBase;



  }
  public IndicatorLights(int numberOfPixels, Limelight limelight) {

    this(numberOfPixels);
    this.limelight = limelight;

  }


  public void turnLightsRed() {

    for(int i = 0; i < theBuffer.getLength(); i++) {

      theBuffer.setRGB(i, 255, 0, 0);

    }

  }

  public void turnLightsGreen() {

    for(int i = 0; i < theBuffer.getLength(); i++) {

      theBuffer.setRGB(i, 0, 255, 0);

    }


  }


  public void TurnLightsRainbow() {

    for (var i = 0; i < theBuffer.getLength(); i++) {
      
      final var hue = (m_rainbowFirstPixelHue + (i* 180 / theBuffer.getLength())) % 180;

      theBuffer.setHSV(i, hue, 255, 128);
   }
   
    m_rainbowFirstPixelHue += 3;

    m_rainbowFirstPixelHue %= 175;
  }

  public void countFuelCells(int numberOfFuelCells) {

    int hue = 117;
    if(numberOfFuelCells == 5) {

      hue = 255;

    }

    int  ballCountBuffer = (theBuffer.getLength() / 5);

    for (int i = 0; i < theBuffer.getLength(); i++) {
      if((i / ballCountBuffer == 4) && (numberOfFuelCells == 4)) {

        hue = 23;

      }
      if (i >= numberOfFuelCells * ballCountBuffer){

        theBuffer.setHSV(i, 0, 0, 0);

      }
      
      else {

        theBuffer.setHSV(i, hue, 255, 128);

      }
    }
  }

  @Override
  public void periodic() {

    if(driveBase != null) {
      SmartDashboard.putBoolean("Drive base boolean lights exist?", true);

      if(driveBase.isReversed()) {

        turnLightsRed();

      }
      else turnLightsGreen();

    }
    if(revolver != null) {

      countFuelCells(revolver.sumFuelCells());

    }
  }
}
