package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
	"github.com/golang/protobuf/proto"
	"time"
)

// ChaincodeDemo implements a simple chaincode to manage an asset
type ChaincodeDemo struct {
	//handlers用来存储调用的函数名称和器对应的函数
	handlers map[string]func(stub shim.ChaincodeStubInterface, args []byte) ([]byte, error)
}

func (t *ChaincodeDemo) Init(stub shim.ChaincodeStubInterface) peer.Response {
	// Get the args from the transaction proposal
	//千万要注意初始化！！！
	t.handlers = make(map[string]func(stub shim.ChaincodeStubInterface, args []byte) ([]byte, error))
	t.handlers["get"] = get
	t.handlers["set"] = set
	t.handlers["getHeader"] = getHeader
	t.handlers["getAllHist"] = getAllHist
	return shim.Success(nil)
}

func (t *ChaincodeDemo) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
	// Extract the function and args from the transaction proposal
	args := stub.GetArgs()
	fn := string(args[0])

	if fnHandler, ok := t.handlers[fn]; ok {
		if result, err := fnHandler(stub, args[1]); err != nil {
			return shim.Error(err.Error())
		} else {
			return shim.Success([]byte(result))
		}
	} else { // assume 'get' even if fn is nil
		return shim.Error("can't find function " + fn)
	}

}
func getHeader(stub shim.ChaincodeStubInterface, args []byte) ([]byte, error) {
	return stub.GetCreator()
}

func set(stub shim.ChaincodeStubInterface, args []byte) ([]byte, error) {
	i := &SimpleRequest{}
	proto.Unmarshal(args, i)
	i.TimeStamp = time.Now().UnixNano()
	if data, err := proto.Marshal(i); err != nil {
		return nil, fmt.Errorf("failed to Marshal object to json")
	} else {
		// We store the key and the value on the ledger
		err := stub.PutState(i.Name, data)
		if err != nil {
			return nil, fmt.Errorf("Failed to create asset: %s,with err msg", i.String(), err.Error())
		}
		return data, nil
	}
}

func get(stub shim.ChaincodeStubInterface, args [] byte) ([]byte, error) {
	value, err := stub.GetState(string(args))
	if err != nil {
		return nil, fmt.Errorf("Failed to get asset: %s with error: %s", string(args), err)
	}
	if value == nil {
		return nil, fmt.Errorf("Asset not found: %s", string(args))
	}
	return value, nil
}

//获得历史所有数据
func getAllHist(stub shim.ChaincodeStubInterface, args [] byte) ([]byte, error) {
	ite, err := stub.GetHistoryForKey(string(args))
	if err != nil {
		return nil, fmt.Errorf("failed to get all history")
	}
	resp := SimpleResponse{}
	resp.Requests = make([]*SimpleRequest, 0)
	for ite.HasNext() {
		v, _ := ite.Next()
		re := SimpleRequest{}
		proto.Unmarshal(v.Value, &re)
		resp.Requests = append(resp.Requests, &re)
	}

	if data, err := proto.Marshal(&resp); err != nil {
		return nil, fmt.Errorf("Failed to marshal proto in getAllHist: %s with error: %s",
			string(args), err)
	} else {
		return data, nil
	}
}

// main function starts up the chaincode in the container during instantiate
func main() {
	if err := shim.Start(new(ChaincodeDemo)); err != nil {
		fmt.Printf("Error starting ChaincodeDemo chaincode: %s", err)
	}
}
