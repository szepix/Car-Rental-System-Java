package com.pap_car_rental;

import com.pap_car_rental.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Before;

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
    public void testBadPwdUser () {
        clickOn("#userName");
        write("mietek");
        clickOn("#userPwd");
        write("123");
        clickOn("#userButton");
        FxAssert.verifyThat("#invalidUser", LabeledMatchers.hasText("Invalid username\nor password."));
    }

    @Test
    public void testUserRegistered () {
        clickOn("#userName");
        write("mietek");
        clickOn("#userPwd");
        write("1234");
        clickOn("#userButton1");
        FxAssert.verifyThat("#invalidUser", LabeledMatchers.hasText("Already registered."));
    }

    @Test
    public void testToShortPwd () {
        clickOn("#userName");
        write("miet");
        clickOn("#userPwd");
        write("12");
        clickOn("#userButton1");
        FxAssert.verifyThat("#invalidUser", LabeledMatchers.hasText("Too short. Must be\nmin 4 chars long."));
    }

    @Test
    public void testBadChar () {
        clickOn("#userName");
        write("miet");
        clickOn("#userPwd");
        write("1234,4");
        clickOn("#userButton1");
        FxAssert.verifyThat("#invalidUser", LabeledMatchers.hasText("Cannot contain ','"));
    }

    @Test
    public void testBadPwdAdmin () {
        clickOn("#adminTab");
        clickOn("#adminName");
        write("mietek");
        clickOn("#adminPwd");
        write("123");
        clickOn("#adminButton");
        FxAssert.verifyThat("#invalidAdmin", LabeledMatchers.hasText("Invalid username or password."));
    }
}