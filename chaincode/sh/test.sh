#!/usr/bin/env bash
dir=chaincode/mychaincode
cd mychaincode

CORE_PEER_ADDRESS=peer:7052 CORE_CHAINCODE_ID_NAME=mycc:0 ./mychaincode

peer chaincode install -p chaincodedev/${dir} -n mycc -v 0

peer chaincode instantiate -n mycc -v 0 -c '{"Args":["a","{\"Name\":\"com\",\"Description\":\"dec1\"}"]}' -C myc

#peer chaincode invoke -n mycc -c '{"Args":["set", "a", "20"]}' -C myc

peer chaincode query -n mycc -c '{"Args":["query","a"]}' -C myc