/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Comparator;
import Model.Corrector;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author user
 */
public class FXMLDocumentController implements Initializable {

    private Comparator comp;

    @FXML
    private TextField reqBit;

    @FXML
    private TextField recBit;

    @FXML
    private TextField errorField;

    @FXML
    private TextField distance;

    @FXML
    private TextArea syndromeText;

    @FXML
    private TextField correction;

    @FXML
    private Button correctButton;

    @FXML
    private void checkClicked() throws Exception {
        try {
            correction.setText("");
            String reqString = reqBit.getText();
            String recString = recBit.getText();
            int[] reqArr = new int[8];
            int[] recArr = new int[12];
            for (int i = 0; i < reqArr.length; i++) {
                reqArr[i] = Integer.parseInt(reqString.substring(i, i + 1));
            }
            for (int i = 0; i < recArr.length; i++) {
                recArr[i] = Integer.parseInt(recString.substring(i, i + 1));
            }
            this.comp = new Comparator(reqArr, recArr);
            comp.compareTo();
            errorField.setText(comp.getErrorMessages());
            distance.setText(String.valueOf(comp.getDistance()));
            comp.syndromeError();
//        ArrayList<Integer> syndrome = comp.getErrorIndex();
//        String s = "";
//        for(int i = 0; i < syndrome.size(); i++){
//            s += String.format("%4s", Integer.toBinaryString(syndrome.get(i)));
//            if(i != syndrome.size() - 1){
//                s += ", ";
//            }
//        }
            syndromeText.setText(comp.getErrorIndex().toString());
            correctButton.setDisable(false);
        } catch (Exception e) {
            errorField.setText("Error!");
        }
    }

    @FXML
    private void correctClicked() {
        Corrector cor = new Corrector();
        cor.correct(comp);
        int[] correctedBitString = comp.getDataBitRec();
        String s = "";
        for (int i = correctedBitString.length - 1; i >= 0; i--) {
            s += correctedBitString[i];
        }
        correction.setText(s);
        correctButton.setDisable(true);
    }

    @FXML
    private void closeClicked() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addTextLimiter(reqBit, 8);
        addTextLimiter(recBit, 12);
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
}
