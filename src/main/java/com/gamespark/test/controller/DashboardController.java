/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamespark.test.controller;

import com.gamespark.test.platform.JavaFXPlatform;
import com.gamesparks.sdk.GS;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kairos.core.Activity;
import org.kairos.core.FragmentManager;

/**
 *
 * @author marcus
 */
public class DashboardController extends Activity {

    @FXML
    private BorderPane borderPane;

    private ResourceBundle bundle;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate() {
        super.onCreate(); //To change body of generated methods, choose Tools | Templates.
        setContentView(getClass().getResource("/fxml/Dashboard.fxml"));
        
        GS gs = JavaFXPlatform.initialise(this, "KEY", "SECRET", false, false);
        gs.start();
    }

    @FXML
    private void performCloseWindow(ActionEvent event) {
       
    }

    @FXML
    private void performMinimizeWindow(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }

}
