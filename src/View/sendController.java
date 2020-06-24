package View;

/**
 * @author 姚君彦
 * 2020/6/9,15:28
 * 奇怪的程序增加了
 */

import Data.common;
import Service.sender;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class sendController implements Initializable {

    public Button sendEx;
    public Button send;
    public TextArea text1;
    public TextField NICnum;
    public Text hintMessage;

    public void initialize(URL location, ResourceBundle resources) {

        // TODO (don't really need to do anything here).

    }

    public void startSending(ActionEvent actionEvent) {
        System.out.println("Button Clicked!");
        System.out.println("然并卵，我没有做这一部分");
        //读取下面的文本转化为字节流发送，并没有实现，累了
    }

    public void startSendingExample(ActionEvent actionEvent) {
        System.out.println("Button Clicked!");
        trySending();
    }

    private void trySending() {

        String NICText = NICnum.getText();
        try {
            common.NICNumber = Integer.parseInt(NICText);
        } catch (NumberFormatException e) {
            hintMessage.setText("input valid NIC number");
            return;
        }

        sender senderS = new sender();
        Thread threadS = new Thread(senderS);
        threadS.setName("threadS");
        if (common.senderNICNum <= common.MaxNICNumber && common.NICNumber >= 0) {
            //System.out.println("common.NICNumber");
            threadS.start();
        } else {
            common.message = "Failed to send, input index of NIC to catch: 0~" + common.MaxNICNumber;
        }
    }
}
