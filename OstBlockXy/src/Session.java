
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.*;


public class Session {
	private SocketChannel mSocketChannel;
	private Selector mSelector;
	private ByteBuffer mBuffer;
	private boolean alive;
	private boolean valid;	//validated client session, to ensure we have a real HTTP/S client that wants to connect with our services
	
	protected Session(final SocketChannel _socketchannel, final Selector _selector){
		this.mSocketChannel = _socketchannel;
		this.mSelector = _selector;
		try {
			this.mSocketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	public void destroy(){
		try {
			this.alive = false;
			this.mSocketChannel.close();
			this.mSocketChannel = null;
			this.mBuffer = null;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
