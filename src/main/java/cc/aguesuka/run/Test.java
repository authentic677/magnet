package cc.aguesuka.run;

import cc.aguesuka.util.HexUtil;
import cc.aguesuka.util.bencode.Bencode;
import cc.aguesuka.util.bencode.BencodeMap;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class Test {

    public static void main(String[] args) throws Exception {


        byte[] t1 = Files.readAllBytes(Paths.get("C:\\Users\\677\\Documents\\ubuntu-18.04.1-desktop-amd64.iso.torrent"));


        byte[] t2 = Files.readAllBytes(Paths.get("test.torrent"));

        BencodeMap parse1 = Bencode.parse(ByteBuffer.wrap(t1));
        BencodeMap parse2 = Bencode.parse(ByteBuffer.wrap(t2));

//        System.out.println(parse1);
//        System.out.println(parse2);

        MessageDigest md = MessageDigest.getInstance("sha1");
        md.update(parse1.getBencodeMap("info").toBencodeBytes());
        System.out.println(HexUtil.encode(md.digest()));

        md = MessageDigest.getInstance("sha1");
        md.update(parse2.toBencodeBytes());
        System.out.println(HexUtil.encode(md.digest()));

    }
}
