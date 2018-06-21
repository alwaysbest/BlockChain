package cn.edu.nju.software.fabricservice.serviceinvoker;

/**
 * 发起调用的返回类型
 */
public enum ReturnType {
    /**
     * 直接返回，丢弃结果，不对结果做任何处理，不管任何错误情况
     */
    DIRECTLY,
    /**
     * 同步调用，等待结果返回
     */
    SYNC,
    /**
     * 异步调用，需要设置回调函数
     */
    ASYNC;

    public static void main(String[] args) {
        double a = 1.0;
        double b = 2.0;
        int iter = 1000;
        for (int i = 0; i < iter; i++) {
            double c = (a + b) / 2;
            a = b;
            b = c;
            if (i % 10 == 0)
                System.out.printf("a=%f,b=%f\n", a, b);
        }
    }
}
