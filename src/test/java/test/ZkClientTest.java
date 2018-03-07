package test;

import org.I0Itec.zkclient.*;
import org.apache.zookeeper.Watcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ZkClientTest {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        String node = "/myApp";
        childChangesListener(zkClient, node);
        dataChangsListener(zkClient, node);
        stateChangesListener(zkClient);

        if (!zkClient.exists(node)) {
            zkClient.createPersistent(node, "hello 我来了");
        }

        Object data = zkClient.readData(node, true);
        System.err.println(data.toString());
        zkClient.updateDataSerialized(node, (DataUpdater<String>) currentData -> currentData + "-123");
        data = zkClient.readData(node, true);
        System.err.println(data.toString());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void childChangesListener(ZkClient zkClient, final String path) {
        zkClient.subscribeChildChanges(path,
                (parentPath, currentChilds) -> System.out.println("clildren of path "
                        + parentPath + ":" + currentChilds));
    }

    static void dataChangsListener(ZkClient zkClient, final String path) {
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Data of " + dataPath + " has changed.");
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Data of " + dataPath + " has changed.");
            }
        });
    }

    static void stateChangesListener(ZkClient zkClient) {
        zkClient.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                System.out.println("handleStateChanged");
            }

            @Override
            public void handleNewSession() throws Exception {
                System.out.println("handleSessionEstablishmentError");
            }

            @Override
            public void handleSessionEstablishmentError(Throwable error) throws Exception {
                System.out.println("handleNewSession");
            }
        });
    }
}
