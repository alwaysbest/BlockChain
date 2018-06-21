package cn.edu.nju.software.fabricservice.serviceinvoker;

import cn.edu.nju.software.fabricservice.configmgt.HFConfig;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Daniel
 * @since 2018/5/2 12:19
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceInvokerId {
    private static final String CC_NAME = HFConfig.newInstance().getCcName();
    private static final String CC_VERSION = HFConfig.newInstance().getCcVersion();
    //TODO 这里的"myCC需要改"
    public static final ServiceInvokerId ITEM_ADD = new ServiceInvokerId(CC_NAME,
            CC_VERSION, "addItem");

    public static final ServiceInvokerId ITEM_GET = new ServiceInvokerId(CC_NAME,
            CC_VERSION, "getItem");

    public static final ServiceInvokerId ITEM_CHANGE = new ServiceInvokerId(CC_NAME,
            CC_VERSION, "changeItem");
    @Setter
    @Getter
    String name;
    @Setter
    @Getter
    String version;
    @Setter
    @Getter
    String func;

    private static String ID_PATTERN = "([a-zA-Z0-9_]+):([0-9.]+)-([a-zA-Z0-9]+)";

    public String getId() {
        return name + ":" + version + "-" + func;
    }

    public static ServiceInvokerId getInstanceByIdString(String serviceInvokerId) {
        if (!serviceInvokerId.matches(ID_PATTERN)) {
            return null;
        }
        String[] t1 = serviceInvokerId.split("-");
        String func = t1[1];
        String[] t2 = t1[0].split(":");
        String name = t2[0];
        String version = t2[1];
        return new ServiceInvokerId(name, version, func);
    }

    public static void main(String[] args) {
        System.out.println(getInstanceByIdString("myCC:1.0-itemAdd"));
    }


}