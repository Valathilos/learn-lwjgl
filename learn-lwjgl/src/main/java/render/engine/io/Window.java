package render.engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window {
  private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

  private int width;
  private int height;
  private String title;
  private long window;
  
  private int frames;
  private long time;


  public Window(int width, int height, String title) {
    this.width = width;
    this.height = height;
    this.title = title;
  }

  public void create() {
    if (!GLFW.glfwInit()) {
      LOGGER.error("GLFW wasn't initialised");
      return;
    }
    
    window = GLFW.glfwCreateWindow(width, height, title, 0, 0); 
    
    if (window == 0) {
      LOGGER.error("Window wasn't created");
      return;      
    }
    
    GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
    
    GLFW.glfwSetWindowPos(window, vidMode.width() - width / 2, vidMode.height() - height / 2);
    GLFW.glfwMakeContextCurrent(window);
    
    GLFW.glfwShowWindow(window);
    
    GLFW.glfwSwapInterval(1);
    
    time = System.currentTimeMillis();

  }
  
  public void update() {
    GLFW.glfwPollEvents();
    frames++;
    if (System.currentTimeMillis() > time + 1000) {
      GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames);
      time = System.currentTimeMillis();
      frames = 0;
    }
  }
  
  public void swapBuffers() {
    GLFW.glfwSwapBuffers(window);
  }
  
  public boolean shouldClose() {
    return GLFW.glfwWindowShouldClose(window);
  }
}
