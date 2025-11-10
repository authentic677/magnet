package cc.aguesuka.run;

import cc.aguesuka.downloader.IDownloadInfoHash;
import cc.aguesuka.downloader.IMetaDataDownloader;
import cc.aguesuka.downloader.impl.DoMetaDataDownLoader;
import cc.aguesuka.util.HexUtil;
import cc.aguesuka.util.inject.Injector;
import cc.aguesuka.util.log.LogSetting;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author :yangmingyuxing
 * 2019/8/2 12:46
 */
public class Main {

    public static void main(String[] args) throws Exception {

        DoMetaDataDownLoader doMetaDataDownLoader = new DoMetaDataDownLoader();

        doMetaDataDownLoader.id=HexUtil.decode("25A10C0D410CB98352A8256219256FB31989B2EC");
        doMetaDataDownLoader.timeout=10000;
        doMetaDataDownLoader.connectionTimeout=10000;

        byte[] bytes = doMetaDataDownLoader.downloadMataData(
                HexUtil.decode("E84213A794F3CCD890382A54A64CA68B7E925433"),
                new InetSocketAddress("107.175.93.162", 51413)
        );
        Files.write(Paths.get("test2.torrent"),bytes);
        System.out.println("写入完成");

        if (true){
            return;
        }

        String infoHash;
        if (args.length == 0) {
            infoHash = "5A1A7D6EEB329F9833FFD8CF6003547478BF4795";
            infoHash = "5FA11DECEE52F8C0D60AB46945501019C0969141";
            ;
        } else {
            infoHash = args[0];
        }
        try (Injector injector = BeansFactory.createInjector()) {
//            IDownloadInfoHash downloadInfoHash = injector.instanceByClass(IDownloadInfoHash.class);
//            downloadInfoHash.download(HexUtil.decode(infoHash));

            //当知道了拥有该种子信息的peer后，就可以与它通信拿到种子
            if (true){
                IMetaDataDownloader iMetaDataDownloader = injector.instanceByClass(IMetaDataDownloader.class);

                System.out.println("你走了吗");

            }

        } catch (Exception e) {
            Logger logger = Logger.getLogger(LogSetting.DEFAULT_NAME);
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }
}
