package se2203.amurji22_assignment1;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

public class SortingHubController implements Initializable {
    private SortingStrategy sortingMethod;
    @FXML
    private Label Display_Slider;

    @FXML
    private Slider SliderItem;

    @FXML
    private Pane pane_for_rectangles;

    @FXML
    private Button reset_button;
    @FXML
    private ComboBox<String> Combo_Box;
    @FXML
    private Button sort_button;

    @FXML
    void reset_do(ActionEvent event) {
        SliderItem.adjustValue(64);
        pane_for_rectangles.getChildren().clear();
        Display_Slider.setText("64");
        random_array();
        Combo_Box.valueProperty().set("Merge Sort");
    }
    int[] intarray = new int[128];
    private boolean isFirstTime = true;
    public int getSize(){
        return (int) SliderItem.getValue();
    }

    public void random_array(){
        //Populate the array from 1 to the slider value
        for(int i=0;i<getSize();i++){
            if (i == 0){
                intarray[i] = 1;
            }else {
                intarray[i] = i + 1;
            }
        }
        //Shuffle the array to randomize the order of numbers
        Random rand = new Random();
        for (int i = 0; i < getSize(); i++) {
            int randomIndexToSwap = rand.nextInt(getSize());
            int temp = intarray[randomIndexToSwap];
            intarray[randomIndexToSwap] = intarray[i];
            intarray[i] = temp;
        }
        updateGraph(intarray);
    }

    public void updateGraph(int[] data){
        //Displaying the rectangles
        List<Rectangle> Rectangles= new ArrayList<>();
        new Thread(()->{
            Platform.runLater(() -> {
                pane_for_rectangles.getChildren().clear();
                for (int i = 0; i < getSize(); i++) {
                    double pane_width = pane_for_rectangles.getPrefWidth();
                    double pane_height = pane_for_rectangles.getPrefHeight();
                    double bar_width = (pane_width / getSize()) - 1;
                    double bar_height = (((double) data[i] / getSize()) * pane_height);
                    double bar_x = (pane_width / (double) getSize()) * i + 1;
                    double bar_y = pane_height - bar_height;

                    Rectangle rectangle = new Rectangle(bar_x, bar_y, bar_width, bar_height);

                    rectangle.setFill(javafx.scene.paint.Color.RED);
                    Rectangles.add(rectangle);
                    pane_for_rectangles.getChildren().add(rectangle);
                }
            });
        }).start();
    }
    @FXML
    void displayDraggedItem(MouseEvent event) {
        pane_for_rectangles.getChildren().clear();
        //Displaying the rounded slider value
        Display_Slider.setText(String.valueOf(getSize()));
        random_array();
    }
    @FXML
    void Sort_Clicked(MouseEvent event) {
            sort_button.setDisable(true); // my computer will die otherwise
            reset_button.setDisable(true);
            setSortStrategy();
            pane_for_rectangles.getChildren().clear();
            updateGraph(intarray);
            sort_button.setDisable(false);
            reset_button.setDisable(false);
    }
    public void setSortStrategy(){
        if(Combo_Box.getValue() == "Merge Sort"){
            sortingMethod = new MergeSort(this);
            new Thread(()->{
                sortingMethod.sort(intarray, 0, getSize()-1);
            }).start();
        }
        if(Combo_Box.getValue() == "Quick Sort"){
            sortingMethod = new QuickSort(this);
            new Thread(()->{
                sortingMethod.sort(intarray,0,getSize()-1);
            }).start();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Combo_Box.getItems().setAll("Merge Sort", "Quick Sort");
        Combo_Box.setValue("Merge Sort");
        random_array();
    }



}