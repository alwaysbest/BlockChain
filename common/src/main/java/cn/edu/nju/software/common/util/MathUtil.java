package cn.edu.nju.software.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @since 2018/5/7 20:45
 */
public class MathUtil {
    public static List<Integer> random(int size, int bound, boolean uniq) {
        if (size > bound)
            return null;
        List<Integer> re = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            while (true) {
                int tem = (int) (Math.random() * bound);
                if (uniq && re.contains(tem))
                    continue;
                re.add(tem);
                break;
            }
        }
        return re;
    }

    public static void main(String[] args) {
        System.out.println(random(10, 10, true));
    }
}
