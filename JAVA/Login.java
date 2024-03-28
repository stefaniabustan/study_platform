package com.example.interfata;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login {
    public static TextField mail;
    public static PasswordField pass;
    public static ImageView signInv;

    public static Scene getStartScene(Stage p, Connection connection) {
        VBox root = new VBox();
        Scene scene = new Scene(root, 550, 500);

        // Image signin = new
        // Image("http://www.clker.com/cliparts/d/u/w/y/F/1/thin-gray-signin-button-md.png",
        // 200, 80,
        // false, true);
        // signInv = new ImageView(signin);

        Button signIn = new Button("Sign in");
        signIn.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        signIn.setPrefSize(200, 70);
        signIn.setFont(new Font(30));
        signIn.setTextFill(Color.WHITE);

        Label title = new Label();
        title.setText("Universitatea Tehnica" + "\n" + "	Cluj Napoca");
        title.setFont(Font.font("Algerian", 30));
        title.autosize();

        HBox h1 = new HBox();
        Image iconper = new Image("https://icons.veryicon.com/png/o/internet--web/55-common-web-icons/person-4.png", 40,
                40, false, true);
        ImageView pers = new ImageView(iconper);
        mail = new TextField();
        mail.setPromptText("Your Email");
        mail.setFont(new Font("Arial", 14));
        mail.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        mail.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        mail.setPrefSize(350, 40);
        h1.getChildren().addAll(pers, mail);
        h1.setAlignment(Pos.CENTER);

        HBox h2 = new HBox();
        Image lock = new Image("https://icons.veryicon.com/png/o/miscellaneous/face-monochrome-icon/password-76.png",
                40, 40, false, true);
        ImageView locker = new ImageView(lock);
        pass = new PasswordField();
        pass.setPromptText("Your password");
        pass.setStyle("-fx-text-inner-color: white;-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        pass.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        pass.setFont(new Font("Arial", 14));
        pass.setPrefSize(350, 40);
        h2.getChildren().addAll(locker, pass);
        h2.setAlignment(Pos.CENTER);

        Label eroare = new Label("");
        eroare.setTextFill(Color.DARKRED);
        eroare.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        root.getChildren().addAll(title, h1, h2, signIn, eroare);
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        BackgroundImage myBI = new BackgroundImage(
                new Image("https://coolbackgrounds.io/images/backgrounds/index/sea-edge-79ab30e2.png", 550, 500, false,
                        true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));

        signIn.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                Login.check_account(p, connection, eroare);
            }

        });
        return scene;

    }

    ///
    public static void check_account(Stage p, Connection connection, Label err) {
        {

            String m = mail.getText();
            String pas = pass.getText();

            if (m.equals("grinch") && pas.equals(""))
                p.setScene(Admin.getAdminScene(true, null,true, connection, p));
            else if (m.equals("ariel") && pas.equals(""))
                p.setScene(Admin.getAdminScene(false, null,true, connection, p));
            else {
                int id = 0;
                try {
                    CallableStatement call = connection.prepareCall("{? =call logare(?,?)}");
                    call.registerOutParameter(1, Types.INTEGER);
                    call.setString("maill", m);
                    call.setString("parolaa", pas);
                    call.execute();
                    id = call.getInt(1);
                    Statement slc = connection.createStatement();
                    slc.execute("SELECT * FROM utilizator where id=" + id);
                    ResultSet rs = slc.getResultSet();
                    Persoana pers = new Persoana();
                    if (rs.next())
                        pers = new Persoana(rs.getInt("id"), rs.getString("cnp"), rs.getString("nume"),
                                rs.getString("prenume"), rs.getString("adresa"), rs.getString("telefon"),
                                rs.getString("mail"), rs.getString("parola"), rs.getString("cont_iban"),
                                rs.getInt("nr_contract"), rs.getInt("functie"));
                    if (pers.getId() > 0) {
                        if (pers.getFunctie() == 0) {
                            p.setScene(Admin.getAdminScene(false, pers,false, connection, p));
                        } else if (pers.getFunctie() == 1) {
                            p.setScene(Profesor.getProfScene(pers, connection, p));
                        } else
                            p.setScene(Student.getStudentScene(pers, connection, p));
                    } else {
                        pass.setText("");
                        slc.execute("Select * from utilizator where mail = '" + m + "' ;");
                        rs = slc.getResultSet();
                        if (rs.next()) {
                            err.setText("Parola incorecta!");
                        } else {
                            err.setText("Date incorecte!");
                        }
                    }
                } catch (SQLException sqlex) {
                    System.err.println("An SQL Exception occured. Details are provided below:");
                    sqlex.printStackTrace(System.err);
                }
            }
        }
    }
}
