package test;

import org.apache.zookeeper.*;

import java.io.IOException;

public class ZookeeperTest {
    // 会话超时时间，设置为与系统默认时间一致
    private static final int SESSION_TIMEOUT = 30 * 1000;
    private ZooKeeper zk;
    private Watcher wh;

    private void createZkInstance() {
        try {
            wh = watchedEvent -> System.err.println("WatchedEvent>>>>" + watchedEvent.toString());
            zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", SESSION_TIMEOUT, wh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zkOperation() throws KeeperException, InterruptedException {
        System.err.println("\n1.创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
        zk.create("/zoo2", "mydata2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.err.println("\n2.查看是否创建成功：");
        System.err.println(new String(zk.getData("/zoo2", this.wh, null)));

        System.err.println("\n3.修改节点数据 ");
        zk.setData("/zoo2", "shangh2018".getBytes(), -1);

        // 这里再次进行修改，则不会触发Watch事件，这就是我们验证ZK的一个特性“一次性触发”，也就是说设置一次监视，只会对下次操作起一次作用。
        System.err.println("\n3-1.再次修改节点数据 ");
        zk.setData("/zoo2", "shanhy20160310-ABCD".getBytes(), -1);

        System.err.println("\n4.查看是否修改成功： ");
        System.err.println(new String(zk.getData("/zoo2", false, null)));

        System.err.println("\n5.删除节点 ");
        zk.delete("/zoo2", -1);

        System.err.println("\n6. 查看节点是否被删除： ");
        System.err.println(" 节点状态： [" + zk.exists("/zoo2", false) + "]");
    }

    private void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZookeeperTest zookeeperTest = new ZookeeperTest();
        zookeeperTest.createZkInstance();
        zookeeperTest.zkOperation();
        zookeeperTest.close();
    }
}
