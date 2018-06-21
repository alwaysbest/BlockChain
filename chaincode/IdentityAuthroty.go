package main

import (
	"github.com/hyperledger/fabric/core/chaincode/lib/cid"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"fmt"
)

//身份认证模块
const (
	ITEM_ADD    = iota //0
	ITEM_CHANGE        //1
	ITEM_GET           //2
	ITEM_RECALL
)

const MANUFACTURE_ORG = "Org1MSP"

//检查当前调用的组织是否有对应方法的调用权限
func check(funcName int, stub shim.ChaincodeStubInterface, itemId string) error {
	orgId, err := cid.GetMSPID(stub)
	if err != nil {
		return fmt.Errorf("error get mspId")
	}
	switch funcName {
	case ITEM_GET:
		return nil
	case ITEM_CHANGE:
		iAsset, err := retrieve(itemId, stub)
		if err != nil || iAsset == nil {
			return fmt.Errorf("error happens,asset not found %s", itemId)
		}
		status := iAsset.OpsStatus
		if orgId != status.CurrentOrg {
			return fmt.Errorf("error org when invoke item_change %s", orgId)
		}
	case ITEM_ADD:
		if orgId != MANUFACTURE_ORG {
			return fmt.Errorf("error org when invoke item_add %s", orgId)
		}
	case ITEM_RECALL:
		if orgId != MANUFACTURE_ORG {
			return fmt.Errorf("error org when invoke item_recall %s", orgId)
		}
	}
	return nil
}
