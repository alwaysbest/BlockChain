package cn.edu.nju.software.fabricservice.configmgt;

import com.google.gson.Gson;
import lombok.*;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 配置文件，
 */
@Data
public class HFConfig {
    List<ChannelConfig> channels;
    List<UserConfig> users;
    List<ChaincodeConfig> chaincodes;
    CAConfig caConfig;
    String defaultChannel;
    String defaultUser;
    String ccName;
    String ccVersion;

    private HFConfig() {
    }

    public static HFConfig newInstance() {
        return newInstanceByFileStream(HFConfig.class.getResourceAsStream("/config.yml"));
    }


    public static HFConfig newInstanceByFileStream(InputStream inputStream) {
        Yaml yaml = new Yaml();
        return yaml.loadAs(inputStream, HFConfig.class);
    }

    public static HFConfig newInstanceByFile(String fileName) throws IOException {
        return newInstanceByFileStream(Files.newInputStream(Paths.get(fileName)));
    }


    public static void main(String[] args) {
        HFConfig hfConfig = HFConfig.newInstance();
        System.out.println(new Gson().toJson(hfConfig));
    }


}
