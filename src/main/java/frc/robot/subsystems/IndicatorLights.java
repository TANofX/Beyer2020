/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
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
  private boolean reversed = true;
  private int cellCount = 5;
  private int angle = 0;
  private boolean targetVisible = true;
  private static boolean needsUpdate = true;

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
    return getBuffer(length, false);
  }

  private DividedAddressableLEDBuffer getBuffer(int length, boolean reverse) {
    if (nextPosition + length > LEDBuffer.getLength()) throw new IndexOutOfBoundsException(length);

    DividedAddressableLEDBuffer retVal = new DividedAddressableLEDBuffer(LEDBuffer, nextPosition, length, reverse);

    nextPosition = nextPosition + length;

    return retVal;
  }

  public static void setLights() {
    if (needsUpdate) {
      LEDStrip.setData(LEDBuffer);
      needsUpdate = false;
    }
  }

  public IndicatorLights(int numberOfPixels, boolean reverse) {
  
    initializeLEDStrip();
    numPixels = numberOfPixels;
    theBuffer = getBuffer(numPixels, reverse);
  }

  public IndicatorLights(int numberOfPixels, Revolver revolver, boolean reverse) {

    this(numberOfPixels, reverse);
    this.revolver = revolver;
  }

  public IndicatorLights(int numberOfPixels, Drives driveBase, boolean reverse) {

    this(numberOfPixels, reverse);
    this.driveBase = driveBase;
  }

  public IndicatorLights(int numberOfPixels, Limelight limelight, boolean reverse) {

    this(numberOfPixels, reverse);
    this.limelight = limelight;

  }


  public void turnLightsRed() {

    for(int i = 0; i < theBuffer.getLength(); i++) {

      theBuffer.setRGB(i, 255, 0, 0);

    }
    needsUpdate = true;
  }

  public void turnLightsGreen() {

    for(int i = 0; i < theBuffer.getLength(); i++) {

      theBuffer.setRGB(i, 0, 255, 0);

    }

    needsUpdate = true;

  }


  public void TurnLightsRainbow() {

    for (var i = 0; i < theBuffer.getLength(); i++) {
      
      final var hue = (m_rainbowFirstPixelHue + (i* 180 / theBuffer.getLength())) % 180;

      theBuffer.setHSV(i, hue, 255, 128);
   }
   
    m_rainbowFirstPixelHue += 3;

    m_rainbowFirstPixelHue %= 175;

    needsUpdate = true;
  }

  public void countFuelCells(int numberOfFuelCells) {

    int hue = 117;
    if(numberOfFuelCells == 5) {

      hue = 255;

    }

    int  ballCountBuffer = (theBuffer.getLength() / 5);

    for (int i = 0; i < theBuffer.getLength(); i++) {
      if((i / ballCountBuffer == 3) && (numberOfFuelCells == 4)) {

        hue = 23;

      }
      if (i >= numberOfFuelCells * ballCountBuffer){

        theBuffer.setHSV(i, 0, 0, 0);

      }
      
      else {

        theBuffer.setHSV(i, hue, 255, 128);

      }
    }
    needsUpdate = true;
  }

public void displayAimPattern(boolean isVisible, int angle) {
  if (isVisible) {
    double scale = (double)angle / 60.0;
    int position = (int)Math.floor((scale + 0.5) * LEDBuffer.getLength());
    for (int i = 0; i < theBuffer.getLength(); i++) {
      if (i == position) {
        theBuffer.setRGB(i, 53, 94, 59);
      } else if ((theBuffer.getLength() % 2 == 0) && ((i == position + 1) && (angle == 0))) {
        theBuffer.setRGB(i, 53, 94, 59);
      } else {
        theBuffer.setRGB(i, 0, 0, 0);
      }
    }
  } else {
    for (int i = 0; i < theBuffer.getLength(); i++) {
      theBuffer.setRGB(i, 0, 0, 0);
    }
  }

  needsUpdate = true;
}

  @Override
  public void periodic() {

    if(driveBase != null) {
      if (driveBase.isReversed() != reversed) {
        reversed = driveBase.isReversed();
          if(reversed)
            turnLightsRed();
          else
            turnLightsGreen();
        }
    }

    if (revolver != null) {
      if (cellCount != revolver.sumFuelCells()) {
        cellCount = revolver.sumFuelCells();
        countFuelCells(cellCount);
      }
    }

    if (limelight != null) {
      if ((targetVisible != limelight.targetVisible()) || (angle != Math.round(limelight.getOffset()))) {
        targetVisible = limelight.targetVisible();
        angle = (int)Math.round(limelight.getOffset());

        displayAimPattern(targetVisible, angle);
      }
    }
  }
}
