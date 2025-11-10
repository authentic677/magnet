package cc.aguesuka.run;

import cc.aguesuka.dht.connection.DhtRequest;
import cc.aguesuka.util.HexUtil;
import cc.aguesuka.util.bencode.Bencode;
import cc.aguesuka.util.bencode.BencodeMap;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 用于测试某node是否可达
 *
 * @author :yangmingyuxing
 * 2019/10/15 22:15
 */
public class FindNodeTest {
    public static void main(String[] args) throws IOException {
        //随机生成一个nodeId，设置好bootstrap节点地址，然后不断getPeer迭代查询
        //设置地址和端口
        String address = "router.bittorrent.com";
//        address="dht.transmissionbt.com";
//        address="87.98.162.88";
        address="18.198.236.147";
        int port = 6881;
        port=6992;
        if (args.length == 1) {
            String[] split = args[0].split(":");
            address = split[0];
            port = Short.parseShort(split[1]);
        }
        // find node 请求
        Random random = new SecureRandom();
        byte[] testId = new byte[20];
        random.nextBytes(testId);
        byte[] msg = DhtRequest.findNode(testId, testId).toBencodeBytes();

        msg=DhtRequest.getPeer(HexUtil.decode("25A10C0D410CB98352A8256219256FB31989B2EC"),
                HexUtil.decode("E84213A794F3CCD890382A54A64CA68B7E925433")).toBencodeBytes();

        // 发送请求
        DatagramPacket request = new DatagramPacket(msg, msg.length);
        request.setSocketAddress(new InetSocketAddress(address, port));
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(10000);
        socket.send(request);
        // 接受回复
        byte[] recvBuff = new byte[2 << 12];
        DatagramPacket responsePacket = new DatagramPacket(recvBuff, recvBuff.length);
        socket.receive(responsePacket);
        if (responsePacket.getLength() > 0) {
            // 解析回复
            BencodeMap responseMessage = Bencode.parse(ByteBuffer.wrap(responsePacket.getData()));
            System.out.println("responseMessage = " + responseMessage);

            byte[] byteArray = responseMessage.getBencodeMap("r").getByteArray("nodes");
            ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);

            byte[] nodeId = new byte[20];
            byteBuffer.get(nodeId);
            byte[] addr=new byte[4];
            byteBuffer.get(addr);
            InetAddress byAddress = Inet4Address.getByAddress(addr);
            System.out.println(byAddress);
            int p=byteBuffer.getShort();
            System.out.println(p);

        } else {
            System.out.println("连接超时");
        }
    }
}
