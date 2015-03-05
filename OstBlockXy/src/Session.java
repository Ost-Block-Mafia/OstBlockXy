
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;


public class Session {
	private SocketChannel clientSocketChannel; //Connected Client
	private SocketChannel serverSocketChannel; //Remote Socket
	private String remoteHost;
	private int remotePort;
	private SelectionKey clientSendKey;
	private SelectionKey serverSendKey;
	private SelectionKey clientRecKey;
	private SelectionKey serverRecKey;
	private Selector mSelector;
	private Proxy owner;
	private boolean connected = false;

	
	protected Session(final SocketChannel _socketchannel, final Proxy _owner, boolean isDropSession){ // DropSession will not count as real session, hence its functionality is to immediately drop the client with a corresponding message and disconnect
		this.clientSocketChannel = _socketchannel;
		
		this.owner = _owner;
		try {
			mSelector = Selector.open();
			this.serverSocketChannel = SocketChannel.open();
			this.serverSocketChannel.configureBlocking(false);
			this.clientSocketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
		    this.clientSocketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
		    this.serverSocketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
		    this.serverSocketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
		    clientSendKey = clientSocketChannel.register(mSelector,SelectionKey.OP_WRITE);
            serverSendKey = serverSocketChannel.register(mSelector,SelectionKey.OP_WRITE);
            clientRecKey = clientSocketChannel.register(mSelector, SelectionKey.OP_READ);
            serverRecKey = serverSocketChannel.register(mSelector,SelectionKey.OP_READ);
            
            clientSendKey.attach(new WriteStorage(false));
            serverSendKey.attach(new WriteStorage(true));
            clientRecKey.attach(new WriteStorage(false));
            serverRecKey.attach(new WriteStorage(true));
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		
		if(waitForConnect(this.clientSocketChannel)){

		try{
		while(clientSendKey.isValid() && serverSendKey.isValid() && clientRecKey.isValid() && serverRecKey.isValid()) {
			mSelector.select();
            Set readyKeys = mSelector.selectedKeys();
            Iterator it = readyKeys.iterator();

            while (it.hasNext()) {
                SelectionKey key = (SelectionKey)it.next();
                it.remove();
                boolean a = key.isReadable();
                boolean b = key.isWritable();
               
                if (key.isValid() && key.isReadable()) {
                    String ret = readMessage(key);
                    WriteStorage ws = (WriteStorage) key.attachment();
                    
                    
                    if (ws.isServer()) {
                    	if(ws.isServer()){
                    		//Process ServerData
                    		((WriteStorage)clientSendKey.attachment()).append(ret);
                    	}
                        //((WriteStorage)client.attachment()).append(modifyTraffic.server(ret));
                    } else {
                    	if(!connected){
                    		String requestedHost = ModifyTraffic.getHost(ret);
                    		URL host = new URL(requestedHost);
                    		this.remotePort = host.getPort();
                    		if(this.remotePort <0 && host.getProtocol().toLowerCase().equals("http")){
                    			this.remotePort = 80; // default settings http
                    		}
                    		if(this.remotePort <0 && host.getProtocol().toLowerCase().equals("https")){
                    			this.remotePort = 443; // default settings https
                    		}
                    		this.remoteHost = host.getHost();
                    		System.out.println(requestedHost);
                    		this.serverSocketChannel.connect(new InetSocketAddress(this.remoteHost,this.remotePort));
                    		waitForConnect(this.serverSocketChannel);
                    		
                    	}
                    	((WriteStorage)serverSendKey.attachment()).append(ret);
                        //((WriteStorage)server.attachment()).append(modifyTraffic.client(ret));
                    }
                    continue;
                }
               
                if (key.isValid() && key.isWritable()) {
                   writeMessage(key, (WriteStorage)key.attachment());
                }
               

					}
		}
		}catch(IOException ioe){
			
		}finally{
		System.out.println(this + " was destoyed!");
			//destroy();
		}
		
		
		}
	}
	
	private boolean waitForConnect(SocketChannel pending){
		try {
			while (!pending.finishConnect()) { 
			    //wait
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	};
	
	
	 public void writeMessage(SelectionKey key, WriteStorage writeStorage) throws IOException
	    {
	        SocketChannel socket = (SocketChannel)key.channel();
	        ByteBuffer buffer = ByteBuffer.wrap(writeStorage.getString().getBytes());
	        int nBytes = socket.write(buffer);
	        writeStorage.clear();
	    }
	       


	 
	    public String readMessage(SelectionKey key) throws IOException
	    {
	        int nBytes = 0;
	        SocketChannel socket = (SocketChannel)key.channel();
	        ByteBuffer buf = ByteBuffer.allocate(8192);
	            nBytes = socket.read(buf);
	            if (nBytes == -1) {
	                return null;
	            }
	            buf.flip();
	            Charset charset = Charset.forName("us-ascii"); //Not sure bout that nigaa US ASCII BEST SHIT YOO; just joking lets use UTF-32
	            CharsetDecoder decoder = charset.newDecoder();
	            CharBuffer charBuffer = decoder.decode(buf);
	            String result = charBuffer.toString();
	            System.out.println(result);
	            return result;

	    } 
	
	
	
	public void destroy(){
		try {
			this.clientSocketChannel.close();
			this.clientSocketChannel = null;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
