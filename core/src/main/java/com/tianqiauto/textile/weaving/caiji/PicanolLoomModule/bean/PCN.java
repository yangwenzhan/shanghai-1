package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.BytesUtil;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.StringUtils;
import org.bouncycastle.util.encoders.Base64;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * bjw
 * @Date 2019/1/19 10:08
 */
public class PCN {


    public PCN() {
        header = new Header();
        body = new Body();
    }

    public PCN(String msg) {
       this(msg.getBytes());
    }

    /**
     * 把请求回来的报文流转换成  herder+body 对象。
     **/
    public PCN(byte[] data) {
        this();
        this.header.init(data);
        byte[] bodyBytes = Arrays.copyOfRange(data, 127, data.length);
        this.body.init(bodyBytes);
    }

    public String toString() {
        return header.toString() + body.toString();
    }


    private Header header;

    private Body body;

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }

    //----------------------------------------------------------------------------------------------------------------------
    public class Header {

        Header() {
            version = "PCN1.0";
            timeStamp = StringUtils.NewDateToString("yyyyMMddHHmmssSSS");
        }

        private String version;          //10个字节，解释为文本。
        private String timeStamp;        //17个字节，解释为日期 Format:yyyyMMddHHmmssSSS
        private String sourceId;         //来源识别 //10个字节，解释为文本。
        private String destinationId;   //目标识别 10个字节，解释为文本。1.织机可能会将此字段留空。2.网络设备应指定以'sn'开头的机器序列号。
        private String messageType;     //消息类型,30个字节，解释为文本。如果使用bicom消息为生产监控服务器进行双向通信：'Monitoring'。如果使用设计和设置传输文件：'Machine'
        private String messageCode;     //消息代码 30个字节，解释为文本。 如果使用bicom消息为生产监控服务器进行双向通信：'bicom'。
        private String dataFormat;      //数据格式 10个字节，解释为字符串。如果使用bicom消息为生产监控服务器进行双向通信：'VDI'。 如果使用设计和设置传输文件传输：'XML'
//        private int dataLength;         //数据长度 10个字节，解释为整数的字符串表示。表示数据中字节数的整数。 只考虑数据本身[数据体的字节大小]，信封内没有其他字段，没有标	记......

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getDestinationId() {
            return destinationId;
        }

        public void setDestinationId(String destinationId) {
            this.destinationId = destinationId;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getMessageCode() {
            return messageCode;
        }

        public void setMessageCode(String messageCode) {
            this.messageCode = messageCode;
        }

        public String getDataFormat() {
            return dataFormat;
        }

        public void setDataFormat(String dataFormat) {
            this.dataFormat = dataFormat;
        }

        /**
         * 获取报文头中的信息并加工
         * bjw
         * @Date 2019/1/19 11:25
         **/
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtils.addEmpty(-10, header.getVersion()));       //10个字节，解释为文本。
            sb.append(StringUtils.addEmpty(-17, header.getTimeStamp()));     //17个字节，解释为日期 Format:yyyyMMddHHmmssSSS
            sb.append(StringUtils.addEmpty(-10, header.getSourceId()));      //10个字节，解释为文本。
            sb.append(StringUtils.addEmpty(-10, header.getDestinationId())); //目标识别 10个字节，解释为文本。
            sb.append(StringUtils.addEmpty(-30, header.getMessageType()));   //消息类型,30个字节
            sb.append(StringUtils.addEmpty(-30, header.getMessageCode()));   //消息代码 30个字节，解释为文本。
            sb.append(StringUtils.addEmpty(-10, header.getDataFormat()));    //数据格式 10个字节，解释为字符串。
            sb.append(StringUtils.add0Before(10, body.toString().length())); //数据长度 10个字节，解释为整数的字符串表示
            return sb.toString();
        }

        public void init(byte[] bytes){
            byte[] versionByte = Arrays.copyOfRange(bytes,0,10);
            this.version = new String(versionByte).trim();
            byte[] timeStaByte = Arrays.copyOfRange(bytes,10,27);
            this.timeStamp = new String(timeStaByte).trim();
            byte[] source_Byte = Arrays.copyOfRange(bytes,27,37);
            this.sourceId = new String(source_Byte).trim();
            byte[] destinaByte = Arrays.copyOfRange(bytes,37,47);
            this.destinationId = new String(destinaByte).trim();
            byte[] massTypByte = Arrays.copyOfRange(bytes,47,77);
            this.messageType = new String(massTypByte).trim();
            byte[] massCodByte = Arrays.copyOfRange(bytes,77,107);
            this.messageCode = new String(massCodByte).trim();
            byte[] dataForByte = Arrays.copyOfRange(bytes,107,117);
            this.dataFormat = new String(dataForByte).trim();
//            byte[] dataLenByte = Arrays.copyOfRange(bytes,117,127);
//            this.dataLength = new Integer(new String(dataLenByte));
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    public class Body {

        private byte id;         //消息标识符

        private byte cnt;        //消息包含字节数

        private byte[] data = {};    //消息内容

        public byte getId() {
            return id;
        }

        public short getIdToShort() {
            return BytesUtil.bytesToShort(id);
        }

        public void setId(byte id) {
            this.id = id;
        }

        public byte getCnt() {
            return cnt;
        }

        public Short getCntToShort() {
            return BytesUtil.bytesToShort(cnt);
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public String toString(){
            this.cnt = (byte)data.length;
            if(this.id == 0 && this.cnt == 0){
                return "";
            }
            List<Byte> list = new ArrayList<Byte>();
            list.add(id);
            list.add(cnt);
            short cntShort = getCntToShort();
            byte [] bytes = new byte[cntShort+2];
            bytes[0] = id;
            bytes[1] = cnt;
            System.arraycopy(data, 0, bytes, 2, cntShort);
            return new String(Base64.encode(bytes));
        }



        private void init(byte[] bytes){
            if(bytes.length > 1){
                byte[] byteJM =  Base64.decode(bytes);
                this.id = byteJM[0];
                this.cnt = byteJM[1];
                this.data = Arrays.copyOfRange(byteJM,2,byteJM.length);
            }
        }
    }


}


