/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pro.polaco.aurora;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


/**
 *
 * @author cassiano
 */
public class GPIO
{
    final GpioController gpio = GpioFactory.getInstance();

    // This is used to provision raspberry pi GPIO pins
    public void provision_pins()
    {
        GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,   // PIN NUMBER
                                                                  "My LED",           // PIN FRIENDLY NAME (optional)
                                                                  PinState.LOW);      // PIN STARTUP STATE (optional)
    }
}
