package com.example.interfata;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Profesor extends Persoana {
    static VBox dreapta;
    static HBox tot;
    static ObservableList<Integer> iduri;
    static ObservableList<Integer> iduri2;
    static ObservableList<String> num;
    static ObservableList<String> nume;
    static ObservableList<String> prenume;
    static int index;
    static int index2;
    static int[] id_cursuri;
    static int[] id_studenti;
    static int selectat = 0;

    public static Scene getProfScene(Persoana pers, Connection connection, Stage p) {

        dreapta = new VBox();
        dreapta = Student.profil(pers);
        tot = new HBox();
        VBox left = Profesor.left(pers, connection, p);
        tot.getChildren().addAll(left, dreapta);
        tot.setAlignment(Pos.CENTER_LEFT);
        tot.setPadding(new Insets(0, 0, 0, 0));

        BackgroundImage myBI = new BackgroundImage(
                new Image("https://coolbackgrounds.io/images/backgrounds/index/sea-edge-79ab30e2.png", 1920, 1080,
                        false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        tot.setBackground(new Background(myBI));

        Scene scene = new Scene(tot, 1920, 1080);
        return scene;
    }

    @SuppressWarnings("unchecked")
    public static VBox left(Persoana pers, Connection connection, Stage p) {
        VBox root = new VBox();

        HBox top = new HBox();
        Image ceva = new Image("https://i.pinimg.com/originals/7e/9a/b2/7e9ab2b0d8b4a110e75cfc4a060e2474.png", 100, 100,
                false, true);
        ImageView c = new ImageView(ceva);

        Label title = new Label("Profesor");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 50));
        top.getChildren().addAll(c, title);
        top.setAlignment(Pos.CENTER_LEFT);
        top.setSpacing(30);

        Button profil = new Button("Profil");
        profil.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        profil.setMaxSize(400, 100);
        profil.setFont(new Font(30));
        profil.setTextFill(Color.WHITE);

        Button curs = new Button("Cursuri");
        curs.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        curs.setMaxSize(400, 100);
        curs.setFont(new Font(30));
        curs.setTextFill(Color.WHITE);

        Button student = new Button("Studenti");
        student.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        student.setMaxSize(400, 100);
        student.setFont(new Font(30));
        student.setTextFill(Color.WHITE);

        Button calendar = new Button("Calendar");
        calendar.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        calendar.setMaxSize(500, 100);
        calendar.setFont(new Font(30));
        calendar.setTextFill(Color.WHITE);
        root.setPadding(new Insets(10, 10, 0, 10));

        Button logoutbtn = new Button("Sign out");
        logoutbtn.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        logoutbtn.setMaxSize(170, 100);
        logoutbtn.setFont(new Font(20));
        logoutbtn.setTextFill(Color.WHITE);
        VBox jos = new VBox();
        jos.getChildren().add(logoutbtn);
        jos.setPadding(new Insets(230, 0, 10, 0));
        jos.setAlignment(Pos.BOTTOM_LEFT);

        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(25, 10, 10, 10));
        root.setSpacing(30);
        root.setStyle("-fx-background-color:#ccccff;");
        root.getChildren().addAll(top, profil, curs, student, calendar, jos);

        profil.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                tot.getChildren().remove(dreapta);
                dreapta = Student.profil(pers);
                tot.getChildren().add(dreapta);
            }
        });
        curs.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                tot.getChildren().remove(dreapta);
                dreapta = Profesor.studSauCurs(false, pers, connection, p);
                tot.getChildren().add(dreapta);
            }
        });
        calendar.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                tot.getChildren().remove(dreapta);
                dreapta = Profesor.calendar(pers, false, connection);
                tot.getChildren().add(dreapta);
            }
        });
        student.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                tot.getChildren().remove(dreapta);
                dreapta = Profesor.studSauCurs(true, pers, connection, p);
                tot.getChildren().add(dreapta);
            }
        });
        logoutbtn.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                p.setScene(Login.getStartScene(p, connection));
            }
        });

        return root;
    }

    @SuppressWarnings("unchecked")
    public static VBox studSauCurs(boolean stud, Persoana pers, Connection connection, Stage p) {
        HBox root = new HBox();

        ObservableList<String> cursuri = FXCollections.observableArrayList("MS", "BD", "POO");
        ObservableList<String> studenti = FXCollections.observableArrayList("Bogdan", "Stefania", "...");

        ListView lista = new ListView();
        lista.setPrefSize(400, 500);
        ListView lista2 = new ListView();
        lista2.setPrefSize(400, 500);

        Label titlu = new Label();
        titlu.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        HBox t1 = new HBox();
        Label n1txt = new Label("Curs:");
        n1txt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField n1f = new TextField();
        n1f.setPromptText("...");
        n1f.setFont(new Font("Arial", 14));
        n1f.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        n1f.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        n1f.setPrefSize(70, 30);
        t1.getChildren().addAll(n1txt, n1f);
        t1.setSpacing(20);
        t1.setAlignment(Pos.CENTER);

        HBox t2 = new HBox();
        Label n2txt = new Label("Seminar:");
        n2txt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField n2f = new TextField();
        n2f.setPromptText("...");
        n2f.setFont(new Font("Arial", 14));
        n2f.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        n2f.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        n2f.setPrefSize(70, 30);
        t2.getChildren().addAll(n2txt, n2f);
        t2.setSpacing(20);
        t2.setAlignment(Pos.CENTER);

        HBox t3 = new HBox();
        Label n3txt = new Label("Laborator:");
        n3txt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField n3f = new TextField();
        n3f.setPromptText("...");
        n3f.setFont(new Font("Arial", 14));
        n3f.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        n3f.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        n3f.setPrefSize(70, 30);
        t3.getChildren().addAll(n3txt, n3f);
        t3.setSpacing(20);
        t3.setAlignment(Pos.CENTER);

        HBox t4 = new HBox();
        Label n4txt = new Label("Colocviu:");
        n4txt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField n4f = new TextField();
        n4f.setPromptText("...");
        n4f.setFont(new Font("Arial", 14));
        n4f.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        n4f.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        n4f.setPrefSize(70, 30);
        t4.getChildren().addAll(n4txt, n4f);
        t4.setSpacing(20);
        t4.setAlignment(Pos.CENTER);

        HBox t5 = new HBox();
        Label n5txt = new Label("Examen:");
        n5txt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField n5f = new TextField();
        n5f.setPromptText("...");
        n5f.setFont(new Font("Arial", 14));
        n5f.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        n5f.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        n5f.setPrefSize(70, 30);
        t5.getChildren().addAll(n5txt, n5f);
        t5.setSpacing(20);
        t5.setAlignment(Pos.CENTER);

        HBox jos = new HBox();
        Label txtCurs = new Label();
        txtCurs.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Label numeCurs = new Label();
        numeCurs.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, 18));
        numeCurs.setStyle("-fx-text-fill: white; -fx-background-color: #2a2222 ;-fx-background-radius: 15px; ");
        numeCurs.setPrefSize(250, 25);
        numeCurs.setAlignment(Pos.CENTER);
        jos.setAlignment(Pos.CENTER);
        jos.setSpacing(20);
        jos.getChildren().addAll(txtCurs, numeCurs);

        Button add = new Button("Save");
        add.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        add.setPrefSize(120, 60);
        add.setTextFill(Color.WHITE);

        Label intr = new Label();
        intr.setFont(Font.font("Arial", FontWeight.LIGHT, 16));

        VBox note = new VBox();
        note.getChildren().addAll(jos, t1, t2, t3, t4, t5, add, intr);
        note.setAlignment(Pos.CENTER);
        note.setSpacing(20);

        VBox lllv = new VBox();

        if (stud) {
            numeCurs.setText("...");
            lista.setItems(studenti);
            lista2.setPrefSize(370, 500);
            lllv.getChildren().addAll(titlu, lista2);
            lllv.setAlignment(Pos.CENTER);
            lllv.setSpacing(20);
            titlu.setText("Studenti");
            txtCurs.setText("Pentru studentul:");
            intr.setText("Notele pentru fiecare dintre activtati ( 0 - 10 )");
        } else {
            lllv.getChildren().addAll(titlu, lista);
            lllv.setAlignment(Pos.CENTER);
            lllv.setSpacing(20);
            numeCurs.setText("...");
            lista.setItems(cursuri);
            titlu.setText("Cursuri");
            txtCurs.setText("Pentru cursul:");
            intr.setText("Procentajul fiecarei activitati din nota finala (suma sa fie 100)");
        }

        root.getChildren().addAll(lllv, note);
        root.setSpacing(20);
        root.setPadding(new Insets(10, 10, 10, 70));
        root.setAlignment(Pos.CENTER);
        VBox r = new VBox();
        r.setAlignment(Pos.CENTER);
        r.getChildren().add(root);

        if (stud == false)// am cursuri
        {
            Statement slc = null;
            try {
                slc = connection.createStatement();
                // slc.execute("SELECT curs.nume FROM curs, sustine_curs WHERE
                // curs.id=sustine_curs.id_curs AND sustine_curs.id_profesor = " +
                // pers.getId()+";");
                slc.execute("SELECT id_curs FROM  sustine_curs WHERE id_profesor= " + pers.getId() + ";");
                ResultSet rs = slc.getResultSet();

                iduri = FXCollections.observableArrayList();// pt id_student
                num = FXCollections.observableArrayList();
                while (rs.next()) {
                    iduri.add(rs.getInt("id_curs"));
                }
                id_cursuri = new int[100];// avem maxim 100 de cursuri in total
                int k = 0;
                for (int i = 0; i < iduri.size(); i++) {
                    slc.execute("SELECT nume FROM curs WHERE id_curs= " + iduri.get(i) + ";");
                    rs = slc.getResultSet();
                    while (rs.next()) {
                        num.add(rs.getString("nume"));
                        id_cursuri[k] = iduri.get(i);
                        k++;
                    }
                }
                lista.setItems(num);
                lista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object o, Object t1) {
                        index = lista.getSelectionModel().getSelectedIndex();
                        if (index >= 0) {
                            numeCurs.setText(num.get(index));
                            Statement slc = null;

                            try {
                                slc = connection.createStatement();
                                ResultSet rs;
                                slc.execute("SELECT procente_curs FROM sustine_curs WHERE id_profesor= " + pers.getId()
                                        + " and id_curs= " + id_cursuri[index]);
                                rs = slc.getResultSet();
                                if (rs.next())
                                    n1f.setText(String.valueOf(rs.getInt("procente_curs")));

                                slc.execute("SELECT procente_seminar FROM sustine_curs WHERE id_profesor= "
                                        + pers.getId() + " and id_curs= " + id_cursuri[index]);
                                rs = slc.getResultSet();
                                if (rs.next())
                                    n2f.setText(String.valueOf(rs.getInt("procente_seminar")));

                                slc.execute("SELECT procente_laborator FROM sustine_curs WHERE id_profesor= "
                                        + pers.getId() + " and id_curs= " + id_cursuri[index]);
                                rs = slc.getResultSet();
                                if (rs.next())
                                    n3f.setText(String.valueOf(rs.getInt("procente_laborator")));

                                slc.execute("SELECT procente_colocviu FROM sustine_curs WHERE id_profesor= "
                                        + pers.getId() + " and id_curs= " + id_cursuri[index]);
                                rs = slc.getResultSet();
                                if (rs.next())
                                    n4f.setText(String.valueOf(rs.getInt("procente_colocviu")));

                                slc.execute("SELECT procente_examen FROM sustine_curs WHERE id_profesor= "
                                        + pers.getId() + " and id_curs= " + id_cursuri[index]);
                                rs = slc.getResultSet();
                                if (rs.next())
                                    n5f.setText(String.valueOf(rs.getInt("procente_examen")));

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        }

                    }
                });

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            add.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    // TODO Auto-gener
                    if (index >= 0) {
                        try {

                            int s = 0;
                            s += Integer.parseInt(n2f.getText());
                            s += Integer.parseInt(n3f.getText());
                            s += Integer.parseInt(n1f.getText());
                            s += Integer.parseInt(n4f.getText());
                            s += Integer.parseInt(n5f.getText());
                            if (s == 100) {
                                CallableStatement clb = connection
                                        .prepareCall("call update_procente_sustine_curs(?,?,?,?,?,?,?);");
                                clb.setInt(1, iduri.get(index));
                                clb.setInt(2, pers.getId());
                                clb.setInt(3, Integer.parseInt(n2f.getText()));
                                clb.setInt(4, Integer.parseInt(n1f.getText()));
                                clb.setInt(5, Integer.parseInt(n5f.getText()));
                                clb.setInt(6, Integer.parseInt(n3f.getText()));
                                clb.setInt(7, Integer.parseInt(n4f.getText()));
                                intr.setTextFill(Color.BLACK);
                                clb.execute();
                            } else
                                intr.setTextFill(Color.RED);

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        } else// am studenti
        {
            Statement slc = null;
            try {
                slc = connection.createStatement();
                slc.execute("SELECT id_student, id_curs FROM inscris_curs WHERE id_profesor= " + pers.getId() + ";");
                ResultSet rs = slc.getResultSet();
                iduri = FXCollections.observableArrayList();// pt id_student
                iduri2 = FXCollections.observableArrayList();// pt id_curs
                nume = FXCollections.observableArrayList();
                prenume = FXCollections.observableArrayList();
                num = FXCollections.observableArrayList();
                while (rs.next()) {
                    iduri.add(rs.getInt("id_student"));
                    iduri2.add(rs.getInt("id_curs"));
                }

                String nume_prenume = null;
                id_cursuri = new int[100];// avem maxim 100 de cursuri in total
                id_studenti = new int[100];
                int k = 0;
                for (int i : iduri) {
                    slc.execute("SELECT nume FROM  utilizator WHERE id= " + i + ";");
                    rs = slc.getResultSet();
                    if (rs.next()) {
                        nume_prenume = rs.getString("nume");
                        slc.execute("SELECT prenume FROM  utilizator WHERE id= " + i + ";");
                        rs = slc.getResultSet();
                        if (rs.next()) {
                            nume_prenume += " ";
                            nume_prenume += rs.getString("prenume");
                            nume.add(nume_prenume);
                            id_studenti[k] = i;
                            k++;
                        }
                    }
                }
                k = 0;
                for (int i = 0; i < iduri.size(); i++) {
                    slc.execute("SELECT nume FROM curs WHERE id_curs= " + iduri2.get(i) + ";");
                    rs = slc.getResultSet();
                    while (rs.next()) {
                        num.add(nume.get(i) + "                    " + rs.getString("nume"));
                        id_cursuri[k] = iduri2.get(i);
                        k++;
                    }
                }
                lista2.setItems(num);
                lista2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object o, Object t1) {
                        index = lista2.getSelectionModel().getSelectedIndex();
                        if (index >= 0) {
                            numeCurs.setText(nume.get(index));
                            Statement slc = null;

                            try {
                                slc = connection.createStatement();
                                ResultSet rs;
                                slc.execute("SELECT nota_curs FROM inscris_curs WHERE id_profesor= " + pers.getId()
                                        + " and id_student= " + id_studenti[index] + " and id_curs= "
                                        + id_cursuri[index]);
                                rs = slc.getResultSet();
                                if (rs.next())
                                    n1f.setText(String.valueOf(rs.getInt("nota_curs")));

                                slc.execute("SELECT nota_seminar FROM inscris_curs WHERE id_profesor= " + pers.getId()
                                        + " and id_student= " + id_studenti[index] + " and id_curs= "
                                        + id_cursuri[index]);
                                rs = slc.getResultSet();
                                if (rs.next())
                                    n2f.setText(String.valueOf(rs.getInt("nota_seminar")));

                                slc.execute("SELECT nota_laborator FROM inscris_curs WHERE id_profesor= " + pers.getId()
                                        + " and id_student= " + id_studenti[index] + " and id_curs= "
                                        + id_cursuri[index]);
                                rs = slc.getResultSet();
                                if (rs.next())
                                    n3f.setText(String.valueOf(rs.getInt("nota_laborator")));

                                slc.execute("SELECT nota_colocviu FROM inscris_curs WHERE id_profesor= " + pers.getId()
                                        + " and id_student= " + id_studenti[index] + " and id_curs= "
                                        + id_cursuri[index]);
                                rs = slc.getResultSet();
                                if (rs.next())
                                    n4f.setText(String.valueOf(rs.getInt("nota_colocviu")));

                                slc.execute("SELECT nota_examen FROM inscris_curs WHERE id_profesor= " + pers.getId()
                                        + " and id_student= " + id_studenti[index] + " and id_curs= "
                                        + id_cursuri[index]);
                                rs = slc.getResultSet();
                                if (rs.next())
                                    n5f.setText(String.valueOf(rs.getInt("nota_examen")));

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                });

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            add.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    // TODO Auto-gener
                    if (index >= 0) {
                        boolean ok = true;
                        if (!(0 <= Integer.parseInt(n2f.getText()) && Integer.parseInt(n2f.getText()) < 11))
                            ok = false;
                        if (!(0 <= Integer.parseInt(n1f.getText()) && Integer.parseInt(n1f.getText()) < 11))
                            ok = false;
                        if (!(0 <= Integer.parseInt(n3f.getText()) && Integer.parseInt(n3f.getText()) < 11))
                            ok = false;
                        if (!(0 <= Integer.parseInt(n4f.getText()) && Integer.parseInt(n4f.getText()) < 11))
                            ok = false;
                        if (!(0 <= Integer.parseInt(n5f.getText()) && Integer.parseInt(n5f.getText()) < 11))
                            ok = false;
                        if (ok) {
                            try {
                                CallableStatement clb = connection
                                        .prepareCall("call update_note_inscris_curs(?,?,?,?,?,?,?,?);");
                                clb.setInt(1, iduri2.get(index));
                                clb.setInt(2, iduri.get(index));
                                clb.setInt(3, pers.getId());
                                clb.setInt(4, Integer.parseInt(n2f.getText()));
                                clb.setInt(5, Integer.parseInt(n1f.getText()));
                                clb.setInt(6, Integer.parseInt(n5f.getText()));
                                clb.setInt(7, Integer.parseInt(n3f.getText()));
                                clb.setInt(8, Integer.parseInt(n4f.getText()));
                                clb.execute();
                                intr.setTextFill(Color.BLACK);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        } else
                            intr.setTextFill(Color.RED);
                    }
                }
            });

        }
        return r;
    }

    @SuppressWarnings("unchecked")
    public static VBox calendar(Persoana pers, boolean stud, Connection connection) {
        VBox vbox = new VBox();

        Label zile = new Label("Luni          Marti          Miercuri          Joi          Vineri");
        zile.setFont(Font.font("Arial", FontWeight.MEDIUM, 30));
        zile.setTextFill(Color.WHITE);
        zile.setStyle("-fx-background-color: #2a2222 ;");
        zile.setPrefSize(900, 80);
        zile.setAlignment(Pos.CENTER);

        Label ora1 = new Label("8-10");
        ora1.setTextFill(Color.WHITE);
        ora1.setStyle("-fx-background-color: #2a2222 ;");
        ora1.setFont(Font.font("Arial", FontWeight.MEDIUM, 25));
        ora1.setPrefSize(80, 80);
        ora1.setAlignment(Pos.CENTER);

        Label ora2 = new Label("10-12");
        ora2.setTextFill(Color.WHITE);
        ora2.setStyle("-fx-background-color: #2a2222 ;");
        ora2.setFont(Font.font("Arial", FontWeight.MEDIUM, 25));
        ora2.setPrefSize(80, 80);
        ora2.setAlignment(Pos.CENTER);

        Label ora3 = new Label("12-14");
        ora3.setTextFill(Color.WHITE);
        ora3.setStyle("-fx-background-color: #2a2222 ;");
        ora3.setFont(Font.font("Arial", FontWeight.MEDIUM, 25));
        ora3.setPrefSize(80, 80);
        ora3.setAlignment(Pos.CENTER);

        Label ora4 = new Label("14-16");
        ora4.setTextFill(Color.WHITE);
        ora4.setStyle("-fx-background-color: #2a2222 ;");
        ora4.setFont(Font.font("Arial", FontWeight.MEDIUM, 25));
        ora4.setPrefSize(80, 80);
        ora4.setAlignment(Pos.CENTER);

        Label ora5 = new Label("16-18");
        ora5.setTextFill(Color.WHITE);
        ora5.setStyle("-fx-background-color: #2a2222 ;");
        ora5.setFont(Font.font("Arial", FontWeight.MEDIUM, 25));
        ora5.setPrefSize(80, 80);
        ora5.setAlignment(Pos.CENTER);

        Label ora6 = new Label("18-20");
        ora6.setTextFill(Color.WHITE);
        ora6.setStyle("-fx-background-color: #2a2222 ;");
        ora6.setFont(Font.font("Arial", FontWeight.MEDIUM, 25));
        ora6.setPrefSize(80, 80);
        ora6.setAlignment(Pos.CENTER);

        Label ora7 = new Label("20-22");
        ora7.setTextFill(Color.WHITE);
        ora7.setStyle("-fx-background-color: #2a2222 ;");
        ora7.setFont(Font.font("Arial", FontWeight.MEDIUM, 25));
        ora7.setPrefSize(80, 80);
        ora7.setAlignment(Pos.CENTER);

        Label nimic = new Label();
        nimic.setStyle("-fx-background-color: #2a2222 ;");
        nimic.setPrefSize(80, 80);

        Image im = new Image("https://cdn-icons-png.flaticon.com/512/42/42371.png", 100, 100, false, true);
        ImageView icon = new ImageView(im);
        VBox timp = new VBox();
        Label dattxt = new Label("Data:");
        dattxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label oratxt = new Label("Ora:");
        oratxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        DatePicker dataInc = new DatePicker();
        dataInc.setValue(LocalDate.now());
        ComboBox oraInc = new ComboBox();
        ObservableList oreinc = FXCollections.observableArrayList();
        oreinc.addAll("8-10", "10-12", "12-14", "14-16", "16-18", "18-20", "20-22");
        oraInc.setItems(oreinc);
        oraInc.getSelectionModel().selectFirst();
        timp.getChildren().addAll(dattxt, dataInc, oratxt, oraInc);
        timp.setSpacing(10);

        VBox ore = new VBox();
        ore.getChildren().addAll(nimic, ora1, ora2, ora3, ora4, ora5, ora6, ora7);

        GridPane calendar = new GridPane();
        calendar.setPrefSize(900, 560);
        Integer[][] matid = new Integer[7][5];
        Label[][] a = new Label[7][5];

        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 5; j++)
                matid[i][j] = 0;

        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 5; j++) {
                a[i][j] = new Label("");
                a[i][j].setPrefSize(180, 80);
                a[i][j].setAlignment(Pos.TOP_CENTER);
                a[i][j].setStyle("-fx-border-width: 2px; -fx-border-color:black; -fx-border-style: solid inside;");
                a[i][j].setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
                calendar.add(a[i][j], j, i);
                int ind = i;
                int jnd = j;
                a[i][j].setOnMouseClicked(new EventHandler() {

                    @Override
                    public void handle(Event arg0) {
                        // TODO Auto-generated method stub
                        if (matid[ind][jnd] == 0) {
                            oraInc.getSelectionModel().select(ind);
                            LocalDate d = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                                    LocalDate.now().getDayOfMonth() - LocalDate.now().getDayOfWeek().getValue() + jnd);
                            dataInc.setValue(d);
                        } else {
                            selectat = matid[ind][jnd];
                        }
                    }
                });
            }
        Profesor.completareActivitati(pers, a, stud, connection, matid);

        // calendar.setAlignment(Pos.CENTER);
        calendar.setStyle("-fx-background-color:#ccccff ;");
        VBox zl = new VBox();
        zl.getChildren().addAll(zile, calendar);
        HBox cal = new HBox();
        cal.getChildren().addAll(icon, ore, zl);

        if (stud) {
            Button particip = new Button("Particip");
            particip.setPrefSize(200, 70);
            particip.setStyle("-fx-background-color: #2a2222 ;-fx-background-radius: 15px;");
            particip.setTextFill(Color.WHITE);
            particip.setAlignment(Pos.CENTER);
            particip.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));

            Label err = new Label("");
            err.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
            err.setTextFill(Color.RED);
            VBox p = new VBox();
            p.getChildren().addAll(particip);
            p.setPadding(new Insets(250, 0, 0, 5));
            cal.getChildren().add(p);

            particip.setOnAction(new EventHandler() {

                @Override
                public void handle(Event arg0) {
                    // TODO Auto-generated method stub
                    if (selectat > 0) {
                        try {
                            CallableStatement clb = connection.prepareCall("call participare_activitate(?,?);");
                            clb.setInt(1, pers.getId());
                            clb.setInt(2, selectat);
                            clb.execute();
                            ResultSet rs = clb.getResultSet();
                            if (rs.next()) {
                                err.setText(rs.getString(1));
                            }
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (selectat < 0) {
                        try {
                            CallableStatement clb = connection.prepareCall("call participare_activitate_grup(?,?);");
                            clb.setInt(1, pers.getId());
                            clb.setInt(2, -selectat);
                            clb.execute();
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        Label curstxt = new Label("Curs:");
        curstxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ObservableList cursuri = FXCollections.observableArrayList();
        ListView curs = new ListView(cursuri);
        curs.setPrefSize(200, 200);

        VBox details = new VBox();
        Label nrtxt = new Label("Numar studenti:");
        nrtxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField numarStud = new TextField();
        numarStud.setPromptText("...");
        numarStud.setFont(new Font("Arial", 14));
        numarStud.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        numarStud.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        numarStud.setPrefSize(100, 20);

        Label tiptxt = new Label("Tip activitate:");
        tiptxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ComboBox tipAct = new ComboBox();
        ObservableList activ = FXCollections.observableArrayList();
        activ.addAll("curs", "seminar", "laborator", "colocviu", "examen");
        tipAct.setItems(activ);
        tipAct.getSelectionModel().selectFirst();

        details.getChildren().addAll(nrtxt, numarStud, tiptxt, tipAct);
        details.setSpacing(10);

        Button creare = new Button("Create");
        creare.setPrefSize(150, 80);
        creare.setStyle("-fx-background-color: #2a2222 ;-fx-background-radius: 15px;");
        creare.setTextFill(Color.WHITE);
        creare.setAlignment(Pos.CENTER);
        creare.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));

        ArrayList<Integer> iduri = new ArrayList<Integer>();
        ArrayList<Integer> idProf = new ArrayList<Integer>();
        TextField durata = new TextField();
        ObservableList profi = FXCollections.observableArrayList();
        if (stud) {
            tiptxt.setText("Profesor:");
            curstxt.setText("Grup:");

            Label durtxt = new Label("Durata (nr. de ore):");
            nrtxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            durata.setPromptText("...");
            durata.setFont(new Font("Arial", 14));
            durata.setStyle(
                    "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
            durata.setBackground(
                    new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
            durata.setPrefSize(100, 20);
            details.getChildren().addAll(durtxt, durata);

            Statement slc;
            try {
                slc = connection.createStatement();
                slc.execute(
                        "Select distinct utilizator.nume as nume,utilizator.prenume as prenume,utilizator.id as id from utilizator,sustine_curs,inscris_curs where utilizator.id = sustine_curs.id_profesor and inscris_curs.id_curs = sustine_curs.id_curs and inscris_curs.id_student= "
                                + pers.getId());
                ResultSet rs = slc.getResultSet();
                while (rs.next()) {
                    idProf.add(rs.getInt("id"));
                    profi.add(rs.getString("nume") + " " + rs.getString("prenume"));

                }
                slc.execute(
                        "select grup.id as id,grup.nume as nume from grup,inscris_grup where grup.id=inscris_grup.id_grup and inscris_grup.id_student= "
                                + pers.getId());
                rs = slc.getResultSet();
                while (rs.next()) {
                    iduri.add(rs.getInt("id"));
                    cursuri.add(rs.getString("nume"));
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            tipAct.setItems(profi);
        } else {
            Statement slc;
            try {
                slc = connection.createStatement();
                slc.execute(
                        "Select curs.nume as num,curs.id_curs as id from curs,sustine_curs where curs.id_curs = sustine_curs.id_curs and sustine_curs.id_profesor= "
                                + pers.getId());
                ResultSet rs = slc.getResultSet();
                while (rs.next()) {
                    iduri.add(rs.getInt("id"));
                    cursuri.add(rs.getString("num"));
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        creare.setOnAction(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                Time[] times = { new Time(8, 0, 0), new Time(10, 0, 0), new Time(12, 0, 0), new Time(14, 0, 0),
                        new Time(16, 0, 0), new Time(18, 0, 0), new Time(20, 0, 0), new Time(22, 0, 0) };
                if (!stud) {
                    try {
                        CallableStatement clb = connection.prepareCall("call creare_activitate(?,?,?,?,?,?,?);");
                        clb.setInt(4, pers.getId());
                        int h = oraInc.getSelectionModel().getSelectedIndex();
                        clb.setTime(1, times[h + 1]);
                        clb.setTime(2, times[h]);
                        int cu = curs.getSelectionModel().getSelectedIndex();
                        clb.setInt(3, iduri.get(cu));
                        clb.setInt(5, Integer.valueOf(numarStud.getText()));
                        if (tipAct.getSelectionModel().getSelectedIndex() > -1)
                            clb.setInt(6, tipAct.getSelectionModel().getSelectedIndex());
                        Date d = Date.valueOf(dataInc.getValue());
                        clb.setDate(7, d);
                        clb.execute();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    try {
                        CallableStatement clb = connection.prepareCall("call creare_activitate_grup(?,?,?,?,?,?,?);");
                        int cu = curs.getSelectionModel().getSelectedIndex();
                        clb.setInt(1, iduri.get(cu));
                        clb.setInt(2, 2);
                        Date d = Date.valueOf(dataInc.getValue());
                        clb.setDate(3, d);
                        int h = oraInc.getSelectionModel().getSelectedIndex();
                        clb.setTime(4, times[h]);
                        clb.setInt(5, Integer.valueOf(durata.getText()));
                        clb.setInt(6, Integer.valueOf(numarStud.getText()));
                        if (tipAct.getSelectionModel().getSelectedIndex() > -1)
                            clb.setInt(7, idProf.get(tipAct.getSelectionModel().getSelectedIndex()));
                        clb.execute();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        HBox jos = new HBox();
        jos.getChildren().addAll(timp, curstxt, curs, details, creare);
        jos.setPadding(new Insets(10, 10, 10, 120));
        jos.setSpacing(20);
        jos.setAlignment(Pos.CENTER_LEFT);

        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(cal, jos);
        vbox.setAlignment(Pos.TOP_LEFT);
        return vbox;
    }

    public static void completareActivitati(Persoana pers, Label[][] a, boolean stud, Connection connection,
                                            Integer[][] matid) {
        String[] activ = { "curs", "seminar", "laborator", "colocviu", "examen", "grup" };
        if (!stud) {
            try {
                Statement slc = connection.createStatement();
                slc.execute("Select * from activitate where id_profesor= " + pers.getId() + " ;");
                ResultSet rs = slc.getResultSet();
                while (rs.next()) {

                    int tip = rs.getInt(7);
                    Date d = rs.getDate(8);
                    int y = d.getDay();

                    LocalDate cur = LocalDate.now();
                    LocalDate dd = d.toLocalDate();
                    if (y < 5 && (dd.getDayOfYear() / 7 == cur.getDayOfYear() / 7 && cur.getYear() == dd.getYear())) {
                        Time t = rs.getTime("ora_incepere");
                        int x = (t.getHours() - 8) / 2;
                        a[x][y].setStyle("-fx-background-color: #cc33ff; -fx-border-color:black;");
                        Statement da = connection.createStatement();
                        da.execute("Select nume from curs where id_curs= " + rs.getString(4));
                        ResultSet nume = da.getResultSet();
                        if (nume.next()) {
                            a[x][y].setText(nume.getString(1) + "\n" + activ[tip]);
                            matid[x][y] = 1;
                        }
                    }
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            ArrayList<Integer> inscrisCurs = new ArrayList<Integer>();
            ArrayList<Integer> neCurs = new ArrayList<Integer>();
            ArrayList<Integer> inscrisGrup = new ArrayList<Integer>();
            ArrayList<Integer> neGrup = new ArrayList<Integer>();
            ArrayList<Integer> cursuri = new ArrayList<Integer>();

            try {
                Statement slc = connection.createStatement();
                slc.execute("Select id_curs from inscris_curs where id_student= " + pers.getId() + ";");
                ResultSet rs = slc.getResultSet();
                while (rs.next()) {
                    cursuri.add(rs.getInt("id_curs"));
                }

                for (int i : cursuri) {
                    slc.execute("Select * from activitate where id_curs =" + String.valueOf(i) + ";");
                    rs = slc.getResultSet();
                    while (rs.next()) {
                        Statement slc2 = connection.createStatement();
                        slc2.execute("Select id_student from participa_activitate where id_activitate= "
                                + rs.getString("id") + " and id_student= " + pers.getId() + " ;");
                        ResultSet rs2 = slc2.getResultSet();
                        if (rs2.next()) {
                            inscrisCurs.add(rs.getInt("id"));
                        } else
                            neCurs.add(rs.getInt("id"));
                    }
                }
                cursuri.clear();
                slc.execute(
                        "Select grup.id as id from grup,inscris_curs where grup.id_curs=inscris_curs.id_curs and inscris_curs.id_student= "
                                + pers.getId() + ";");
                rs = slc.getResultSet();
                while (rs.next()) {
                    cursuri.add(rs.getInt("id"));
                }

                for (int i : cursuri) {
                    slc.execute("Select * from activitate_grup where id_grup =" + String.valueOf(i) + ";");
                    rs = slc.getResultSet();
                    while (rs.next()) {
                        Statement slc2 = connection.createStatement();
                        slc2.execute("Select id_student from participa_activitate_grup where id_activitate_grup= "
                                + rs.getString("id") + " and id_student= " + pers.getId() + ";");
                        ResultSet rs2 = slc2.getResultSet();
                        if (rs2.next())
                            inscrisGrup.add(rs.getInt("id"));
                        else
                            neGrup.add(rs.getInt("id"));

                    }
                }

                for (int i : inscrisCurs) {
                    slc.execute("Select * from activitate where id =" + i + " ; ");
                    rs = slc.getResultSet();
                    if (rs.next()) {
                        int tip = rs.getInt(7);
                        Date d = rs.getDate(8);
                        int y = d.getDay();

                        LocalDate cur = LocalDate.now();
                        LocalDate dd = d.toLocalDate();
                        if (y < 5
                                && (dd.getDayOfYear() / 7 == cur.getDayOfYear() / 7 && cur.getYear() == dd.getYear())) {
                            Time t = rs.getTime("ora_incepere");
                            int x = (t.getHours() - 8) / 2;
                            a[x][y].setStyle("-fx-background-color: #cc33ff; -fx-border-color:black;");
                            Statement da = connection.createStatement();
                            da.execute("Select nume from curs where id_curs= " + rs.getString(4));
                            ResultSet nume = da.getResultSet();
                            if (nume.next()) {
                                a[x][y].setText(nume.getString(1) + "\n" + activ[tip]);
                                matid[x][y] = i;
                            }
                        }
                    }
                }

                for (int i : neCurs) {
                    slc.execute("Select * from activitate where id= " + i + ";");
                    rs = slc.getResultSet();
                    if (rs.next()) {
                        int tip = rs.getInt(7);
                        Date d = rs.getDate(8);
                        int y = d.getDay();
                        LocalDate cur = LocalDate.now();
                        LocalDate dd = d.toLocalDate();
                        if (y < 5
                                && (dd.getDayOfYear() / 7 == cur.getDayOfYear() / 7 && cur.getYear() == dd.getYear())) {
                            Time t = rs.getTime("ora_incepere");
                            int x = (t.getHours() - 8) / 2;
                            if (a[x][y].getText().equals("")) {
                                a[x][y].setStyle("-fx-background-color: #cc66ff; -fx-border-color:black;");
                                Statement da = connection.createStatement();
                                da.execute("Select nume from curs where id_curs= " + rs.getString(4));
                                ResultSet nume = da.getResultSet();
                                if (nume.next()) {
                                    a[x][y].setText(nume.getString(1) + "\n" + activ[tip]);
                                    matid[x][y] = i;
                                }
                            }
                        }
                    }
                }

                for (int i : inscrisGrup) {
                    slc.execute("Select * from activitate_grup where id =" + i + " ; ");
                    rs = slc.getResultSet();
                    if (rs.next()) {
                        Date d = rs.getDate(7);
                        int y = d.getDay();

                        LocalDate cur = LocalDate.now();
                        LocalDate dd = d.toLocalDate();
                        if (y < 5
                                && (dd.getDayOfYear() / 7 == cur.getDayOfYear() / 7 && cur.getYear() == dd.getYear())) {
                            Time t = rs.getTime("ora_inceput");
                            int x = (t.getHours() - 8) / 2;
                            if (a[x][y].getText().equals("")) {
                                a[x][y].setStyle("-fx-background-color: #3399ff; -fx-border-color:black;");
                                Statement da = connection.createStatement();
                                da.execute("Select nume from grup where id= " + rs.getString(2));
                                ResultSet nume = da.getResultSet();
                                if (nume.next()) {
                                    a[x][y].setText(nume.getString(1) + "\n" + activ[5]);
                                    matid[x][y] = -i;
                                }
                            }
                        }
                    }
                }

                for (int i : neGrup) {
                    slc.execute("Select * from activitate_grup where id =" + i + " ; ");
                    rs = slc.getResultSet();
                    if (rs.next()) {
                        Date d = rs.getDate(7);
                        int y = d.getDay();

                        LocalDate cur = LocalDate.now();
                        LocalDate dd = d.toLocalDate();
                        if (y < 5
                                && (dd.getDayOfYear() / 7 == cur.getDayOfYear() / 7 && cur.getYear() == dd.getYear())) {
                            Time t = rs.getTime("ora_inceput");
                            int x = (t.getHours() - 8) / 2;

                            if (a[x][y].getText().equals("")) {
                                a[x][y].setStyle("-fx-background-color: #99ccff; -fx-border-color:black;");
                                Statement da = connection.createStatement();
                                da.execute("Select nume from grup where id= " + rs.getString(2));
                                ResultSet nume = da.getResultSet();
                                if (nume.next()) {
                                    a[x][y].setText(nume.getString(1) + "\n" + activ[5]);
                                    matid[x][y] = -i;
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}