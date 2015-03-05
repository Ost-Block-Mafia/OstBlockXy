public class WriteStorage {
    private StringBuffer toWrite;
    private boolean server;

    public WriteStorage(boolean server) {
        this.setServer(server);
        clear();
    }
    
    public boolean isServer() {
        return server;
    }
    public void setServer(boolean server) {
        this.server = server;
    }
    
    public void clear() {
        toWrite = new StringBuffer();
    }
    
    public void append(String s) {
        toWrite.append(s);
    }
    
    public String getString() {
        return toWrite.toString();
    }   
}