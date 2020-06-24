package Data;

/**
 * @author 姚君彦
 * 2020/6/8,6:23
 * 奇怪的程序增加了
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class common {
    public static String message;

    public static StringBuffer NICmessage = new StringBuffer();
    public static int NICNumber;
    public static int MaxNICNumber;
    public static boolean ifPromiscuous;
//    public static ArrayList<record> list = new ArrayList<record>(); //javaFX 的数据集合

    //以下两个线程是无限循环的，所以必须记录其ID，可以用interrupt停止线程
    public static long lastFreshThreadID = -1;
    public static long lastRunnerThreadID = -1;

//    public static List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
//    public static StringBuilder errbuf = new StringBuilder(); // For any error msgs

    //必须由ObservableList类型存储和传递record的实例给tableview的setCellValueFactory，别的都会不显示
    public static ObservableList<record> viewList = FXCollections.observableArrayList();

    public static int senderNICNum;

    //通过线程组获得线程
    //暂时没发现能越过线程池直接获得的方法，但是网上又说这个方法过时了
    public static Thread findThread(long threadId) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while (group != null) {
            Thread[] threads = new Thread[(int) (group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for (int i = 0; i < count; i++) {
                if (threadId == threads[i].getId()) {
                    return threads[i];
                }
            }
            group = group.getParent();
        }
        return null;
    }

    public static void tryCloseThreads() {
        tryCloseRunnerThread();
        tryCloseFreshThread();
    }

    public static void tryCloseRunnerThread() {
        try {
            Thread t = findThread(lastRunnerThreadID);
            assert t != null;
            t.interrupt();
            lastRunnerThreadID = -1;
        } catch (Exception e) {
        }
//        if(lastRunnerThreadID !=-1){
//            Thread t = findThread(lastRunnerThreadID);
//            assert t != null;
//            t.interrupt();
//            lastRunnerThreadID =- 1;
//        }
    }

    public static void tryCloseFreshThread() {
        try {
            Thread t = findThread(lastFreshThreadID);
            assert t != null;
            t.interrupt();
            lastFreshThreadID = -1;
        } catch (Exception e) {
        }

    }
}
