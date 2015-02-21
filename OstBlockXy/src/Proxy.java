import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Proxy {
	private ServerSocketChannel mAcceptor;
	private Selector mSelector;
	private HashSet<Session> mSessions = new HashSet<Session>();
	private int mSlots;
	private int mPort;
	private boolean log;
	private File mLog;
	
	
	
	
	Proxy(int port, int slots, boolean log, String logname){
		this.mPort = port;
		this.mSlots = slots;
		this.log = log;
		try {
			this.mAcceptor = ServerSocketChannel.open();
			this.mAcceptor.configureBlocking(false);
			this.mSelector = Selector.open();
			this.mAcceptor.register(this.mSelector, SelectionKey.OP_ACCEPT);
			this.mAcceptor.socket().bind(new InetSocketAddress(port));
			//this.mAcceptor.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String host ="";
		try {
			host = mAcceptor.getLocalAddress().toString();
			System.out.println("Server listening on IP: " + host);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not get local socket; system will exit now");
			System.exit(0);
		}
	      
	      
	      
		System.out.println();
		beginAccept();
	}
	

	private void configureSession(final Session _session){
		
	}
	
	private void beginAccept(){
		System.out.println("Listening fot incomming Clients!");
		while(true){  	
				//prevent 
		try{
				//provisorisch
			mSelector.select();
		      Set<SelectionKey> readyKeys = mSelector.selectedKeys();
		      Iterator<SelectionKey> iterator = readyKeys.iterator();
		      while (iterator.hasNext()) {
		        SelectionKey key = (SelectionKey) iterator.next();
		        iterator.remove();
		        if (key.isAcceptable()) {
		          SocketChannel client = mAcceptor.accept();
		          System.out.println("Accepted connection from " + client);
		          client.configureBlocking(false);
		          if(mSessions.size() <= mSlots){	
		          mSessions.add(new Session(client,this.mSelector));
		          }else{
		        	  dropClient(client);
		          }
		          
		          //SelectionKey key2 = client.register(mSelector, SelectionKey.OP_WRITE);
		        }
		        
		        key.channel().close();
		      }
		    
		}catch(IOException ioe){
			ioe.printStackTrace();
			
		}
			}
		
		}


	private void dropClient(final SocketChannel client){
		
	}

	public void shutdown(){
		
	}
}