package Data;

/**
 * @author 姚君彦
 * 2020/6/8,4:43
 * 奇怪的程序增加了
 */

import Service.parser;
import javafx.beans.property.SimpleStringProperty;


//从静态parser取出数据
public class record {
    private final SimpleStringProperty frameNo;
    private final SimpleStringProperty arriveTime;

    private final SimpleStringProperty srcEth;
    private final SimpleStringProperty srcLGIG;


    private final SimpleStringProperty destEth;
    private final SimpleStringProperty destLGIG;


    private final SimpleStringProperty srcIp;
    private final SimpleStringProperty destIp;

    private final SimpleStringProperty protocol;

    private final SimpleStringProperty srcPort;
    private final SimpleStringProperty destPort;
    private final SimpleStringProperty ack;
    private final SimpleStringProperty seq;

    private String data;

    public record() {

        this.frameNo = new SimpleStringProperty("" + parser.getFrameNo());
        this.arriveTime = new SimpleStringProperty(parser.getArriveTime());
        this.srcEth = new SimpleStringProperty(parser.getSrcEth());
        this.srcLGIG = new SimpleStringProperty(srcIGLG());
        this.destEth = new SimpleStringProperty(parser.getDestEth());
        this.destLGIG = new SimpleStringProperty(destIGLG());
        this.srcIp = new SimpleStringProperty(parser.getSrcIp());
        this.destIp = new SimpleStringProperty(parser.getDestIp());
        this.protocol = new SimpleStringProperty(parser.getProtocol());
        this.srcPort = new SimpleStringProperty(parser.getSrcPort());
        this.destPort = new SimpleStringProperty(parser.getDestPort());
        this.ack = new SimpleStringProperty(parser.getAck());
        this.seq = new SimpleStringProperty(parser.getSeq());
        this.data = parser.getData();
    }

    public record(int i) {
        frameNo = new SimpleStringProperty("1231321");
        arriveTime = new SimpleStringProperty("new Date");
        srcEth = new SimpleStringProperty("parser.getSrcEth()");
        srcLGIG = new SimpleStringProperty("srcIGLG()");
        destEth = new SimpleStringProperty("parser.getDestEth()");
        destLGIG = new SimpleStringProperty("destIGLG()");
        srcIp = new SimpleStringProperty("parser.getSrcIp()");
        destIp = new SimpleStringProperty("parser.getDestIp()");
        protocol = new SimpleStringProperty("parser.getProtocol()");
        srcPort = new SimpleStringProperty("parser.getSrcPort()");
        destPort = new SimpleStringProperty("parser.getDestPort()");
        ack = new SimpleStringProperty("parser.getAck()");
        seq = new SimpleStringProperty("parser.getSeq()");
        data = "parser.getData()";
    }


    //LG:0为出厂MAC，1为分配的MAC
    //IG:0为单播，1为广播
    private String srcIGLG() {
        String temp = "";
        if (Integer.valueOf(parser.getSrcLG()) == 0) {
            temp += "出厂MAC";
        } else {
            temp += "分配的MAC";
        }
        if (Integer.valueOf(parser.getSrcIG()) == 0) {
            temp += "单播";
        } else {
            temp += "广播";
        }
        return temp;
    }

    private String destIGLG() {
        String temp = "";
        if (Integer.valueOf(parser.getSrcLG()) == 0) {
            temp += "出厂MAC";
        } else {
            temp += "分配的MAC";
        }
        if (Integer.valueOf(parser.getSrcIG()) == 0) {
            temp += "单播";
        } else {
            temp += "广播";
        }
        return temp;
    }

    //别的地方应该也用getset的，但是这是我最后生成的emmmm
    public String getFrameNo() {
        return frameNo.get();
    }

    public SimpleStringProperty frameNoProperty() {
        return frameNo;
    }

    public void setFrameNo(String frameNo) {
        this.frameNo.set(frameNo);
    }

    public String getArriveTime() {
        return arriveTime.get();
    }

    public SimpleStringProperty arriveTimeProperty() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime.set(arriveTime);
    }

    public String getSrcEth() {
        return srcEth.get();
    }

    public SimpleStringProperty srcEthProperty() {
        return srcEth;
    }

    public void setSrcEth(String srcEth) {
        this.srcEth.set(srcEth);
    }

    public String getSrcLGIG() {
        return srcLGIG.get();
    }

    public SimpleStringProperty srcLGIGProperty() {
        return srcLGIG;
    }

    public void setSrcLGIG(String srcLGIG) {
        this.srcLGIG.set(srcLGIG);
    }

    public String getDestEth() {
        return destEth.get();
    }

    public SimpleStringProperty destEthProperty() {
        return destEth;
    }

    public void setDestEth(String destEth) {
        this.destEth.set(destEth);
    }

    public String getDestLGIG() {
        return destLGIG.get();
    }

    public SimpleStringProperty destLGIGProperty() {
        return destLGIG;
    }

    public void setDestLGIG(String destLGIG) {
        this.destLGIG.set(destLGIG);
    }

    public String getSrcIp() {
        return srcIp.get();
    }

    public SimpleStringProperty srcIpProperty() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp.set(srcIp);
    }

    public String getDestIp() {
        return destIp.get();
    }

    public SimpleStringProperty destIpProperty() {
        return destIp;
    }

    public void setDestIp(String destIp) {
        this.destIp.set(destIp);
    }

    public String getProtocol() {
        return protocol.get();
    }

    public SimpleStringProperty protocolProperty() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol.set(protocol);
    }

    public String getSrcPort() {
        return srcPort.get();
    }

    public SimpleStringProperty srcPortProperty() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort.set(srcPort);
    }

    public String getDestPort() {
        return destPort.get();
    }

    public SimpleStringProperty destPortProperty() {
        return destPort;
    }

    public void setDestPort(String destPort) {
        this.destPort.set(destPort);
    }

    public String getAck() {
        return ack.get();
    }

    public SimpleStringProperty ackProperty() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack.set(ack);
    }

    public String getSeq() {
        return seq.get();
    }

    public SimpleStringProperty seqProperty() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq.set(seq);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
