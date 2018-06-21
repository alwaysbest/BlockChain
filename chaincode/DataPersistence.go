package main

//数据持久化模块，将数据存入数据库中
import (
	"github.com/golang/protobuf/proto"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
)

//新增商品信息
func create(itemId string, message proto.Message, stub shim.ChaincodeStubInterface) error {
	value, _ := stub.GetState(itemId)
	if value != nil {
		return fmt.Errorf("itemId already exists")
	}

	if data, err := marshalProtobuf(message); err != nil {
		return fmt.Errorf("protobuf marshal error")
	} else {
		err = stub.PutState(itemId, data)
		if err != nil {
			return fmt.Errorf("put state error")
		}
		return nil
	}
}

//更新商品信息
func update(itemId string, message proto.Message, stub shim.ChaincodeStubInterface) error {
	if data, err := marshalProtobuf(message); err != nil {
		return fmt.Errorf("protobuf marshal error")
	} else {
		err = stub.PutState(itemId, data)
		if err != nil {
			return fmt.Errorf("put state error")
		}
		return nil
	}
	return nil
}

//查询商品信息
func retrieve(itemId string, stub shim.ChaincodeStubInterface) (*ItemAsset, error) {
	value, err := stub.GetState(itemId)
	if err != nil || value == nil {
		return nil, fmt.Errorf("error happens,asset not found %s", itemId)
	}
	iAsset := &ItemAsset{}
	err = unmarshalProtobuf(value, iAsset)
	if err != nil {
		return nil, fmt.Errorf("unmarshal error")
	}
	return iAsset, nil
}

/**
这里是为了统一存入数据库的编码方式，可能直接转为字节数组，可能变为json数据
 */
func marshalProtobuf(pb proto.Message) ([]byte, error) {
	if data, err := proto.Marshal(pb); err != nil {
		return nil, fmt.Errorf("protobuf marshal error")
	} else {
		return data, nil
	}
}

/**
这里是为了统一存入数据库的编码方式，可能直接转为字节数组，可能变为json数据
 */
func unmarshalProtobuf(data []byte, pb proto.Message) error {
	err := proto.Unmarshal(data, pb)
	if err != nil {
		return fmt.Errorf("unmarshal protubuf error")
	}
	return nil
}
