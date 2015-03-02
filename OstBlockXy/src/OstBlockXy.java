
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
  //LOGO https://www.gamedev.net/topic/313105-im-looking-for-an-ascii-ussr-logo/
  public static void main(String[] args) { //PARAMS [PORT] [SIZE] [LOG] [??]
    // TODO Auto-generated method stub
    
    for(String s : welcome2){
      System.out.println(s);
    }
    System.out.println();
    
    for(int i = 0; i < args.length; i++){
      //TODO habndle args = http://www.javaworld.com/article/2074849/core-java/processing-command-line-arguments-in-java--case-closed.html
      switch (args[i]) {
        case "-p": 
        case "-port":
          try {
            int nport = Integer.parseInt(args[i+1]);
            if (nport>=0&&nport<=65535) {
              port=nport;
            } 
            i++;
          } catch (NumberFormatException e) {
            System.err.println("Argument " + args[i] + " must be an integer.");
          }  
          break;
        case "-s": 
        case "-size":
          try {
            size = Integer.parseInt(args[i+1]);
            i++;
          } catch (NumberFormatException e) {
            System.err.println("Argument " + args[i] + " must be an integer.");
          }  
        break;
        case "-l":
        case "-log":
          logname=args[i+1];
          i++;
          log=true;
        default: 
          break;
      } // end of switch
      
    }      
    proxy = new Proxy(port,size,log,logname);
    
  }
  
}
