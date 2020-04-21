package render;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements Runnable{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
  
  public Thread game;
  
  public void start() {
    game = new Thread(this, "game");
    game.start();
  }
  
  public static void init() {
    LOGGER.debug("Initialising Game");    
  }
  
  public void run() {
    init();
    
    while (true) {
      update();
      render();
    }
  }
  
  private void render() {
    LOGGER.debug("Rendering Game");        
  }

  private void update() {
    LOGGER.debug("Updating Game");        
  }

  public static void main(String[] args) {
    
    new Main().start();

  }

}
