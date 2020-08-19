package com.blockchain.iot;

import com.blockchain.iot.data.TestData;
import com.blockchain.iot.model.Block;
import com.blockchain.iot.model.ParkingSpace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ParkingSpaceController {

    List<ParkingSpace> parkingSpaces = new ArrayList<ParkingSpace>();

    List<Block> blockChain = new ArrayList<Block>();

    int prefix = 3;

    String prefixString = new String(new char[prefix]).replace('\0', '0');

    @GetMapping("/get-respose-from-3")
    public String getData() {

        return "response from node 3";
    }

    @GetMapping("/parkingspaces")
    public List<ParkingSpace> getSmartHome() {

        for (int i = 0; i < blockChain.size(); i++) {
            String previousHash = i == 0 ? "0"
                    : blockChain.get(i - 1)
                    .getHash();
            boolean flag = blockChain.get(i)
                    .getHash()
                    .equals(blockChain.get(i)
                            .calculateBlockHash())
                    && previousHash.equals(blockChain.get(i)
                    .getPreviousHash())
                    && blockChain.get(i)
                    .getHash()
                    .substring(0, prefix)
                    .equals(prefixString);
            if (flag) {
                System.out.println("Blocks in the block chain is validated");
            }
        }
        return parkingSpaces;
    }

    @PostMapping("/parkingspaces")
    public String saveSmartHome(@RequestBody ParkingSpace parkingSpace) {
        parkingSpaces.add(parkingSpace);

        if (blockChain.size() == 0) {
            Block parkingSpaceBlock = new Block("This is iotblockchain3 block", parkingSpace, "0", new Date().getTime());
            parkingSpaceBlock.mineBlock(prefix);
            blockChain.add(parkingSpaceBlock);
        } else {
            Block parkingSpaceBlock = new Block("This is iotblockchain3 block", parkingSpace, blockChain.get(blockChain.size() - 1).getHash(), new Date().getTime());
            parkingSpaceBlock.mineBlock(prefix);
            blockChain.add(parkingSpaceBlock);
        }
        System.out.println("Block No: "+blockChain.size());
        return "success";
    }

    @GetMapping("/seeddata")
    public void insertData() {
        TestData.callPost();
    }
}
