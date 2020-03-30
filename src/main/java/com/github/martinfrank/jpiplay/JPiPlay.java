package com.github.martinfrank.jpiplay;

import com.pi4j.io.gpio.*;

import java.io.IOException;

public class JPiPlay {

    private final GpioController gpio;

    public static void main(String[] args) {
        try {
            System.out.println("main...");
            JPiPlay play = new JPiPlay();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public JPiPlay() throws IOException {
        gpio = GpioFactory.getInstance();

        final GpioPinDigitalOutput myPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01,   // PIN NUMBER
                "PIN01",           // PIN FRIENDLY NAME (optional)
                PinState.LOW);      // PIN STARTUP STATE (optional)
        new Thread(new Runnable() {

            private boolean toogle = false;
            @Override
            public void run() {
                for(int i = 0; i < 11; i++){
                    try {
                        System.out.println("tick");
                        myPin.setState(toogle);
                        toogle = !toogle;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
