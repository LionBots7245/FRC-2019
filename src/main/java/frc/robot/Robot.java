/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.Controller;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.Spark;
// import edu.wpi.first.wpilibj.Timer;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {


  WPI_TalonSRX FLeft = new WPI_TalonSRX(4);
  WPI_TalonSRX FRight = new WPI_TalonSRX(1);
  WPI_TalonSRX BLeft = new WPI_TalonSRX(3);
  WPI_TalonSRX BRight = new WPI_TalonSRX(2);
  WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(8);
  WPI_TalonSRX Left = new WPI_TalonSRX(6);
	WPI_TalonSRX Right = new WPI_TalonSRX(7);


  DifferentialDrive drive = new DifferentialDrive(FLeft, FRight);


  XboxController xboxController = new XboxController(2);
  Joystick joystick = new Joystick(0);
  //Joystick throttle = new Joystick(1);
  
	JoystickButton button1 = new JoystickButton(joystick,1);
  JoystickButton button2 = new JoystickButton(joystick,2);
  JoystickButton button3 = new JoystickButton(joystick,3);
  JoystickButton button7 = new JoystickButton(joystick,7);
  JoystickButton button8 = new JoystickButton(joystick,8);
  
  
  
  DifferentialDrive intakeDrive = new DifferentialDrive(Left, Right);



  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  boolean toggled = false;
  boolean toggledPressed = false;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
   //CameraServer.getInstance().startAutomaticCapture();
   new Thread(() -> {
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
    camera.setResolution(160, 120);

    UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(1);
    camera1.setResolution(160, 120);
    
    CvSink cvSink = CameraServer.getInstance().getVideo();
    CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
    
    
    Mat source = new Mat();
    Mat output = new Mat();
    
    while(!Thread.interrupted()) {
        cvSink.grabFrame(source);
        Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
        outputStream.putFrame(output);
    }
}).start();
     
    BLeft.follow(FLeft);
    BRight.follow(FRight);

    
  }
                        
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
       /* drive.arcadeDrive(joystick.getY(),joystick.getTwist()/2);


        if(button2.get())
          {
            intakeDrive.tankDrive(.75,.75);
          }
          else if(button1.get())
          {
            intakeDrive.tankDrive(-.5, -.5);
          }else 
          {
            intakeDrive.stopMotor();
          }
    
          if(button7.get())
        {
          elevatorMotor.set(0.8);
        }
        else if(button8.get())
        {
          elevatorMotor.set(-0.8);
        }
        else
        {
          elevatorMotor.set(0);
        }*/
        break;

        case kDefaultAuto:
      default:
        this.teleopPeriodic();
        // Put default auto code here

        // drive.arcadeDrive(joystick.getY(),joystick.getTwist()*3/4);


        // if(button2.get()) {
        //   intakeDrive.tankDrive(.75,.75);
        // }

        // if(button1.get()) {
        //   intakeDrive.stopMotor();
        //   intakeDrive.tankDrive(-.5, -.5);
        //   Timer.delay(0.5);
        //   intakeDrive.stopMotor();

        // }
          // else if(button1.get())
          // {
          //   intakeDrive.tankDrive(-.5, -.5);
          // }else 
          // {
          //   intakeDrive.stopMotor();
          // }
    
        //   if(button7.get())
        // {
        //   elevatorMotor.set(1);
        // }
        // else if(button8.get())
        // {
        //   elevatorMotor.set(-1);
        // }
        // else
        // {
        //   elevatorMotor.set(0);
        // }

        // break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */


    //last years circle map
   /*
  public double circleMap(double input){
    
    double k = (1.95 - 0.35)/1.3;
    double R = Math.pow(1 + Math.pow((1-k),4), 0.25);
		
		return Math.signum(input)*(k - Math.sqrt(Math.pow(R, 2) - Math.pow(input, 2)));
	}
  */
  
//new circle map

  public double circleMap(double input){
    
    double k = 1.34423076923;
    double R = 1.09423076923;
		
		return Math.signum(input)*( - Math.sqrt(R * R - input * input) + k);
	}
  

	public double cubicMap(double input) {
		
		double k = 1.29996410157;
		double R = -1.00891701994;
		
		return Math.signum(input)*(k + Math.pow(Math.pow(R, 3) + Math.pow(Math.abs(input),
				3),1/3));
	}
	@Override
  public void teleopPeriodic() {

    drive.arcadeDrive(joystick.getY(),joystick.getTwist()/1.5);

    // if(button2.get()) {
    //   intakeDrive.tankDrive(.75,.75);
    // }

    // if(button1.get()) {
    //   Timer.delay(1.0);
    //   intakeDrive.tankDrive(-.5, -.5);
    //   intakeDrive.stopMotor();

    // }

      //testing toggle:

      
      updateToggle();

      if (toggled && !button1.get()){
        System.out.println("Intake");
        intakeDrive.tankDrive(.75,.75);
      }else if (button1.get()){
        toggled = false;
        intakeDrive.tankDrive(-.75, -.75);
        System.out.println("out");
      }
      else{
        intakeDrive.stopMotor();
        // intakeDrive.tankDrive(-.75, -.75);2edv
        // Timer.delay(2);v
        // intakeDrive.stopMotor();
      }


      

    /*if(button3.get())
      {
        intakeDrive.tankDrive(.75,.75);
      }
      else if(button1.get())
      {
        intakeDrive.tankDrive(-.75, -.75);  
      }else 
      {
        intakeDrive.stopMotor();
      }
      */

      if(button7.get())
		{
			elevatorMotor.set(0.5);
		}
    else if(button8.get())
		{
			elevatorMotor.set(0.1);
		}
		else
		{
			elevatorMotor.set(0);
    } 


    
    

    
      
      
      //elevatorMotor.set((throttle.getTwist() + 1)/2);

      //throttle 4+5
      //4 up
      //5 down
      

  
    
  //System.out.println(throttle.getTwist());
  //System.out.println(button1.get());
  //System.out.println(button2.get());
 // drive.arcadeDrive(xboxController.getY(),xboxController.getX());
  /*System.out.println(joystick.getY());
  System.out.println(joystick.getTwist());
  System.out.println(xboxController.getY());
  System.out.println(xboxController.getX());*/
  }
  

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void updateToggle(){
    if (button3.get()){
      if (!toggledPressed){
        toggled = !toggled;
        toggledPressed = true;
      }
      
    } else{
      toggledPressed = false;
    }
  }
}
