package template.dgcj;

public abstract class AbstractMasterProcessor {

  abstract void sendTaskMaster(int slaveNodeId);
  abstract void receiveTaskSlave(int masterNodeId);
  abstract void handleSlave();
  abstract void sendSlave(int masterNodeId);
  abstract void receiveMaster(int slaveNodeId);
  abstract void handleMaster();
  abstract void finishMaster();

  public final int slaveCnt;
  public final int myNodeId;
  public final int masterNodeId;

  AbstractMasterProcessor(int slaveCnt) {
    this.slaveCnt = slaveCnt;
    myNodeId = message.MyNodeId();
    masterNodeId = getMasterNodeId();
    verifyInternal();
  }

  boolean isSlave() {
    return isSlave(myNodeId);
  }

  boolean isSlave(int nodeId) {
    return nodeId < slaveCnt;
  }

  boolean isMaster() {
    return isMaster(myNodeId);
  }

  boolean isMaster(int nodeId) {
    return nodeId == message.NumberOfNodes() - 1;
  }

  public void run() {
    if (isMaster()) {
      for (int i = 0; i < message.NumberOfNodes(); ++i) if (isSlave(i)) {
        sendTaskMaster(i);
        message.Send(i);
      }
    }
    if (isSlave()) {
      message.Receive(masterNodeId);
      receiveTaskSlave(masterNodeId);
      handleSlave();
      sendSlave(masterNodeId);
      message.Send(masterNodeId);
    }
    if (isMaster()) {
      for (int i = 0; i < message.NumberOfNodes(); ++i) if (isSlave(i)) {
        message.Receive(i);
        receiveMaster(i);
      }
      handleMaster();
      finishMaster();
    }
  }

  private int getMasterNodeId() {
    for (int i = 0; i < message.NumberOfNodes(); ++i) if (isMaster(i)) {
      return i;
    }
    throw new IllegalArgumentException("Master node not found.");
  }

  private void verifyInternal() {
    if (slaveCnt + 1 > message.NumberOfNodes()) {
      throw new IllegalArgumentException("Too many slave nodes.");
    }
    if (isSlave() && isMaster()) {
      throw new IllegalArgumentException("A node can't be both master and slave node.");
    }
    int masterNodeCnt = 0;
    for (int i = 0; i < message.NumberOfNodes(); ++i) if (isMaster(i)) {
      ++masterNodeCnt;
    }
    if (masterNodeCnt != 1) {
      throw new IllegalArgumentException("There should be one and only one master node.");
    }
  }
}
