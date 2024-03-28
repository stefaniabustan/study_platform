package com.example.interfata;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
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

public class Admin {
    private static HBox tot;
    protected static VBox profil;
    protected static Persoana pers;
    protected static Persoana prof;
    protected static Curs cursul;
    static Boolean ok;
    static ObservableList useri;
    static ObservableList cursuri;
    static ListView lv;
    static ObservableList items;

    /**
     * Creeaza scena adminilor si al superadminilor cu acces la listele de cursuri
     * si utilizatori din baza de date
     *
     * @param caracter
     * @param superAd
     * @param connection
     * @param p
     * @return
     */
    public static Scene getAdminScene(boolean caracter, Persoana profilAdmin, boolean superAd, Connection connection,
                                      Stage p) {

        items = FXCollections.observableArrayList();
        lv = new ListView();
        VBox admin = Admin.search(caracter, superAd, connection, p, profilAdmin);
        profil = Admin.profil(superAd, connection);
        pers = new Persoana();
        cursul = new Curs();
        tot = new HBox();
        BackgroundImage myBI = new BackgroundImage(
                new Image("https://coolbackgrounds.io/images/backgrounds/index/sea-edge-79ab30e2.png", 1920, 1080,
                        false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        tot.setBackground(new Background(myBI));
        tot.getChildren().addAll(admin, profil);
        tot.setSpacing(150);
        Scene scene = new Scene(tot, 1920, 1080);
        return scene;
    }

    @SuppressWarnings("unchecked")
    public static VBox search(boolean caracter, boolean superAd, Connection connection, Stage p, Persoana profilAdmin) {

        ObservableList<String> lis = FXCollections.observableArrayList("All", "Profesor", "Student");
        ComboBox filtru = new ComboBox(lis);
        filtru.getSelectionModel().selectFirst();
        filtru.setPrefSize(150, 20);
        Image ic = null;
        Label title = new Label();
        if (!superAd) {
            title.setText("Admin");
            ic = new Image("https://static.thenounproject.com/png/2303078-200.png", 100, 100, false, true);
        } else {
            lis.add("Admin");
            if (caracter) {
                title.setText("Grinch");
                ic = new Image("https://img.icons8.com/color/480/grinch.png", 100, 100, false, true);
            } else {
                ic = new Image("https://pngimg.com/uploads/ariel/ariel_PNG41.png", 100, 100, false, true);
                title.setText("Ariel");
            }
        }
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 50));
        ImageView icon = new ImageView(ic);
        if (!superAd) {
            Persoana perss = profilAdmin;
            title.setOnMouseClicked(new EventHandler() {

                @Override
                public void handle(Event arg0) {
                    // TODO Auto-generated method stub
                    tot.getChildren().remove(profil);
                    VBox cpr = Student.profil(perss);
                    cpr.setPadding(new Insets(100, 0, 80, 0));
                    profil = cpr;
                    tot.getChildren().add(profil);
                }
            });
        }

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER_LEFT);
        top.getChildren().addAll(icon, title);
        top.setSpacing(30);

        HBox radio = new HBox();
        RadioButton curs = new RadioButton();
        curs.setText("Cursuri");
        curs.setFont(new Font(20));
        curs.setStyle(
                "-fx-text-fill: white; -fx-background-color: #2a2222 ;-fx-background-radius: 15px; -fx-background-insets: 0;");
        curs.setPrefSize(200, 30);
        curs.setAlignment(Pos.CENTER);

        RadioButton utilizator = new RadioButton();
        utilizator.setText("Utilizatori");
        utilizator.setFont(new Font(20));
        utilizator.setStyle(
                "-fx-text-fill: white; -fx-background-color: #2a2222 ;-fx-background-radius: 15px; -fx-background-insets: 0;");
        utilizator.setPrefSize(200, 30);
        utilizator.setAlignment(Pos.CENTER);
        utilizator.setSelected(true);
        radio.getChildren().addAll(curs, utilizator);
        radio.setAlignment(Pos.TOP_LEFT);

