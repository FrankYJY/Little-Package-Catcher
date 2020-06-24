package Service;

import Data.common;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.winpcap.WinPcap;
import org.jnetpcap.winpcap.WinPcapSendQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 姚君彦
 * 2020/6/8,11:44
 * 奇怪的程序增加了
 */
public class sender implements Runnable {
    private static List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
    private static StringBuilder errbuf = new StringBuilder(); // For any error msgs

    @Override
    public void run() {


        //先照抄runner类
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
            return;
        }

        //选择一个网卡
        PcapIf device = alldevs.get(common.NICNumber); // We know we have atleast 1 device

        int snaplen = 64 * 1024; // Capture all packets, no trucation
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
        int timeout = 10 * 1000; // 10 seconds in millis
        WinPcap pcap = WinPcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

        //创建一个数据包队列

        WinPcapSendQueue queue = WinPcap.sendQueueAlloc(512);//分配的是字节数,这个队列长n字节

        //默认的头，标注了后面数据段的大小，是帧头
        int n = 400;
        PcapHeader hdr = new PcapHeader(n, n);//(caplen,wirelen)

        //头长是16byte
        System.out.println(hdr.size());
        //PcapPktHdr已过时
        byte[] pkt = new byte[n];
        //byte型直接打印时为n-128  (-128~127),不影响数据本身,直接赋就完事了
        Arrays.fill(pkt, (byte) 255); // 这里不只是数据段，就是frame以上的层
//        int j = 0;
//        while (j<128){
//            System.out.println(pkt[j]);
//            j++;
//        }
        pkt[0] = (byte) 0x1a;
        pkt[1] = (byte) 0x2b;
        pkt[2] = (byte) 0x3c;
        pkt[3] = (byte) 0x4d;
        pkt[4] = (byte) 0x5e;
        pkt[5] = (byte) 0x6f;
        //...
        pkt[6] = (byte) 0x7a;
        pkt[7] = (byte) 0x8b;
        pkt[8] = (byte) 0x9c;
        pkt[9] = (byte) 0xad;
        pkt[10] = (byte) 0xbe;
        pkt[11] = (byte) 0xcf;

//            pkt[12] = (byte) 0x08;//ipv4
//            pkt[12] = (byte) 0x00;//ipv4
//
//            pkt[14] = (byte) 0x45;//ipv4
//            pkt[15] = (byte) 0x00;
//            pkt[16] = (byte) 0x01;
//            pkt[17] = (byte) 0x23;
//            pkt[18] = (byte) 0x42;
//            pkt[19] = (byte) 0x45;
//            pkt[20] = (byte) 0x00;
//            pkt[21] = (byte) 0x00;
//            pkt[22] = (byte) 0x80;
//            pkt[23] = (byte) 0x11;
//            pkt[24] = (byte) 0x63;
//            pkt[25] = (byte) 0x33;
//            pkt[26] = (byte) 0xc0;
//            pkt[27] = (byte) 0xa8;
//            pkt[28] = (byte) 0x89;
//            pkt[29] = (byte) 0x01;
//
//
//
//            //ip4 destination
//            pkt[30] = (byte) 0xc0;
//            pkt[31] = (byte) 0xa8;
//            pkt[32] = (byte) 0x89;
//            pkt[33] = (byte) 0xff;

        int i;
        queue.queue(hdr, pkt); // Packet #1

// Packet #2

//        Arrays.fill(pkt, (byte) 0x11); // Junk packet
//        queue.queue(hdr, pkt); // Packet #3


        //发送我们的数据包队列

        r = pcap.sendQueueTransmit(queue, WinPcap.TRANSMIT_SYNCH_ASAP);
        if (r != queue.getLen()) {
            System.err.println(pcap.getErr());
            return;
        }
        for (int k = 0; k < 10000; k++) {
            pcap.sendQueueTransmit(queue, WinPcap.TRANSMIT_SYNCH_ASAP);
        }


        pcap.close();
    }

}
