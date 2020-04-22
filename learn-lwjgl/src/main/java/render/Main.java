package render;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import render.engine.io.Input;
import render.engine.io.Window;

public class Main implements Runnable{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
  
  private final int HEIGHT = 760;
  private final int WIDTH = 1280;
  

  
  public Thread game;
  public Window window;
  
  public void start() {
    game = new Thread(this, "game");
    game.start();
  }
  
  public void init() {
    LOGGER.debug("Initialising Game");
    window = new Window(WIDTH, HEIGHT, "Learn LWJGL");
    window.setBackgroundColour(1.0f, 0.0f, 0.0f);
//    window.setFullscreen(true);
    window.create();
  }
  
  public void run() {
    init();
    
    while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
      update();
      render();
      if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
    }
    window.cleanup();
  }
  
  private void render() {
//    LOGGER.debug("Rendering Game"); 
    window.swapBuffers();
  }

  private void update() {
//    LOGGER.debug("Updating Game");    
    window.update();
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) { 
      LOGGER.debug("Left Button Clicked: {}", "X: " + Input.getScrollX() +  ", Y: " + Input.getScrollY() );
    }
  }

  public static void main(String[] args) {
    
    new Main().start();

  }

}
