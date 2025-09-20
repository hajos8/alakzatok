package org.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AlakzatController implements Initializable {
    @FXML public RadioButton red, blue, green;
    @FXML public RadioButton circle, square, triangle;
    @FXML public ListView<String> list;
    @FXML public ImageView imageView;
    @FXML public VBox imgBackgrond;
    @FXML public Button addButton, deleteButton, saveButton;

    public static ArrayList<Alakzat> alakzatokList = new ArrayList<>();

    public static boolean isTestIsRunning = false;

    public static String TestColor, TestShape;
    public static int TestIndex;


    @FXML
    protected void fillUp(){
        ObservableList <String> lines = FXCollections.observableArrayList();
        for (Alakzat a : alakzatokList) {
            lines.add(a.color + ", " + a.shape);
        }
        if(!isTestIsRunning) list.setItems(lines);

    }

    @FXML
    protected void add(){
        String color, shape;
        if(!isTestIsRunning){
            color = red.isSelected() ? "Piros" : blue.isSelected() ? "Kék" : green.isSelected() ? "Zöld" : "";
            shape = circle.isSelected() ? "Kör" : square.isSelected() ? "Négyzet" : triangle.isSelected() ? "Háromszög" : "";

            alakzatokList.add(new Alakzat(color, shape));
        }
        else{
            alakzatokList.add(TestIndex, new Alakzat(TestColor, TestShape));
        }

        fillUp();
    }

    @FXML
    protected void delete(){
        if(isTestIsRunning) alakzatokList.remove(TestIndex);
        else alakzatokList.remove(list.getSelectionModel().getSelectedIndex());

        fillUp();
    }

    @FXML
    protected void changeBg(){
        if(red.isSelected()) imgBackgrond.setStyle("-fx-background-color: red;-fx-border-color: black; -fx-border-width: 3px;");
        else if(blue.isSelected()) imgBackgrond.setStyle("-fx-background-color: blue;-fx-border-color: black; -fx-border-width: 3px;");
        else if(green.isSelected()) imgBackgrond.setStyle("-fx-background-color: green;-fx-border-color: black; -fx-border-width: 3px;");
    }

    @FXML
    protected void changeImg(){
        if(circle.isSelected()) createImg("/icons/kor.png");
        else if(square.isSelected()) createImg("/icons/negyzet.png");
        else if(triangle.isSelected()) createImg("/icons/haromszog.png");
    }

    @FXML
    protected void createImg(String path){
        if(path == null || path.isEmpty()) return;

        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
    }

    @FXML
    protected void save(){
        try{
            FileWriter writer = new FileWriter("alakzat.dat", StandardCharsets.UTF_8, false);
            for(Alakzat a : alakzatokList){
                writer.write(a.color + ", " + a.shape + "\n");
            }
            writer.close();
        }
        catch (Exception e){
            System.out.println("Hiba a fájl írásakor: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Scanner scanner = new Scanner(new File("alakzat.dat"), StandardCharsets.UTF_8);

            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                alakzatokList.add(new Alakzat(parts[0], parts[1]));

            }

            fillUp();

            scanner.close();

        }
        catch (Exception e){
            System.out.println("Hiba a fájl megnyitásakor: " + e.getMessage());
        }

        if(!isTestIsRunning){
            addButton.setGraphic(new ImageView(
                    new Image(getClass().getResourceAsStream("/icons/add16.png"))));

            deleteButton.setGraphic(new ImageView(
                    new Image(getClass().getResourceAsStream("/icons/del16.png"))));

            saveButton.setGraphic(new ImageView(
                    new Image(getClass().getResourceAsStream("/icons/save16.png"))));
        }

    }
}