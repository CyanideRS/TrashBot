// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_robotDrive;
  private final XboxController m_driverController = new XboxController(0);

  private final CANSparkMax m_leftFrontMotor = new CANSparkMax(1, CANSparkLowLevel.MotorType.kBrushed);
  private final CANSparkMax m_leftRearMotor = new CANSparkMax(2, CANSparkLowLevel.MotorType.kBrushed);
  private final CANSparkMax m_rightFrontMotor = new CANSparkMax(3, CANSparkLowLevel.MotorType.kBrushed);
  private final CANSparkMax m_rightRearMotor = new CANSparkMax(4, CANSparkLowLevel.MotorType.kBrushed);


  @Override
  public void robotInit() {
    SendableRegistry.addChild(m_robotDrive, m_leftFrontMotor);
    SendableRegistry.addChild(m_robotDrive, m_rightFrontMotor);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightFrontMotor.setInverted(false);
    m_rightRearMotor.setInverted(false);
    m_leftRearMotor.follow(m_leftFrontMotor);
    m_rightRearMotor.follow(m_rightFrontMotor);

    m_robotDrive = new DifferentialDrive(m_leftFrontMotor::set, m_rightFrontMotor::set);
  }

  @Override
  public void teleopPeriodic() {
    m_robotDrive.tankDrive(-m_driverController.getLeftY(), -m_driverController.getRightY());
  }
}
