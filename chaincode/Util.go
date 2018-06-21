package main

import (
	"fmt"
)

func checkItemIdformat(itemId string) bool {
	return len(itemId) == 32
}

func createError(msg string, args ...string) ([]byte, error) {
	return nil, fmt.Errorf(msg, args)
}

func createSuccessResp(data []byte) Response {
	return Response{0, "success", data}

}

func createRespWithoutData(eCode int32, msg string, format ...string) Response {
	return Response{eCode, fmt.Sprintf(msg, format), nil}
}

func createResp(eCode int32, data []byte, msg string, format ...string) Response {
	return Response{eCode, fmt.Sprintf(msg, format), data}
}

func setError(response *Response, eCode int32, msg string) Response {
	response.Code = eCode
	response.Message = msg
	return *response
}

