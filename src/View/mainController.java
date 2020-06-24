package View;

/**
 * @author 姚君彦
 * 2020/6/4,16:40
 * 奇怪的程序增加了
 */

import Data.common;
import Data.record;
import Service.runner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class mainController implements Initializable {
    @FXML
    public TextField NICNumber;
    @FXML
    public CheckBox promiscuous;

    public TableColumn frameNo = new TableColumn();
    public TableColumn arriveTime = new TableColumn();
    public TableColumn srcEth = new TableColumn();
    public TableColumn srcLGIG = new TableColumn();
    public TableColumn destEth = new TableColumn();
    public TableColumn destLGIG = new TableColumn();
    public TableColumn srcIp = new TableColumn();
    public TableColumn destIp = new TableColumn();
    public TableColumn protocol = new TableColumn();
    public TableColumn srcPort = new TableColumn();
    public TableColumn destPort = new TableColumn();
    public TableColumn ack = new TableColumn();
    public TableColumn seq = new TableColumn();
    public TableColumn data = new TableColumn();
    public Button transButton;
    public Button stopButton;
    @FXML
    private TableView<record> table = new TableView<record>();

    @FXML
    private Text message;
    @FXML
    public TextArea NICMessage;
    @FXML
    private Button startButton;

    private boolean inputOJBK;

//    boolean firstTime = true;

//    //类内连接测试
//    private List<record> recordList = new ArrayList<record>();
//    private ObservableList<record> viewList = FXCollections.observableArrayList(
//            new record(1)
//    );


    public void initialize(URL location, ResourceBundle resources) {

        // TODO (don't really need to do anything here).
        runner NICGetter = new runner();
        NICGetter.getNICInfo();
        updateUserSetting();
        updateView();
//        recordList = common.list;
        //要绑定的TableColumn变量名                            要和record中变量一致，如果部分不显示就是字符串没拼对
        frameNo.setCellValueFactory(new PropertyValueFactory<record, String>("frameNo"));
        arriveTime.setCellValueFactory(new PropertyValueFactory<record, String>("arriveTime"));
        srcEth.setCellValueFactory(new PropertyValueFactory<record, String>("srcEth"));
        srcLGIG.setCellValueFactory(new PropertyValueFactory<record, String>("srcLGIG"));
        destEth.setCellValueFactory(new PropertyValueFactory<record, String>("destEth"));
        destLGIG.setCellValueFactory(new PropertyValueFactory<record, String>("destLGIG"));
        srcIp.setCellValueFactory(new PropertyValueFactory<record, String>("srcIp"));
        destIp.setCellValueFactory(new PropertyValueFactory<record, String>("destIp"));
        protocol.setCellValueFactory(new PropertyValueFactory<record, String>("protocol"));
        srcPort.setCellValueFactory(new PropertyValueFactory<record, String>("srcPort"));
        destPort.setCellValueFactory(new PropertyValueFactory<record, String>("destPort"));
        ack.setCellValueFactory(new PropertyValueFactory<record, String>("ack"));
        seq.setCellValueFactory(new PropertyValueFactory<record, String>("seq"));
        data.setCellValueFactory(new PropertyValueFactory<record, String>("data"));


    }

    // When user click on startbutton
    // this method will be called.
    public void startRunning(ActionEvent event) {
        System.out.println("Button Clicked!");
        common.message = "Running! If nothing shows, wait a moment\nuse mouse scroll and bars to view";
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String dateTimeString = df.format(now);
        updateUserSetting();
//        System.out.println(inputOJBK);

        common.viewList.clear();
        if (inputOJBK) {
            //running的进程
            //必须要终结上一个进程，因为每次点击都新开一个进程，每次用lastThreadID记录
            common.tryCloseRunnerThread();
//        if (thread.isAlive())
//            thread.interrupt();
        }

        runner runnerR = new runner();
        Thread threadR = new Thread(runnerR);
        threadR.setName("threadR");
        common.lastRunnerThreadID = threadR.getId();
        //System.out.println(threadR.getId());
        if (common.NICNumber <= common.MaxNICNumber && common.NICNumber >= 0) {
            //System.out.println("common.NICNumber");
            threadR.start();
        } else {
            common.message = "Network Interface Card is scanned, input index of NIC to catch: 0~" + common.MaxNICNumber;
            common.lastRunnerThreadID = -1;
        }
//        Thread threadR = new Thread() {
//            @Override
//            public void run() {
//                while(true){
//                    try {
//
//                    } catch (InterruptedException e) {
//                        break;
//                    }
//
//                    System.out.println(this.getId());
//                }
//            }
//
//        };


        //界面刷新进程
        //必须要终结上一个进程，因为每次点击都新开一个进程，每次用lastThreadID记录
        common.tryCloseFreshThread();
//                    setCellValueFactory(new PropertyValueFactory<record,String>("frameNo"));


        //要做到刷新界面，需要单独开一个线程，暂时我只发现这样原地重写可以做到持续对controller进行调用
        // （在外面的话好像得找到controller再调用里面，不太了解，放一起算了）
        Thread threadF = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        break;
                    }
                    updateView();
                    //使用公共类中的obslist
                    table.setItems(common.viewList);
//                       System.out.println(common.list.toString());
                    updateView();
//                        System.out.println(this.getId());
                }
            }

        };
        threadF.setName("threadF");
        common.lastFreshThreadID = threadF.getId();
        threadF.start();
        //i++;


    }

    private void updateUserSetting() {
        common.ifPromiscuous = promiscuous.isSelected();
        try {
            common.NICNumber = Integer.parseInt(NICNumber.getText());
            inputOJBK = true;
            return;
//            String temp = NICNumber.getText();
//            System.out.println(temp);
//            if (temp.matches("[0-9]+")) {
//                int tempNum = Integer.parseInt(temp);
//                if (tempNum>=0 && tempNum<= common.MaxNICNumber) {
//                    common.NICNumber = tempNum;
//                    return;
//                }
//            }
//            inputOJBK=false;
//            return;
        } catch (Exception e) {
            common.message = "Network Interface Card is scanned,\ninput index of detected NIC to catch: 0 ~ " + common.MaxNICNumber;
            common.lastRunnerThreadID = -1;
            inputOJBK = false;
            return;
        }


    }

    private void updateView() {
        message.setText(common.message);
        NICMessage.setText(common.NICmessage.toString());

    }

//    private void  fillTableView() {
//        Bird b=new Bird();
//        final ObservableList<Bird> data = FXCollections.observableArrayList(b);
//
//    }


    public void stopRunning(ActionEvent actionEvent) {
        common.tryCloseThreads();
        common.message = "Has Stopped";
        updateView();
    }

    public void runSenderWindow(ActionEvent actionEvent) {

        // 为按钮添加事件——点击时打开新的窗口
        transButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                // 创建新的stage,然后看Main依样画葫芦,这个自动就是另一个线程，可以一起动
                Stage secondStage = new Stage();
                Parent root2 = null;
                try {
                    root2 = FXMLLoader.load(getClass().getResource("senderScene.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                secondStage.setTitle("littleWatcher-sender by:姚君彦");
                secondStage.setScene(new Scene(root2, 364, 277));
                secondStage.show();

                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.out.print("监听到窗口关闭");
                        common.tryCloseThreads();
                    }
                });
            }
        });

    }


}
