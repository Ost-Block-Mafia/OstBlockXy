
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.*;


public class Session {
	private SocketChannel mSocketChannel;
	private ByteBuffer mBuffer;
	private boolean alive;
	
	protected Session(final SocketChannel _socketchannel){
		this.mSocketChannel = _socketchannel;
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
