
public class OstBlockXy {
  static Proxy proxy;
  static int port = 8080;
  static int size = 255;
  static boolean log = false;
  static String logname ="";
  private static String[] welcome =
  { 
    "#    # ##### ##### #####        #####  #####   ####  #    # #   #",
    "#    #   #     #   #    #       #    # #    # #    #  #  #   # # ",
    "######   #     #   #    # ##### #    # #    # #    #   ##     # ",
    "#    #   #     #   #####        #####  #####  #    #   ##     # ",
    "#    #   #     #   #            #      #   #  #    #  #  #    # ",
    "#    #   #     #   #            #      #    #  ####  #    #   # "
  };
  private static String[] welcome2 =
  { 
    " ####   ##### #####        #####  #       ####   ##### #    # #     # #     #",
    "#    # #        #          #    # #      #    # #      #   #   #   #   #   # ",
    "#    # #        #          #    # #      #    # #      #  #     # #     # #  ",
    "#    #  ####    #   #####  #####  #      #    # #      ###       #       #   ",
    "#    #      #   #          #    # #      #    # #      # #      # #      #   ",
    "#    #      #   #          #    # #      #    # #      #  #    #   #     #   ",
    " ####  #####    #          #####  ######  ####   ##### #   #  #     #    #   "
  };
  
  private static String[] help =
  {
    "You've asked for help? Maybe this can help you out:",
    "     -port / -p <port>     --- sets a custom port (standard is 8080)",
    "     -size / -s <size>     --- sets a custom size (standard is 255)",
    "     -log  / -l <logfile>  --- enables loggin to <logfile>",
    "     -help / -h            --- shows this page"
  };
  
  
  
  //LOGO https://www.gamedev.net/topic/313105-im-looking-for-an-ascii-ussr-logo/
  public static void main(String[] args) { //PARAMS [PORT] [SIZE] [LOG] [??]
    // TODO Auto-generated method stub
    
    for(String s : welcome2){
      System.out.println(s);
    }
    System.out.println();
    
    for(int i = 0; i < args.length; i++){
      switch (args[i]) {
        case "?":
        case "-h":
        case "-help":
        for(String s : help){
          System.out.println(s);
        }
        System.exit(0);
        break;
        case "-p": 
        case "-port":
        try {
          int nport = Integer.parseInt(args[i+1]);
          if (nport>=0&&nport<=65535) {
            port=nport;
          } 
          i++;
        } catch (Exception e) {
          System.err.println("Invalid use of argument "+args[i]+". See -help for usage.");
          System.exit(0);
        }  
        break;
        case "-s": 
        case "-size":
        try {
          size = Integer.parseInt(args[i+1]);
          i++;
        } catch (Exception e) {
          System.err.println("Invalid use of argument "+args[i]+". See -help for usage.");
          System.exit(0);
        }  
        break;
        case "-l":
        case "-log":
        try {
          logname=args[i+1];
          i++;
          log=true;
        } catch (Exception e) {
          System.err.println("Invalid use of argument "+args[i]+". See -help for usage.");
          System.exit(0);
        } 
        break; 
        default: 
        System.err.println("Invalid use of argument "+args[i]+". See -help for usage.");
        System.exit(0);
        break;
      } // end of switch
      
    }      
    proxy = new Proxy(port,size,log,logname);
    
  }
  
}
