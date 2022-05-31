package com.pap_car_rental;

import com.pap_car_rental.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Before;

import static com.pap_car_rental.App.db;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.hamcrest.MatcherAssert.assertThat;
import org.testfx.matcher.control.LabeledMatchers;

import java.io.IOException;
import java.util.ArrayList;

public class UserControllerTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        ArrayList<Client> client_list = db.listClients();
        client_list.forEach(e -> {
            boolean isUser = e.login.equals("mietek");
            if (isUser) {
                App.currentUser = e;
                App.isUser = true;
            }
        });
        System.out.println("fake login");
//        Parent mainNode = FXMLLoader.load(App.class.getResource("user.fxml"));
        Parent mainNode = FXMLLoader.load(App.class.getResource("user.fxml"));
        System.out.println("window loaded");
        stage.setScene(new Scene(mainNode));
        System.out.println("scene set");
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
    public void testBadPwdUser () throws IOException {
        // clickOn("#userNameDisplay");
    }
}