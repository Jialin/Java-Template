package template.dgcj;

public abstract class AbstractLinearProcessor {

  abstract void handleSelf();
  abstract void receiveMessages(int prevNodeId);
  abstract void handleMessages();
  abstract void sendMessages(int nextNodeId);
  abstract void finishLastNode();

  public final int myNodeId;

  public AbstractLinearProcessor() {
    myNodeId = message.MyNodeId();
  }

  public int prevNodeId() {
    return myNodeId == 0 ? -1 : myNodeId - 1;
  }

  public int nextNodeId() {
    return myNodeId + 1 == message.NumberOfNodes() ? -1 : myNodeId + 1;
  }

  public void run() {
    handleSelf();
    int prevNodeId = prevNodeId();
    if (prevNodeId >= 0) {
      message.Receive(prevNodeId);
      receiveMessages(prevNodeId);
    }
    handleMessages();
    int nextNodeId = nextNodeId();
    if (nextNodeId >= 0) {
      sendMessages(nextNodeId);
      message.Send(nextNodeId);
    }
    if (nextNodeId < 0) {
      finishLastNode();
    }
  }
}