        TextField search = new TextField();
        search.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 20));
        search.setStyle("-fx-background-color: white; -fx-background-radius: 15px; ");
        search.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        search.setPrefSize(1000, 40);
        Image srcc = new Image(
                "https://www.clipartmax.com/png/full/473-4732129_computer-icons-google-search-clip-art-transprent-%E2%93%92-transparent-background-magnifying-glass.png",
                35, 35, false, true);
        ImageView im = new ImageView(srcc);

        HBox src = new HBox();
        src.setSpacing(-40);
        src.setAlignment(Pos.CENTER_LEFT);
        src.getChildren().addAll(search, im);
        src.setPadding(new Insets(0, 0, -10, 0));

        useri = FXCollections.observableArrayList();
        cursuri = FXCollections.observableArrayList();
        ok = true;
        Admin.refresh(connection);

        lv.setItems(items);
        lv.setEditable(false);
        lv.setPrefSize(1000, 800);

        Button logoutbtn = new Button("Sign out");
        logoutbtn.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        logoutbtn.setPrefSize(170, 100);
        logoutbtn.setFont(new Font(20));
        logoutbtn.setTextFill(Color.WHITE);
        VBox jos = new VBox();
        jos.getChildren().add(logoutbtn);
        jos.setPadding(new Insets(0, 0, 10, 0));
        jos.setAlignment(Pos.BOTTOM_LEFT);

        VBox admin = new VBox();
        admin.getChildren().addAll(top, radio, src, filtru, lv, jos);
        admin.setSpacing(10);
        admin.setPadding(new Insets(10, 10, 10, 10));
        admin.setAlignment(Pos.TOP_LEFT);

        utilizator.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                ok = true;
                Admin.refresh(connection);
                items.clear();
                items.addAll(useri);
                curs.setSelected(false);
                filtru.setVisible(true);
                filtru.getSelectionModel().selectFirst();
                tot.getChildren().remove(profil);
                profil = Admin.profil(superAd, connection);
                tot.getChildren().add(profil);
            }

        });

        curs.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                ok = false;
                Admin.refresh(connection);
                items.clear();
                items.addAll(cursuri);
                utilizator.setSelected(false);
                filtru.setVisible(false);
                tot.getChildren().remove(profil);
                profil = Admin.creareCurs(connection);
                tot.getChildren().add(profil);
            }

        });

        filtru.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                items.clear();
                items.addAll(useri);
                if (filtru.getSelectionModel().isSelected(1)) {
                    for (Object c : useri) {
                        Persoana p = (Persoana) c;
                        if (p.getFunctie() != 1)
                            items.remove(c);
                    }
                } else if (filtru.getSelectionModel().isSelected(2)) {
                    for (Object c : useri) {
                        Persoana p = (Persoana) c;
                        if (p.getFunctie() != 2)
                            items.remove(c);

                    }
                } else if (filtru.getSelectionModel().isSelected(3)) {
                    for (Object c : useri) {
                        Persoana p = (Persoana) c;
                        if (p.getFunctie() != 0)
                            items.remove(c);
                    }
                }
            }
        });

        search.textProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                // TODO Auto-generated method stub
                try {
                    String txt = search.getText();
                    if (!txt.equals("") && !items.isEmpty())
                        for (Object c : items) {
                            if (c.toString().contains(txt) == false) {
                                items.remove(c);
                            }
                        }
                } catch (Exception e) {
                }
            }
        });

        lv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                // TODO Auto-generated method stub
                int p = lv.getSelectionModel().getSelectedIndex();
                if (p >= 0) {
                    if (!ok) {
                        cursul = (Curs) items.get(p);
                        tot.getChildren().remove(profil);
                        profil = Admin.creareCurs(connection);
                        tot.getChildren().add(profil);
                    } else {
                        pers = (Persoana) items.get(p);
                        tot.getChildren().remove(profil);
                        profil = Admin.profil(superAd, connection);
                        tot.getChildren().add(profil);
                    }
                }
            }
        });

        logoutbtn.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                cursul = null;
                prof = null;
                pers = null;
                p.setScene(Login.getStartScene(p, connection));

            }
        });
        return admin;
    }

    /**
     * Creeaza pagina de unde adminii pot gestiona utilizatorii
     *
     * @param superAd
     * @param connection
     * @return
     */
    @SuppressWarnings("unchecked")
    public static VBox profil(boolean superAd, Connection connection) {
        Image pr = new Image("https://icon-library.com/images/edit-profile-icon/edit-profile-icon-10.jpg", 160, 160,
                false, true);
        ImageView icon = new ImageView(pr);

        VBox name = new VBox();
        name.setAlignment(Pos.TOP_LEFT);
        Label numetxt = new Label("Prenume:");
        numetxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField nume = new TextField();
        nume.setPromptText("...");
        nume.setFont(new Font("Arial", 14));
        nume.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        nume.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        nume.setPrefSize(500, 30);
        name.getChildren().addAll(numetxt, nume);

        VBox lastName = new VBox();
        lastName.setAlignment(Pos.TOP_LEFT);
        Label prenumetxt = new Label("Nume:");
        prenumetxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField prenume = new TextField();
        prenume.setPromptText("...");
        prenume.setFont(new Font("Arial", 14));
        prenume.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        prenume.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        prenume.setPrefSize(500, 30);
        lastName.getChildren().addAll(prenumetxt, prenume);

        VBox cNPb = new VBox();
        cNPb.setAlignment(Pos.TOP_LEFT);
        Label CNPtxt = new Label("CNP:");
        CNPtxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField CNP = new TextField();
        CNP.setPromptText("...");
        CNP.setFont(new Font("Arial", 14));
        CNP.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        CNP.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        CNP.setPrefSize(500, 30);
        cNPb.getChildren().addAll(CNPtxt, CNP);

        VBox adress = new VBox();
        adress.setAlignment(Pos.TOP_LEFT);
        Label adresatxt = new Label("Adresa:");
        adresatxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField adresa = new TextField();
        adresa.setPromptText("...");
        adresa.setFont(new Font("Arial", 14));
        adresa.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        adresa.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        adresa.setPrefSize(500, 30);
        adress.getChildren().addAll(adresatxt, adresa);

        VBox phone = new VBox();
        phone.setAlignment(Pos.TOP_LEFT);
        Label teltxt = new Label("Telefon:");
        teltxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField tel = new TextField();
        tel.setPromptText("...");
        tel.setFont(new Font("Arial", 14));
        tel.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        tel.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        tel.setPrefSize(500, 30);
        phone.getChildren().addAll(teltxt, tel);

        VBox email = new VBox();
        email.setAlignment(Pos.TOP_LEFT);
        Label mailtxt = new Label("Email:");
        mailtxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField mail = new TextField();
        mail.setPromptText("...");
        mail.setFont(new Font("Arial", 14));
        mail.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        mail.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        mail.setPrefSize(500, 30);
        email.getChildren().addAll(mailtxt, mail);

        VBox cont = new VBox();
        cont.setAlignment(Pos.TOP_LEFT);
        Label ibantxt = new Label("Cont IBAN:");
        ibantxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField iban = new TextField();
        iban.setPromptText("...");
        iban.setFont(new Font("Arial", 14));
        iban.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        iban.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        iban.setPrefSize(500, 30);
        cont.getChildren().addAll(ibantxt, iban);

        VBox password = new VBox();
        password.setAlignment(Pos.TOP_LEFT);
        Label parolatxt = new Label("Parola:");
        parolatxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField parola = new TextField();
        parola.setPromptText("...");
        parola.setFont(new Font("Arial", 14));
        parola.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        parola.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        parola.setPrefSize(500, 30);
        password.getChildren().addAll(parolatxt, parola);

        VBox contract = new VBox();
        contract.setAlignment(Pos.TOP_LEFT);
        Label contracttxt = new Label("Numar contract:");
        contracttxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField contr = new TextField();
        contr.setPromptText("...");
        contr.setFont(new Font("Arial", 14));
        contr.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        contr.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        contr.setPrefSize(500, 30);
        contract.getChildren().addAll(contracttxt, contr);

        HBox jos = new HBox();
        ObservableList functii = FXCollections.observableArrayList();
        functii.addAll("Profesor", "Student");
        if (superAd)
            functii.add("Admin");
        ComboBox functions = new ComboBox(functii);
        functions.setStyle("-fx-background-radius: 15px; -fx-background-color: white;");
        functions.setPrefSize(120, 30);

        Label nrtxt = new Label("Nr. minim ore:");
        nrtxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField nr = new TextField();
        nr.setText("0");
        nr.setFont(new Font("Arial", 14));
        nr.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        nr.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        nr.setPrefSize(60, 30);

        Label antxt = new Label("Nr. maxim ore:");
        antxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField an = new TextField();
        an.setText("0");
        an.setFont(new Font("Arial", 14));
        an.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        an.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        an.setPrefSize(60, 30);

        TextField dep = new TextField();
        dep.setText("Departament");
        dep.setFont(new Font("Arial", 14));
        dep.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        dep.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        dep.setPrefSize(120, 30);

        functions.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                if (functions.getSelectionModel().isSelected(0)) {
                    nrtxt.setVisible(true);
                    nrtxt.setText("Nr. minim ore:");
                    nr.setVisible(true);

                    antxt.setVisible(true);
                    antxt.setText("Nr. maxim ore:");
                    an.setVisible(true);

                    dep.setVisible(true);
                } else {
                    if (functions.getSelectionModel().isSelected(1)) {
                        nrtxt.setVisible(true);
                        nrtxt.setText("Nr. ore:");
                        nr.setVisible(true);

                        antxt.setVisible(true);
                        antxt.setText("An studiu:");
                        an.setVisible(true);

                        dep.setVisible(false);
                    } else {
                        nrtxt.setVisible(false);
                        nr.setVisible(false);
                        antxt.setVisible(false);
                        an.setVisible(false);
                        dep.setVisible(false);
                    }
                }

            }
        });

        nrtxt.setVisible(false);
        nr.setVisible(false);
        antxt.setVisible(false);
        an.setVisible(false);
        dep.setVisible(false);
        jos.getChildren().addAll(functions, nrtxt, nr, antxt, an, dep);
        jos.setAlignment(Pos.CENTER);
        jos.setSpacing(10);

        if (pers != null) {
            nume.setText(pers.getNume());
            prenume.setText(pers.getPrenume());
            CNP.setText(pers.getCnp());
            parola.setText(pers.getParola());
            mail.setText(pers.getMail());
            tel.setText(pers.getTelefon());
            iban.setText(pers.getContIban());
            adresa.setText(pers.getAdresa());
            contr.setText(String.valueOf(pers.getNrContract()));
            String f = null;
            if (pers.getFunctie() == 0) {
                f = "Admin";
                nrtxt.setVisible(false);
                nr.setVisible(false);
                antxt.setVisible(false);
                an.setVisible(false);
                dep.setVisible(false);
            }
            if (pers.getFunctie() == 1) {
                f = "Profesor";
                nrtxt.setVisible(true);
                nrtxt.setText("Nr. minim ore:");
                nr.setVisible(true);

                antxt.setVisible(true);
                antxt.setText("Nr. maxim ore:");
                an.setVisible(true);

                dep.setVisible(true);

                try {
                    Statement slc = connection.createStatement();
                    slc.execute("Select nr_min_ore,nr_max_ore,departament from profesor where id_utilizator="
                            + pers.getId() + ";");
                    ResultSet rs = slc.getResultSet();
                    if (rs.next()) {
                        nr.setText(String.valueOf(rs.getInt("nr_min_ore")));
                        an.setText(String.valueOf(rs.getInt("nr_max_ore")));
                        dep.setText(rs.getString("departament"));
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (pers.getFunctie() == 2) {
                f = "Student";
                nrtxt.setVisible(true);
                nrtxt.setText("Nr. ore:");
                nr.setVisible(true);

                antxt.setVisible(true);
                antxt.setText("An studiu:");
                an.setVisible(true);
                dep.setVisible(false);
                try {
                    Statement slc = connection.createStatement();
                    slc.execute("Select nr_ore,an_studiu from student where id_utilizator= " + pers.getId() + ";");
                    ResultSet rs = slc.getResultSet();
                    if (rs.next()) {
                        System.out.println(rs.getInt("nr_ore"));
                        nr.setText(String.valueOf(rs.getInt("nr_ore")));
                        an.setText(String.valueOf(rs.getInt("an_studiu")));
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            functions.getSelectionModel().select(f);
        }

        VBox texte = new VBox();
        texte.setAlignment(Pos.TOP_CENTER);
        texte.setSpacing(20);
        texte.getChildren().addAll(icon, cNPb, name, lastName, adress, phone, email, cont, password, contract, jos);
        Button save = new Button("Save");
        save.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        save.setPrefSize(120, 60);
        save.setTextFill(Color.WHITE);

        Button create = new Button("Create");
        create.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        create.setPrefSize(120, 60);
        create.setTextFill(Color.WHITE);

        Button clear = new Button("Clear");
        clear.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        clear.setPrefSize(120, 60);
        clear.setTextFill(Color.WHITE);

        Button delete = new Button("Delete");
        delete.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        delete.setPrefSize(120, 60);
        delete.setTextFill(Color.WHITE);

        HBox btt = new HBox();
        btt.getChildren().addAll(clear, delete, save, create);
        btt.setAlignment(Pos.CENTER);
        btt.setSpacing(50);

        VBox root = new VBox();
        root.getChildren().addAll(texte, btt);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setSpacing(10);

        delete.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                if (pers != null) {
                    try {
                        CallableStatement clb = connection.prepareCall("call sterge_cont(?);");
                        clb.setInt(1, pers.getId());
                        clb.execute();
                        Admin.refresh(connection);

                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                Admin.refresh(connection);
            }
        });
        create.setOnAction(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub

                try {
                    int fun;
                    if (functions.getSelectionModel().isSelected(0))
                        fun = 1;
                    else if (functions.getSelectionModel().isSelected(1))
                        fun = 2;
                    else
                        fun = 0;
                    CallableStatement clb = connection.prepareCall("call creare_cont(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    clb.setString(1, CNP.getText());
                    clb.setString(2, nume.getText());
                    clb.setString(3, prenume.getText());
                    clb.setString(4, adresa.getText());
                    clb.setString(5, tel.getText());
                    clb.setString(6, mail.getText());
                    clb.setString(7, parola.getText());
                    clb.setString(8, iban.getText());
                    clb.setInt(9, Integer.valueOf(contr.getText()));
                    clb.setInt(10, fun);
                    clb.setInt(11, Integer.valueOf(nr.getText()));
                    clb.setInt(12, Integer.valueOf(an.getText()));
                    clb.setString(13, dep.getText());
                    clb.execute();
                    Admin.refresh(connection);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        save.setOnAction(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                if (pers != null)
                    try {
                        CallableStatement clb;
                        clb = connection.prepareCall("call actualizare_cont(?,?,?)");
                        clb.setInt(1, pers.getId());
                        clb.setString(2, "nume");
                        clb.setString(3, nume.getText());
                        clb.execute();
                        clb.setString(2, "prenume");
                        clb.setString(3, prenume.getText());
                        clb.execute();
                        clb.setString(2, "adresa");
                        clb.setString(3, adresa.getText());
                        clb.execute();
                        clb.setString(2, "parola");
                        clb.setString(3, parola.getText());
                        clb.execute();
                        clb.setString(2, "telefon");
                        clb.setString(3, tel.getText());
                        clb.execute();
                        clb.setString(2, "cont_iban");
                        clb.setString(3, iban.getText());
                        clb.execute();
                        clb.setString(2, "nr_contract");
                        clb.setString(3, contr.getText());
                        clb.execute();
                        if (pers.getFunctie() == 2) {
                            clb.setString(2, "an_studiu");
                            clb.setString(3, an.getText());
                            clb.execute();
                            clb.setString(2, "nr_ore");
                            clb.setString(3, nr.getText());
                            clb.execute();
                        }
                        if (pers.getFunctie() == 1) {
                            clb.setString(2, "nr_min_ore");
                            clb.setString(3, nr.getText());
                            clb.execute();
                            clb.setString(2, "nr_max_ore");
                            clb.setString(3, an.getText());
                            clb.execute();
                            clb.setString(2, "departament");
                            clb.setString(3, dep.getText());
                            clb.execute();
                        }
                        Admin.refresh(connection);

                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        });

        clear.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                nume.clear();
                prenume.clear();
                CNP.clear();
                parola.clear();
                mail.clear();
                tel.clear();
                iban.clear();
                adresa.clear();
                contr.clear();
                an.clear();
                nr.clear();
                Label l1 = new Label("Da");
                Label l2 = new Label("nu");
                l1.setAlignment(Pos.CENTER_LEFT);
                l2.setAlignment(Pos.CENTER_RIGHT);
                Label l3 = new Label(l1.getText() + l2.getText());
                l3.setPrefSize(300, 40);
                dep.clear();
                functions.getSelectionModel().clearSelection();
            }
        });

        return root;
    }

    /**
     * Pagina de unde adminii pot gestiona cursurile
     *
     * @param connection
     * @return
     */
    @SuppressWarnings("unchecked")
    public static VBox creareCurs(Connection connection) {
        VBox root = new VBox();
        Image pr = new Image("https://cdn-icons-png.flaticon.com/512/4762/4762232.png", 200, 200, false, true);
        ImageView icon = new ImageView(pr);

        VBox name = new VBox();
        name.setAlignment(Pos.TOP_LEFT);
        Label numetxt = new Label("Nume curs:");
        numetxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField nume = new TextField();
        nume.setPromptText("...");
        nume.setFont(new Font("Arial", 14));
        nume.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        nume.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        nume.setPrefSize(500, 30);
        name.getChildren().addAll(numetxt, nume);

        VBox des = new VBox();
        des.setAlignment(Pos.TOP_LEFT);
        Label descrieretxt = new Label("Descriere curs:");
        descrieretxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField descriere = new TextField();
        descriere.setPromptText("...");
        descriere.setFont(new Font("Arial", 14));
        descriere.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        descriere.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        descriere.setPrefSize(500, 30);
        des.getChildren().addAll(descrieretxt, descriere);

        HBox number = new HBox();
        Label nrtxt = new Label("Numar maxim student:");
        nrtxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField nr = new TextField();
        nr.setPromptText("...");
        nr.setFont(new Font("Arial", 14));
        nr.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        nr.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        nr.setPrefSize(70, 30);
        number.getChildren().addAll(nrtxt, nr);
        number.setSpacing(20);
        number.setAlignment(Pos.CENTER);

        Button save = new Button("Save");
        save.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        save.setPrefSize(120, 60);
        save.setTextFill(Color.WHITE);

        Button create = new Button("Create");
        create.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        create.setPrefSize(120, 60);
        create.setTextFill(Color.WHITE);

        Button clear = new Button("Clear");
        clear.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        clear.setPrefSize(120, 60);
        clear.setTextFill(Color.WHITE);

        Button delete = new Button("Delete");
        delete.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        delete.setPrefSize(120, 60);
        delete.setTextFill(Color.WHITE);

        HBox btt = new HBox();
        btt.getChildren().addAll(clear, delete, save, create);
        btt.setAlignment(Pos.CENTER);
        btt.setSpacing(50);

        HBox jos = new HBox();
        Label txtCurs = new Label("Pentru cursul:");
        txtCurs.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Label numeCurs = new Label("...");
        numeCurs.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, 18));
        numeCurs.setStyle("-fx-text-fill: white; -fx-background-color: #2a2222 ;-fx-background-radius: 15px; ");
        numeCurs.setPrefSize(250, 25);
        numeCurs.setAlignment(Pos.CENTER);
        jos.setAlignment(Pos.CENTER);
        jos.setSpacing(20);
        jos.getChildren().addAll(txtCurs, numeCurs);
        jos.setPadding(new Insets(80, 0, 0, 0));

        HBox addProf = new HBox();
        ObservableList profesori = FXCollections.observableArrayList();
        try {
            Statement slc = connection.createStatement();
            slc.execute("Select * from utilizator where functie = 1");
            ResultSet rs = slc.getResultSet();
            while (rs.next()) {
                profesori.add(new Persoana(rs.getInt("id"), rs.getString("cnp"), rs.getString("nume"),
                        rs.getString("prenume"), rs.getString("adresa"), rs.getString("telefon"), rs.getString("mail"),
                        rs.getString("parola"), rs.getString("cont_iban"), rs.getInt("nr_contract"),
                        rs.getInt("functie")));
            }
        } catch (SQLException e) { // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ListView profi = new ListView(profesori);
        profi.setPrefSize(350, 300);
        Button add = new Button("Add");
        add.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        add.setPrefSize(120, 60);
        add.setTextFill(Color.WHITE);

        Button scoate = new Button("remove");
        scoate.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 15px;");
        scoate.setPrefSize(120, 60);
        scoate.setTextFill(Color.WHITE);
        addProf.getChildren().addAll(profi, add, scoate);
        addProf.setSpacing(20);
        addProf.setAlignment(Pos.CENTER);

        profi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                // TODO Auto-generated method stub
                int index = profi.getSelectionModel().getSelectedIndex();
                prof = (Persoana) profesori.get(index);

            }
        });

        add.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                if (profi != null && cursul != null) {
                    try {
                        CallableStatement clb = connection.prepareCall("call inserare_sustine_curs(?,?)");
                        clb.setInt(1, cursul.getId());
                        clb.setInt(2, prof.getId());
                        clb.execute();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        });

        scoate.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                if (profi != null && cursul != null) {
                    try {
                        Statement slc = connection.createStatement();
                        slc.execute("Delete from sustine_curs where id_curs = " + cursul.getId() + " and id_profesor= "
                                + prof.getId() + " ;");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        });

        if (cursul != null) {
            nume.setText(cursul.getNume());
            descriere.setText(cursul.getDescriere());
            nr.setText(String.valueOf(cursul.getNr_max()));
            numeCurs.setText(cursul.getNume());
        }

        clear.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                nume.clear();
                descriere.clear();
                nr.clear();
                numeCurs.setText(null);
            }
        });

        delete.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                if (cursul != null) {
                    try {
                        CallableStatement clb = connection.prepareCall("call sterge_curs(?)");
                        clb.setString(1, cursul.getNume());
                        clb.execute();
                        Admin.refresh(connection);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        create.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                if (cursul != null) {
                    try {
                        CallableStatement clb = connection.prepareCall("call creare_curs(?,?,?)");
                        clb.setString(2, nume.getText());
                        clb.setString(3, descriere.getText());
                        clb.setInt(1, Integer.valueOf(nr.getText()));
                        clb.execute();
                        Admin.refresh(connection);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        save.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                if (cursul != null) {
                    try {
                        Statement slc = connection.createStatement();
                        slc.execute("UPDATE curs SET nume= '" + nume.getText() + "' where id_curs= " + cursul.getId()
                                + " ;");
                        slc.execute("UPDATE curs SET descriere = '" + descriere.getText() + "' where id_curs = "
                                + cursul.getId() + " ;");
                        slc.execute("UPDATE curs SET nr_max_studenti = " + Integer.valueOf(nr.getText())
                                + " where id_curs = " + cursul.getId() + " ;");
                        Admin.refresh(connection);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        root.getChildren().addAll(icon, name, des, number, btt, jos, addProf);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        return root;
    }

    /**
     * Reincarca listele de cursuri sau utilizatori dupa modificarile acestora/
     * Refresh
     *
     * @param connection
     */
    public static void refresh(Connection connection) {
        if (ok) {
            try {
                useri.clear();
                Statement slc = connection.createStatement();
                slc.execute("Select * from utilizator");
                ResultSet rs = slc.getResultSet();
                while (rs.next()) {
                    useri.add(new Persoana(rs.getInt("id"), rs.getString("cnp"), rs.getString("nume"),
                            rs.getString("prenume"), rs.getString("adresa"), rs.getString("telefon"),
                            rs.getString("mail"), rs.getString("parola"), rs.getString("cont_iban"),
                            rs.getInt("nr_contract"), rs.getInt("functie")));
                }
                items.clear();
                items.addAll(useri);
                lv.setItems(items);
            } catch (SQLException e) { // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                cursuri.clear();
                Statement slc = connection.createStatement();
                slc.execute("Select * from curs");
                ResultSet rs = slc.getResultSet();
                while (rs.next()) {
                    cursuri.add(new Curs(rs.getInt("id_curs"), rs.getString("nume"), rs.getString("descriere"),
                            rs.getInt("nr_max_studenti")));
                }
                items.clear();
                items.addAll(cursuri);
                lv.setItems(cursuri);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

}
