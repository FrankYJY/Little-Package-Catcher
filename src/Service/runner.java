package Service;

/**
 * @author 姚君彦
 * 2020/6/7,15:12
 * 奇怪的程序增加了
 */

import Data.common;
import Data.record;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;


public class runner implements Runnable {
    private static StringBuilder errbuf = new StringBuilder();
    private static List<PcapIf> alldevs = new ArrayList<PcapIf>();

    @Override
    public void run() {
        //getNICInfo();
        PcapIf device = alldevs.get(common.NICNumber);
//        PcapIf device = alldevs.get(3);
        //在这里选择要分析的网卡

        int snaplen = 64 * 1024;//最大长度ip最长65535(在openlive使用)
        int flags;
        if (common.ifPromiscuous)
            flags = Pcap.MODE_PROMISCUOUS;//混杂模式
        else
            flags = Pcap.MODE_BLOCKING;
        int timeout = 10 * 1000;//等待延迟到来的包的超时机制

        //            开一个捕获器,针对此网卡
        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
        if (pcap == null) {
            common.message = "Error while opening device for capture:" + errbuf.toString();
            return;
        }
        String ss = new Date().toString();

        PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {
            @Override
            public void nextPacket(PcapPacket packet, String user) {
                // TODO Auto-generated method stub
//                System.out.println("ddd");
                parser.update(packet);
                record recordi = new record();
                common.viewList.add(recordi);
                common.message = "Running! If nothing shows, wait a moment or try other NICs\nuse mouse scroll and bars to view";
//                System.out.printf("Received at %s caplen=%-4d len=%-4d %s\n",
//                        new Date(packet.getCaptureHeader().timestampInMillis()).toString(),
//                        packet.getCaptureHeader().caplen(), // 抓取到的长度
//                        packet.getCaptureHeader().wirelen(), // 数据原始长度
//                        user // User supplied object
//                );
            }
        };
        try {
            while (true) {
                try {
                    sleep(1);//不能持续运行，线程一定要sleep，不然异步的读取与写入会进入死循环，或者可以写一个锁
                } catch (InterruptedException e) {
                    break;
                }
                pcap.loop(1, jpacketHandler, ss);//开始循环，调用内类JpacketHandler
            }
        } catch (Exception e) {
            common.message = "Stopped";
        }

        pcap.close();
        //最后一定要关闭pcap
    }

    public void getNICInfo() {
        //获得网卡信息
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            common.message = "Can't read list of devices, error is " + errbuf.toString();
            return;
        }
        common.message = "Network devices found:";

        int i = 0;

        common.NICmessage.setLength(0);
        for (PcapIf device : alldevs) {
            common.NICmessage.append("#" + i + ": " + device.getName() + " [" + device.getDescription() + "]\n");
            //System.out.printf("#%d: %s [%s]\n", i++, device.getName(), device.getDescription());
            i++;
        }
        common.MaxNICNumber = i - 1;
    }
}