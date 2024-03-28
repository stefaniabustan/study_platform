package com.example.interfata;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.input.*;

import java.io.FileOutputStream;
import java.sql.*;

public class Student extends Persoana {

    static ObservableList<String> curs_note;
    static ObservableList<Integer> idCursuri;
    static ObservableList<String> mesajele;
    static ObservableList<String> num;
    static ObservableList<Integer> iduri;
    static ObservableList<Integer> iduri2;
    static int index;
    static int index2;
    static int count;
    static Boolean ok;
    static VBox dreapta;
    static HBox tot;
    public static Scene getStudentScene(Persoana pers, Connection connection, Stage p) {

         dreapta =new VBox();
        dreapta=Student.profil(pers);
        VBox stanga = Student.left(pers,connection, p);
        tot = new HBox();
        tot.getChildren().addAll(stanga,dreapta);
        tot.setAlignment(Pos.CENTER_LEFT);
        tot.setPadding(new Insets(0, 0, 0, 0));
        BackgroundImage myBI = new BackgroundImage(
                new Image("https://coolbackgrounds.io/images/backgrounds/index/sea-edge-79ab30e2.png", 1920, 1080,
                        false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        tot.setBackground(new Background(myBI));

        //addListener

        Scene scene = new Scene(tot, 1920, 1080);
        return scene;

    }

    public static VBox left(Persoana pers,Connection connection, Stage p) {


        VBox root = new VBox();


        HBox top = new HBox();
        Image ceva = new Image("https://www.shareicon.net/data/2016/01/12/701853_education_512x512.png", 100, 100,
                false, true);
        ImageView c = new ImageView(ceva);

        Label title = new Label("Student");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 50));
        top.getChildren().addAll(c, title);
        top.setAlignment(Pos.CENTER_LEFT);
        top.setSpacing(30);

        Button profil = new Button("Profil");
        profil.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        profil.setMaxSize(400, 100);
        profil.setFont(new Font(30));
        profil.setTextFill(Color.WHITE);

        Button curs = new Button("Cursuri ");
        curs.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        curs.setMaxSize(400, 100);
        curs.setFont(new Font(30));
        curs.setTextFill(Color.WHITE);

        Button calendar = new Button("Calendar");
        calendar.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        calendar.setMaxSize(400, 100);
        calendar.setFont(new Font(30));
        calendar.setTextFill(Color.WHITE);
        root.setPadding(new Insets(10, 10, 0, 10));


        Button grupuri = new Button("Grupuri");
        grupuri.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        grupuri.setMaxSize(400, 100);
        grupuri.setFont(new Font(30));
        grupuri.setTextFill(Color.WHITE);
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
        root.getChildren().addAll(top, profil, curs,grupuri, calendar,jos);


        profil.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                tot.getChildren().remove(dreapta);
                dreapta=Student.profil(pers);
                tot.getChildren().add(dreapta);
            }
        });
        curs.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                tot.getChildren().remove(dreapta);
                dreapta=Student.cursuri(pers,connection,p);
                tot.getChildren().add(dreapta);            }
        });
        calendar.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                tot.getChildren().remove(dreapta);
                dreapta=Profesor.calendar(pers,true,connection);
                tot.getChildren().add(dreapta);
            }
        });
        grupuri.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                tot.getChildren().remove(dreapta);
                dreapta=Student.grupuri(pers,connection,p);
                tot.getChildren().add(dreapta);
            }
        });
        logoutbtn.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-gener
                p.setScene(Login.getStartScene(p,connection));
            }
        });

        return root;
    }

    public static VBox profil(Persoana pers) {
        Image pr = new Image("https://icons.veryicon.com/png/o/internet--web/55-common-web-icons/person-4.png", 230, 230,
                false, true);
        ImageView icon = new ImageView(pr);

        VBox name = new VBox();
        name.setAlignment(Pos.TOP_LEFT);
        Label numetxt = new Label("Nume:");
        numetxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField nume = new TextField();
        nume.setFont(new Font("Arial", 14));
        nume.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        nume.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        nume.setPrefSize(500, 30);
        name.getChildren().addAll(numetxt, nume);
        nume.setEditable(false);


       VBox lastName = new VBox();
        lastName.setAlignment(Pos.TOP_LEFT);
        Label prenumetxt = new Label("Prenume:");
        prenumetxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField prenume = new TextField();
        prenume.setFont(new Font("Arial", 14));
        prenume.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        prenume.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        prenume.setPrefSize(500, 30);
        lastName.getChildren().addAll(prenumetxt, prenume);
        prenume.setEditable(false);

        VBox cNPb = new VBox();
        cNPb.setAlignment(Pos.TOP_LEFT);
        Label CNPtxt = new Label("CNP:");
        CNPtxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField CNP = new TextField();
        CNP.setFont(new Font("Arial", 14));
        CNP.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        CNP.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        CNP.setPrefSize(500, 30);
        cNPb.getChildren().addAll(CNPtxt, CNP);
        CNP.setEditable(false);

        VBox adress = new VBox();
        adress.setAlignment(Pos.TOP_LEFT);
        Label adresatxt = new Label("Adresa:");
        adresatxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField adresa = new TextField();
        adresa.setFont(new Font("Arial", 14));
        adresa.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        adresa.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        adresa.setPrefSize(500, 30);
        adress.getChildren().addAll(adresatxt, adresa);
        adresa.setEditable(false);

        VBox phone = new VBox();
        phone.setAlignment(Pos.TOP_LEFT);
        Label teltxt = new Label("Numarul de telefon:");
        teltxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField tel = new TextField();
        tel.setFont(new Font("Arial", 14));
        tel.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        tel.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        tel.setPrefSize(500, 30);
        phone.getChildren().addAll(teltxt, tel);
        tel.setEditable(false);

        VBox email = new VBox();
        email.setAlignment(Pos.TOP_LEFT);
        Label mailtxt = new Label("Mail:");
        mailtxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField mail = new TextField();
        mail.setFont(new Font("Arial", 14));
        mail.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        mail.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        mail.setPrefSize(500, 30);
        email.getChildren().addAll(mailtxt, mail);
        mail.setEditable(false);

        VBox cont = new VBox();
        cont.setAlignment(Pos.TOP_LEFT);
        Label ibantxt = new Label("Contul IBAN:");
        ibantxt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField iban = new TextField();
        iban.setFont(new Font("Arial", 14));
        iban.setStyle(
                "-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        iban.setBackground(
                new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        iban.setPrefSize(500, 30);
        cont.getChildren().addAll(ibantxt, iban);
        iban.setEditable(false);


        VBox texte = new VBox();
        texte.setSpacing(20);
        texte.getChildren().addAll(icon, cNPb, name, lastName, adress, phone, email, cont);
        texte.setAlignment(Pos.TOP_CENTER);

        VBox root = new VBox();
        root.getChildren().addAll(texte);
        root.setAlignment(Pos.TOP_RIGHT);
        root.setSpacing(10);
        root.setPadding(new Insets(10,10,10,180));

        nume.setText(pers.getNume());
        prenume.setText(pers.getPrenume());
        CNP.setText(pers.getCnp());
        adresa.setText(pers.getAdresa());
        mail.setText(pers.getMail());
        iban.setText(pers.getContIban());
        tel.setText(pers.getTelefon());
        return root;
    }

    public static VBox cursuri(Persoana pers,Connection connection, Stage p) {
        idCursuri=FXCollections.observableArrayList();
        VBox root = new VBox();
        HBox antet = new HBox();
        Label curs = new Label("Cursul");
        curs.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
        curs.setStyle("-fx-background-color:BLACK; -fx-background: 20px; -fx-border-color: white");
        curs.setPrefSize(210, 30);
        curs.setTextFill(Color. WHITE);
        curs.setAlignment(Pos.CENTER);

        Label seminar = new Label("Seminar");
        seminar.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
        seminar.setStyle("-fx-background-color: #2a2222; -fx-background: 20px;");
        seminar.setPrefSize(100, 30);
        seminar.setTextFill(Color.WHITE);
        seminar.setAlignment(Pos.CENTER);

        Label examen = new Label("Examen");
        examen.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
        examen.setStyle("-fx-background-color: #2a2222; -fx-background: 20px;");
        examen.setPrefSize(100, 30);
        examen.setTextFill(Color.WHITE);
        examen.setAlignment(Pos.CENTER);


        Label lab = new Label("Laborator");
        lab.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
        lab.setStyle("-fx-background-color: #2a2222; -fx-background: 20px;");
        lab.setPrefSize(100, 30);
        lab.setTextFill(Color.WHITE);
        lab.setAlignment(Pos.CENTER);

        Label c = new Label("Curs");
        c.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
        c.setStyle("-fx-background-color: #2a2222; -fx-background: 20px;");
        c.setPrefSize(100, 30);
        c.setTextFill(Color.WHITE);
        c.setAlignment(Pos.CENTER);

        Label clv = new Label("Colocviu");
        clv.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
        clv.setStyle("-fx-background-color: #2a2222; -fx-background: 20px;");
        clv.setPrefSize(100, 30);
        clv.setTextFill(Color.WHITE);
        clv.setAlignment(Pos.CENTER);

        Label med = new Label("Nota finala");
        med.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
        med.setStyle("-fx-background-color: #2a2222; -fx-background: 20px;");
        med.setPrefSize(110, 30);
        med.setTextFill(Color.WHITE);
        med.setAlignment(Pos.CENTER);

        // root.setPadding(new Insets(100, 10, 0, 10));


        ObservableList<Label> cursuri=FXCollections.observableArrayList( );
        ListView lista= new ListView(cursuri);
        lista.setPrefSize(400,350);
        lista.setEditable(true);


        VBox root2 = new VBox();

        Label insc = new Label("Cursuri");
        insc.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 18));
        insc.setStyle("-fx-background-color:BLACK; -fx-background: 20px; ");
        insc.setPrefSize(280, 30);
        insc.setTextFill(Color. WHITE);
        insc.setAlignment(Pos.CENTER);

        HBox h1=new HBox();
        Button inscris = new Button("Inscrie-te!");
        inscris.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        inscris.setPrefSize(120, 50);
        inscris.setFont(new Font(20));
        inscris.setTextFill(Color.WHITE);

        Button desc = new Button("Descarca");
        desc.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 10px;");
        desc.setPrefSize(80, 20);
        desc.setFont(new Font(13));
        desc.setTextFill(Color.WHITE);
        desc.setAlignment(Pos.CENTER);

        desc.setOnAction(new EventHandler() {
            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                try {

                    FileOutputStream fos = new FileOutputStream("Catalog.xls", true); // true for append mode
                    String str = "Curs\tNota Seminar\tNota Examen\tNota Laborator\tNota Curs\tNota Colocviu\tNota Finala";
                    for (String i : curs_note) {
                        String[] parti = i.split("\s+");
                        str += '\n';
                        for (String j : parti) {
                            str += j + "\t";
                        }
                    }

                    byte[] b = str.getBytes(); // converts string into bytes
                    fos.write(b); // writes bytes into file
                    fos.close(); // close the file
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        ObservableList<Label> inscriere=FXCollections.observableArrayList( );
        ListView lista2= new ListView(inscriere);
        lista2.setMaxSize(280,250);
        lista2.setEditable(true);//sus dr jos st

        Label text=new Label("Cursuri: ");
        text.setFont(new Font(18));
        text.setTextFill(Color.BLACK);

        h1.getChildren().addAll(text,lista2, inscris);
        h1.setAlignment(Pos.CENTER_LEFT);
        h1.setSpacing(10);
        //h1.setPadding(new Insets(60,100,0,100));

        HBox ceva = new HBox();
        ceva.setSpacing(20);

        Button renunta = new Button("Renunta!");
        renunta.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        renunta.setPrefSize(200, 70);
        renunta.setAlignment(Pos.CENTER);
        renunta.setFont(new Font(20));
        renunta.setTextFill(Color.WHITE);
        renunta.setOnAction(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                index = lista.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    try {
                        CallableStatement clb = connection.prepareCall("call renuntare_curs_student(?,?)");
                        clb.setInt(1, pers.getId());
                        clb.setInt(2, idCursuri.get(index));
                        clb.execute();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        ceva.setAlignment(Pos.CENTER);
        ceva.getChildren().addAll(root, renunta);
        root2.getChildren().addAll(ceva, h1);

        antet.getChildren().addAll(curs, seminar, lab, examen,clv,c,med);
        antet.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(antet,lista,desc);
       root.setAlignment(Pos.TOP_LEFT);

       root.setPadding(new Insets(50,10,30,0));

        curs_note= FXCollections.observableArrayList();
        try {
            CallableStatement clb= connection.prepareCall("call vizualizare_note(?);");
            clb.setInt(1,pers.getId());
            clb.execute();
            ResultSet rs=clb.getResultSet();
            while(rs.next())
            {
                int nrSpace=0;
                String s=null;
                nrSpace=75-rs.getString("CURS").length();
                s=rs.getString("CURS");
                for(int i=0;i<nrSpace;i++)
                    s+=" ";
                nrSpace=27-rs.getString("Nota seminar").length();
                s+=rs.getString("Nota seminar");
                for(int i=0;i<nrSpace;i++)
                    s+=" ";
                nrSpace=27-rs.getString("Nota examen").length();
                s+=rs.getString("Nota examen");
                for(int i=0;i<nrSpace;i++)
                    s+=" ";
                s+="  ";
                nrSpace=27-rs.getString("Nota laborator").length();
                s+=rs.getString("Nota laborator");
                for(int i=0;i<nrSpace;i++)
                    s+=" ";
                nrSpace=27-rs.getString("Nota curs").length();
                s+=rs.getString("Nota curs");
                for(int i=0;i<nrSpace;i++)
                    s+=" ";
                s+="  ";
                nrSpace=27-rs.getString("Nota colocviu").length();
                s+=rs.getString("Nota colocviu");
                for(int i=0;i<nrSpace;i++)
                    s+=" ";

                idCursuri.add(rs.getInt("ID"));
                Double media;
                Statement slc=null;
                slc= connection.createStatement();

                {
                    slc.execute("SELECT procente_seminar, procente_curs, procente_laborator, procente_colocviu, procente_examen from sustine_curs where id_curs= "+rs.getInt("ID")+" and id_profesor="+rs.getInt("ID_PROF")+";");
                    ResultSet rs3= slc.getResultSet();
                    if(rs3.next())
                    {
                        media= Double.valueOf(rs.getInt("Nota examen")*rs3.getInt("procente_examen"));
                        media=media+ Double.valueOf(rs.getInt("Nota curs")*rs3.getInt("procente_curs"));
                        media=media+ Double.valueOf(rs.getInt("Nota laborator")*rs3.getInt("procente_laborator"));
                        media= media+Double.valueOf(rs.getInt("Nota colocviu")*rs3.getInt("procente_colocviu"));
                        media= media+Double.valueOf(rs.getInt("Nota seminar")*rs3.getInt("procente_seminar"));
                        media/=100;
                        s+= media;
                    }
                }


                curs_note.add(s);
            }
            lista.setItems(curs_note);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Statement slc=null;
        try {
            slc= connection.createStatement();
            ResultSet rs;
            slc.execute("SELECT distinct id_curs FROM inscris_curs WHERE id_student = "+pers.getId()+";");
            rs = slc.getResultSet();

            iduri= FXCollections.observableArrayList();//pt id_student
            iduri2= FXCollections.observableArrayList();
            num= FXCollections.observableArrayList();
            while(rs.next()) {
                iduri.add(rs.getInt("id_curs"));
            }
            int k=0;
            slc.execute("SELECT id_curs FROM curs;");
            rs=slc.getResultSet();
            while(rs.next() && k<=10)
            {
                Boolean ok=true;
                for(int i=0;i<iduri.size();i++)
                    if(iduri.get(i)==rs.getInt("id_curs"))
                    {
                        ok=false;
                        break;
                    }
                if(ok==true)
                {
                    iduri2.add(rs.getInt("id_curs"));
                    k++;
                }
            }
            for(int i=0;i<iduri2.size();i++)
            {
                slc.execute("SELECT nume FROM curs WHERE id_curs= " + iduri2.get(i) + ";");
                rs = slc.getResultSet();
                if(rs.next())
                num.add(rs.getString("nume"));
            }
            lista2.setItems(num);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lista2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                index = lista2.getSelectionModel().getSelectedIndex();
                if (index >= 0)
                {
                    inscris.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            try {
                                CallableStatement clb= connection.prepareCall("call inserare_inscris_curs(?,?,?);");
                                Statement slc=null;
                                slc=connection.createStatement();
                                ResultSet rs;
                                slc.execute("SELECT id_profesor FROM inscris_curs WHERE id_curs= "+iduri2.get(index)+" group by id_profesor order by  count(*);");
                                rs=slc.getResultSet();
                                if(rs.next())
                                {

                                    clb.setInt(1,iduri2.get(index));
                                    clb.setInt(2, pers.getId());
                                    clb.setInt(3, rs.getInt("id_profesor"));
                                    clb.execute();
                                }

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });
                }
            }
        });
        return root2;
    }
    public static VBox grupuri( Persoana pers,Connection connection, Stage p) {
        VBox root = new VBox();
        HBox antet = new HBox();
        Label gr = new Label("Grupuri");
        gr.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 20));
        gr.setStyle("-fx-background-color:BLACK; -fx-background: 20px; ");
        gr.setPrefSize(750, 50);
        gr.setTextFill(Color. WHITE);
        gr.setAlignment(Pos.CENTER);


        ObservableList<Label> grupuri=FXCollections.observableArrayList();
        ListView lista= new ListView(grupuri);
        lista.setPrefSize(400,500);
        lista.setEditable(true);

        VBox root2 = new VBox();
        VBox root3 = new VBox();

        Label num = new Label("Nume grup");
        num.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 26));
        num.setTextFill(Color. BLACK);

        Button open = new Button("Deschidere grup");
        open.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        open.setPrefSize(280, 50);
        open.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 20));
        open.setTextFill(Color.WHITE);
        open.setAlignment(Pos.CENTER);

        Button inscriere = new Button("Inscriere grup");
        inscriere.setStyle("-fx-background-color: #2a2222; -fx-background-radius: 20px;");
        inscriere.setPrefSize(280, 50);
        inscriere.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 20));
        inscriere.setTextFill(Color.WHITE);
        inscriere.setAlignment(Pos.CENTER);

        root3.setAlignment(Pos.CENTER);
        root3.setSpacing(10);

        HBox root4=new HBox();
        //sugestii studenti
        VBox rootIm=new VBox();
        Image ceva = new Image("https://static.thenounproject.com/png/138580-200.png", 130, 130,
                false, true);
        ImageView c = new ImageView(ceva);
        Label title1 = new Label("Adauga");
        title1.setFont(Font.font("Arial", FontWeight.MEDIUM, 13));
        rootIm.setAlignment(Pos.CENTER);
        rootIm.setSpacing(-10);
        rootIm.getChildren().addAll(c,title1);

        VBox rootIm2=new VBox();
        Image ceva2 = new Image("https://static.thenounproject.com/png/138580-200.png", 130, 130,
                false, true);
        ImageView c2 = new ImageView(ceva);
        Label title2 = new Label("Adauga");
        title2.setFont(Font.font("Arial", FontWeight.MEDIUM, 13));
        rootIm2.setAlignment(Pos.CENTER);
        rootIm2.setSpacing(-10);
        rootIm2.getChildren().addAll(c2,title2);

        VBox rootIm3=new VBox();
        Image ceva3 = new Image("https://static.thenounproject.com/png/138580-200.png", 130, 130,
                false, true);
        ImageView c3 = new ImageView(ceva);
        Label title3 = new Label("Adauga");
        title3.setFont(Font.font("Arial", FontWeight.MEDIUM, 13));
        rootIm3.setAlignment(Pos.CENTER);
        rootIm3.setSpacing(-10);
        rootIm3.getChildren().addAll(c3,title3);
        root3.getChildren().add(num);
        curs_note= FXCollections.observableArrayList();
        iduri= FXCollections.observableArrayList();
        iduri2= FXCollections.observableArrayList();
        Statement slc=null;
        try {
            slc = connection.createStatement();
            slc.execute("SELECT grup.nume as nume, grup.id as id FROM grup, inscris_curs where grup.id_curs=inscris_curs.id_curs and "+
                    " inscris_curs.id_student= "+pers.getId()+";");
            ResultSet rs = slc.getResultSet();
            while(rs.next())
            {
                curs_note.add(rs.getString("nume"));
                iduri.add(rs.getInt("id"));
            }
            lista.setItems(curs_note);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        lista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                index=lista.getSelectionModel().getSelectedIndex();
                if(index>=0)
                {
                    Statement slc=null;
                    try {
                        slc= connection.createStatement();
                        slc.execute("SELECT id_student FROM inscris_grup where id_grup= " + iduri.get(index)+";");
                        ResultSet rs = slc.getResultSet();
                         ok=false;
                        while(rs.next())
                        {
                            if(rs.getInt("id_student")==pers.getId())
                            {
                                ok=true;
                            }
                            iduri2.add(rs.getInt("id_student"));
                        }
                        root4.getChildren().clear();
                        if(ok==false)//nu e inscris in grup
                        {
                            if(root3.getChildren().contains(open))
                            root3.getChildren().remove(open);
                            if(!root3.getChildren().contains(inscriere))
                                root3.getChildren().add(inscriere );
                            root4.getChildren().addAll(root3);
                            inscriere.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                try {
                                    CallableStatement clb = connection.prepareCall("call inscriere_grup(?,?);");

                                    clb.setInt(1, pers.getId());
                                    clb.setInt(2, iduri.get(index));

                                    clb.execute();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });

                        }
                        else
                        {

                            if(root3.getChildren().contains(inscriere))
                                root3.getChildren().remove(inscriere);
                            if(!root3.getChildren().contains(open))
                            root3.getChildren().add(open);
                           // title1.setText("Adauga!");
                            //title2.setText("Adauga!");
                            //title3.setText("Adauga!");
                            open.setOnMouseClicked(new EventHandler() {
                                @Override
                                public void handle(Event arg0) {
                                    tot.getChildren().remove(dreapta);
                                    dreapta=Student.mesaje(iduri.get(index),pers,connection,p);
                                    tot.getChildren().add(dreapta);
                                    // TODO Auto-gener
                                }
                            });
                                    //in iduri2 momentan am toate id_student care is inscrisi la cursul pe care am apasat
                                    slc.execute("SELECT utilizator.id as id, utilizator.nume as nume, utilizator.prenume as prenume FROM utilizator, mesaj where utilizator.id=mesaj.id_student " +
                                            "  group by utilizator.id  order by count(*) DESC" +";");
                                    rs=slc.getResultSet();//toti studentii
                                     count=0;//imi trebuie 3 studenti
                                    String [] vec=new String[4];
                                    Integer [] id_vec=new Integer[4];
                                    while(rs.next() && count<3)
                                    {
                                        Boolean este=false;
                                        for(int i=0;i<iduri2.size();i++)
                                            if(rs.getInt("id")==iduri2.get(i))
                                            {
                                                este=true;
                                                break;
                                            }
                                        if(este==false)//ii adaug
                                        {
                                            System.out.println(rs.getString("nume"));
                                            Statement slc2=null;
                                            slc2= connection.createStatement();
                                            slc2.execute("SELECT * from inscris_curs, grup where inscris_curs.id_student= "+rs.getInt("id")
                                                    +" and inscris_curs.id_curs=grup.id_curs and grup.id= "+iduri.get(index)+" ; ");
                                            ResultSet rs2=slc2.getResultSet();
                                            if(rs2.next())
                                            {
                                                vec[count]=rs.getString("nume");
                                                vec[count]+=" ";
                                                vec[count]+=rs.getString("prenume");
                                                id_vec[count]=rs.getInt("id");
                                                count++;

                                            }
                                        }

                                    }
                            if(count>=1)
                            {
                                rootIm.getChildren().remove(title1);
                                title1.setText(vec[0]);
                                rootIm.getChildren().add(title1);
                            }
                            if(count>=2)
                                title2.setText(vec[1]);
                            if(count>=3)
                                title3.setText(vec[2]);
                            root4.getChildren().addAll(root3, rootIm, rootIm2, rootIm3);




                            c.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                            if(count>=1)
                                            {
                                                try {
                                                    CallableStatement clb= connection.prepareCall("call inscriere_grup(?,?);");

                                                    clb.setInt(1, id_vec[0]);
                                                    clb.setInt(2,iduri.get(index) );

                                                    clb.execute();
                                                } catch (SQLException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        }
                                    });
                                    c2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                            if(count>=2)
                                            {
                                                try {
                                                    CallableStatement clb= connection.prepareCall("call inscriere_grup(?,?);");

                                                    clb.setInt(1, id_vec[1]);
                                                    clb.setInt(2,iduri.get(index) );

                                                    clb.execute();
                                                } catch (SQLException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        }
                                    });
                                    c3.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                            if(count>=3)
                                            {
                                                try {
                                                    CallableStatement clb= connection.prepareCall("call inscriere_grup(?,?);");
                                                    clb.setInt(1, id_vec[2]);
                                                    clb.setInt(2,iduri.get(index) );
                                                    clb.execute();
                                                } catch (SQLException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        }
                                    });
                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }

            }
        });




        root4.setAlignment(Pos.CENTER_LEFT);
        root4.setSpacing(30);

        root2.getChildren().addAll(root,root4);

        antet.getChildren().addAll(gr);
        antet.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(antet,lista);
        root.setAlignment(Pos.TOP_LEFT);

        root.setPadding(new Insets(50,10,30,0));
        return root2;
    }

    public static VBox mesaje(int idul, Persoana pers,Connection connection, Stage p) {

        VBox root= new VBox();
        HBox antetTabel= new HBox();

        HBox rootIm=new HBox();
        Image ceva = new Image("https://icons.veryicon.com/png/o/miscellaneous/fs-icon/live-chat.png", 110, 110,
                false, true);
        ImageView c = new ImageView(ceva);
        Label num = new Label("Nume grup");
        num.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 40));
        rootIm.getChildren().addAll(c,num);
        num.setTextFill(Color. BLACK);
        rootIm.setAlignment(Pos.CENTER_LEFT);
        rootIm.setSpacing(30);

        VBox st=new VBox();
        ListView lista= new ListView();
        lista.setPrefSize(300,500);
        lista.setEditable(true);

        Label pp = new Label("Participanti");
        pp.setFont(Font.font("Arial", FontWeight.MEDIUM, 24));
        pp.setStyle("-fx-background-color: #2a2222; -fx-background: 20px; -fx-border-color: WHITE; -fx-border-width: 2px;");
        pp.setPrefSize(300, 30);
        pp.setTextFill(Color.WHITE);
        pp.setAlignment(Pos.CENTER);

        st.getChildren().addAll(pp,lista);

        VBox dr=new VBox();
        VBox chatt=new VBox();
        Label chat = new Label("Chat");
        chat.setFont(Font.font("Arial", FontWeight.MEDIUM, 24));
        chat.setStyle("-fx-background-color: #2a2222; -fx-background: 20px;");
        chat.setPrefSize(700, 30);
        chat.setTextFill(Color.WHITE);
        chat.setAlignment(Pos.CENTER);


        ListView mess= new ListView();
        mess.setPrefSize(300,480);
        mess.setEditable(true);

        TextField scrie=new TextField();
        scrie.setPromptText("Scrie mesaj...                                                                                                                       (max 100 caractere)");
        scrie.setFont(new Font("Arial", 14));
        scrie.setStyle("-fx-text-inner-color: white; -fx-background-color: #2a2222 ;\n" + "-fx-background-radius: 15px;");
        scrie.setBackground(new Background(new BackgroundFill(Color.web("#2a2222", 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        scrie.setPrefSize(450, 40);

        chatt.getChildren().addAll(mess,scrie);
        chatt.setSpacing(-20);
        dr.getChildren().addAll(chat,chatt);

        antetTabel.getChildren().addAll(st,dr);

        root.getChildren().addAll(rootIm, antetTabel);
        root.setSpacing(40);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10,10,30,0));

        curs_note= FXCollections.observableArrayList();
        mesajele= FXCollections.observableArrayList();
        iduri= FXCollections.observableArrayList();
        iduri2= FXCollections.observableArrayList();
        Statement slc=null;
        try {
            slc = connection.createStatement();
            slc.execute("SELECT id_student  FROM inscris_grup where id_grup= "+ idul+";");
            ResultSet rs = slc.getResultSet();
            while(rs.next())
            {
                iduri.add(rs.getInt("id_student"));
            }
            for(int i=0;i<iduri.size();i++)
            {
                slc.execute("SELECT nume, prenume  FROM utilizator where id= "+ iduri.get(i)+";");
                rs = slc.getResultSet();
                if(rs.next())
                {
                    String nume_prenume=rs.getString("nume");
                    nume_prenume+=" ";
                    nume_prenume+=rs.getString("prenume");
                    curs_note.add(nume_prenume);
                }
            }
            lista.setItems(curs_note);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        incarcare_mesaj(connection, idul, mess);

        scrie.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    CallableStatement clb= null;
                    try {
                        clb = connection.prepareCall("call creare_mesaj(?,?,?);");
                        clb.setInt(1,pers.getId());
                        clb.setInt(2,idul);
                        clb.setString(3, scrie.getText());
                        scrie.setText(null);

                        clb.execute();
                        incarcare_mesaj(connection, idul, mess);


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        });


        return root;
    }
    public static void incarcare_mesaj(Connection connection, int idul,ListView mess )
    {
        try {
            mesajele.clear();
            CallableStatement clb= connection.prepareCall("call mesaje_grup(?);");
            clb.setInt(1,idul);
            clb.execute();
            ResultSet rs=clb.getResultSet();
            while(rs.next())
            {
                String data_ora=rs.getString("ora");
                String s=rs.getString("nume")+" "+rs.getString("prenume");
                s+=":  ";
                s+=rs.getString("mesaj");
                s+="       "  ;
                s+=data_ora;
                mesajele.add(s);
            }
            mess.setItems(mesajele);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    }
