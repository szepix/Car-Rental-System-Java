package com.pap_car_rental;

import com.pap_car_rental.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert.*;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("main_menu.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUp () throws Exception {
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testInput () {
        clickOn("#userName");
        write("This is a test!");
    }
}