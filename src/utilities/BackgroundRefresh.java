/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 *
 * @author NelsonDelaTorre
 */

public class BackgroundRefresh {
    public int refreshRate = 1;    
    public BackgroundRefresh() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {    
            /**
             * Continuous background process here
             * will update every int refreshRate;
             */            
            System.out.println("From BackgroundRefresh timer");
        }),
            new KeyFrame(Duration.seconds(refreshRate))
        );        
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
