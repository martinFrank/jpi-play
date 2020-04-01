package com.github.martinfrank.jpiplay;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;

public class JPiPlay {

    private final GpioController gpio;

    private static final int PWM_RANGE = 1000;
    private static final int PWM_CLOCK = 500;

    public static void main(String[] args) {
        System.out.println("main...");
        new JPiPlay().start();
    }


    public JPiPlay() {
        gpio = GpioFactory.getInstance();
        Gpio.pwmSetMode(Gpio.PWM_MODE_BAL);
        Gpio.pwmSetRange(PWM_RANGE);
        Gpio.pwmSetClock(PWM_CLOCK);
    }

    public void start() {

        final GpioPinPwmOutput pwm = gpio.provisionPwmOutputPin(RaspiPin.GPIO_23);
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 11; i++) {
                    try {
                        double d = 0d + i / 10d;
                        int range = (int) (d * PWM_RANGE);
                        System.out.println("tick - range: " + range);
                        pwm.setPwm(range);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                shutDown();
            }
        }).start();
    }

    private void shutDown() {
        gpio.shutdown();
    }


}
