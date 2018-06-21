package main

//数据协议模块
import (
	"github.com/golang/protobuf/proto"
	"fmt"
)

func encode(message proto.Message) ([]byte, error) {
	if data, err := proto.Marshal(message); err != nil {
		return nil, fmt.Errorf("protobuf marshal error")
	} else {
		return data, nil
	}
}

func decode(data [] byte, message proto.Message) error {
	err := proto.Unmarshal(data, message)
	if err != nil {
		return fmt.Errorf("unmarshal protubuf error")
	}
	return nil
}


