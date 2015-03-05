import java.util.ArrayList;


public class ModifyTraffic {

	public static String getHost(String req){//null if no host was found
		String[] lines = req.split("\r\n");
		if(lines.length >0){
		String[] firstline = lines[0].split(" ");
		if(firstline.length >0){
		for(int i =0; i<firstline.length;i++){
			String s = firstline[i];
			if(s.toLowerCase().equals("post") || s.toLowerCase().equals("get")){
				return firstline[i+1];
			}
		}
		}
		}
		return null;
	}
	
	public static String killReferer(String req){
		if(req.toLowerCase().contains("referer:")){
		String[] lines = req.split("\r\n");
		ArrayList<String> duplicate = new ArrayList<String>();
		for(String s :  lines){
			duplicate.add(s);
		}
		
		
		for(int i =0; i<lines.length;i++){
			String s = lines[i];
			if(s.toLowerCase().contains("referer:")){
				duplicate.remove(i);
			}
		}
		StringBuilder sb = new StringBuilder();
		for(String s : duplicate){
			sb.append(s);
			sb.append("\r\n");
		}
		
		
		return sb.toString();
		}
		return req;
	}
}
