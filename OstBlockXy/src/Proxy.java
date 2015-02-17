import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.*;

public class Proxy {
	private ServerSocketChannel mAcceptor;
	private Selector mSelector;
	private Session[] mSessions;
	private int mSlots;
	private int mPort;
	private boolean log;
	
	
	Proxy(int port, int slots, boolean log){
		this.mPort = port;
		this.mSlots = slots;
		this.log = log;
		try {
			this.mAcceptor = ServerSocketChannel.open();
			this.mSelector = Selector.open();
			this.mAcceptor.register(this.mSelector, SelectionKey.OP_ACCEPT);
			this.mAcceptor.socket().bind(new InetSocketAddress(port));
			//this.mAcceptor.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void configureSession(final Session _session){
		
	}
	

	public void shutdown(){
		
	}
}