package render;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import render.engine.io.Window;

public class Main implements Runnable{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
  
  private final int HEIGHT = 760;
  private final int WIDTH = 1280;
  

  
  public Thread game;
  public static Window window;
  
  public void start() {
    game = new Thread(this, "game");
    game.start();
  }
  
  public void init() {
    LOGGER.debug("Initialising Game");
    window = new Window(WIDTH, HEIGHT, "Learn LWJGL");
    window.create();
  }
  
  public void run() {
    init();
    
    while (!window.shouldClose()) {
      update();
      render();
    }
  }
  
  private void render() {
//    LOGGER.debug("Rendering Game"); 
    window.swapBuffers();
  }

  private void update() {
//    LOGGER.debug("Updating Game");    
    window.update();
  }

  public static void main(String[] args) {
    
    new Main().start();

  }

}
