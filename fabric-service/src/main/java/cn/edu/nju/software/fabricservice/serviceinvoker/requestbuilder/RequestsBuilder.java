package cn.edu.nju.software.fabricservice.serviceinvoker.requestbuilder;

/**
 * @author Daniel
 * @since 2018/5/2 0:10
 */
public class RequestsBuilder {

    public static ItemChangeRequestsBuilder newItemChangeRequestsBuilder() {
        return new ItemChangeRequestsBuilder();
    }

    public static ItemAddRequestsBuilder newItemAddRequestBuilder() {
        return new ItemAddRequestsBuilder();
    }

    public static ItemGetRequestsBuilder newItemGetRequestsBuilder() {
        return new ItemGetRequestsBuilder();
    }



}
