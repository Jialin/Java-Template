package template.dgcj;

public abstract class AbstractBroadcastProcessor {

  abstract void send(int nodeId);
  abstract void receive(int broadcastNodeId);

  public final int broadcastNodeId;

  AbstractBroadcastProcessor() {
    this(message.NumberOfNodes() - 1);
  }

  AbstractBroadcastProcessor(int broadcastNodeId) {
    this.broadcastNodeId = broadcastNodeId;
  }

  public void run() {
    if (message.MyNodeId() == broadcastNodeId) {
      for (int i = 0; i < message.NumberOfNodes(); ++i) {
        send(i);
        message.Send(i);
      }
    }
    message.Receive(broadcastNodeId);
    receive(broadcastNodeId);
  }
}
