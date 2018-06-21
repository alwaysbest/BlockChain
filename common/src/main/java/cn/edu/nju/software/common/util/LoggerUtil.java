package cn.edu.nju.software.common.util;

import org.slf4j.Logger;
import sun.rmi.runtime.Log;

/**
 * @author Daniel
 * @since 2018/5/1 0:13
 */
public class LoggerUtil {
    public static void errorf(Logger logger, String format, String... args) {
        System.out.println(String.format(format,args));
//        logger.error(String.format(format, args));
    }

    public static void infof(Logger logger, String format, String... args) {
        logger.info(String.format(format, args));
    }

    public static void debuf(Logger logger, String format, String... args) {
        logger.info(String.format(format, args));
    }
}
