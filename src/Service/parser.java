package Service;

/**
 * @author 姚君彦
 * 2020/6/5,14:40
 * 奇怪的程序增加了
 */

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.JProtocol;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import java.util.Date;

//这里应该只提供切割服务的，记录应该另开在专门的数据类，但是没有关系，我再把这些数据复制到数据类
public class parser {
    //只需要一个分割器存储器
    //解析到什么协议先把它搞个静态实例,每次随着循环更新,是全局的
    private static Ip4 ip4 = new Ip4();
    private static Ip6 ip6 = new Ip6();
    private static Ethernet eth = new Ethernet();
    private static Tcp tcp = new Tcp();
    private static Udp udp = new Udp();
    //没有研究pcappacket构造的参数，不然可以建立pcappacket然后下面的函数都不需要传递packet了


    //以下是需要操作的数据结构，只取了重要的部分，
    private static int frameNo;
    private static String arriveTime;

    private static String srcEth;
    private static String srcLG;//0为出厂MAC，1为分配的MAC
    private static String srcIG;//0为单播，1为广播

    private static String destEth;
    private static String destLG;//0为出厂MAC，1为分配的MAC
    private static String destIG;//0为单播，1为广播

    private static String srcIp;
    private static String destIp;
    //private static String ttl;

    private static String protocol;

    private static String srcPort;
    private static String destPort;
    private static String ack;
    private static String seq;

    private static boolean ifUseHttp;

    private static String data;

    //这里处理部分可以单独做一层
    public static void update(PcapPacket packet) {
        frameNo++;
        arriveTime = new Date(packet.getCaptureHeader().timestampInMillis()).toString();
        srcEth = parseSrcMAC(packet);
        srcLG = Long.toString(eth.source_LG());
        srcIG = Long.toString(eth.source_IG());
        destEth = parseDestMac(packet);
        destLG = Long.toString(eth.destination_LG());
        destIG = Long.toString(eth.destination_IG());
        srcIp = parseSrcIp(packet);
        destIp = parseDestIp(packet);
        protocol = parseProtocol(packet);
        srcPort = parseSrcPort(packet);
        destPort = parseDestPort(packet);
        if (packet.hasHeader(tcp)) {
            ack = Long.toString(tcp.ack());
            seq = Long.toString(tcp.seq());
        } else {
            ack = seq = null;
        }
        ifUseHttp = packet.hasHeader(Http.ID);
        data = parseData(packet);
    }

    //解析出源Mac地址
    private static String parseSrcMAC(PcapPacket packet) {
        if (packet.hasHeader(eth)) { // 如果packet有eth头部
            return FormatUtils.mac(eth.source());
        }
        return null;
    }

    //解析出目的Mac地址
    private static String parseDestMac(PcapPacket packet) {
        if (packet.hasHeader(eth)) { // 如果packet有eth头部
            return FormatUtils.mac(eth.destination());
        }
        return null;
    }

    //ip有ip4，ip6
    //解析出源ip
    private static String parseSrcIp(PcapPacket packet) {
        if (packet.hasHeader(ip4)) { // 如果packet有ip头部
            return FormatUtils.ip(ip4.source());
        } else if (packet.hasHeader(ip6)) {
            return FormatUtils.ip(ip6.source());
        } else return null;
    }

    //解析出目的ip
    private static String parseDestIp(PcapPacket packet) {
        if (packet.hasHeader(ip4)) { // 如果packet有ip头部
            return FormatUtils.ip(ip4.destination());
        } else if (packet.hasHeader(ip6)) {
            return FormatUtils.ip(ip6.destination());
        } else return null;
    }

    //解析出协议类型
    public static String parseProtocol(PcapPacket packet) {

        //逆向遍历协议表找到最精确（最高层）的协议名
        JProtocol[] protocols = JProtocol.values();
        for (int i = protocols.length - 1; i >= 0; i--) {
            if (packet.hasHeader(protocols[i].getId())) {
                return protocols[i].name();
            }
        }
        return null;
    }

    //解析出源port
    private static String parseSrcPort(PcapPacket packet) {
        if (packet.hasHeader(eth)) {
            return FormatUtils.mac(eth.source());
        }
        return null;
    }

    //解析出目的port
    private static String parseDestPort(PcapPacket packet) {
        if (packet.hasHeader(eth)) {
            return FormatUtils.mac(eth.destination());
        }
        return null;
    }

    //解析出data
    private static String parseData(PcapPacket packet) {
//        if (packet.hasHeader(tcp)) { // 如果packet有ip头部
//            return new String(tcp.getPayload());
//        }else if(packet.hasHeader(udp)) {
//            //System.out.println(FormatUtils.ip(udp.getPayload()).toString());
//            return FormatUtils.ip(udp.getPayload());
//        }else return null;
        byte[] temp1 = new byte[10000];
        packet.transferStateAndDataTo(temp1);
        return new String(temp1.toString());
    }

    public static Ip4 getIp4() {
        return ip4;
    }

    public static Ip6 getIp6() {
        return ip6;
    }

    public static Ethernet getEth() {
        return eth;
    }

    public static Tcp getTcp() {
        return tcp;
    }

    public static int getFrameNo() {
        return frameNo;
    }

    public static String getArriveTime() {
        return arriveTime;
    }

    public static String getSrcEth() {
        return srcEth;
    }

    public static String getSrcLG() {
        return srcLG;
    }

    public static String getSrcIG() {
        return srcIG;
    }

    public static String getDestEth() {
        return destEth;
    }

    public static String getDestLG() {
        return destLG;
    }

    public static String getDestIG() {
        return destIG;
    }

    public static String getSrcIp() {
        return srcIp;
    }

    public static String getDestIp() {
        return destIp;
    }

    public static String getProtocol() {
        return protocol;
    }

    public static String getSrcPort() {
        return srcPort;
    }

    public static String getDestPort() {
        return destPort;
    }

    public static String getAck() {
        return ack;
    }

    public static String getSeq() {
        return seq;
    }

    public static boolean getIfUseHttp() {
        return ifUseHttp;
    }

    public static String getData() {
        return data;
    }
}
