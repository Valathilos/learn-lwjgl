package render.engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window {
  private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

  private  int width;
  private int height;
  private String title;
  private long window;
  
  private int frames;
  private long time;
  
  public Input input;
  
  private float backgroundR;
  private float backgroundG;
  private float backgroundB;
  
  private GLFWWindowSizeCallback sizeCallback;
  
  private boolean isResized;
  private boolean isFullscreen;

  private int[] windowPosX = new int[1];
  private int[] windowPosY = new int[1];

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
    
    input = new Input();
    
    window = GLFW.glfwCreateWindow(width, height, title, isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0); 
    
    if (window == 0) {
      LOGGER.error("Window wasn't created");
      return;      
    }
    
    GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
    
    windowPosX[0] = vidMode.width() - width / 2;
    windowPosY[0] = vidMode.height() - height / 2;
        
    GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
    GLFW.glfwMakeContextCurrent(window);
    GL.createCapabilities();
    GL11.glEnable(GL11.GL_DEPTH_TEST);
    
    createCallbacks();
    
    GLFW.glfwShowWindow(window);
    
    GLFW.glfwSwapInterval(1);
    
    time = System.currentTimeMillis();

  }
  
  private void createCallbacks() {
    sizeCallback = new GLFWWindowSizeCallback() {      
      public void invoke(long window, int w, int h) {
        width = w;
        height = h;
        isResized = true;
      }
    };
    
    GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
    GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
    GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
    GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
    GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
        
  }
  
  public void update() {
    if (isResized) {
      GL11.glViewport(0, 0, width, height);
      isResized = false;
    }
    
    GL11.glClearColor(backgroundR, backgroundG, backgroundB, 1.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    
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
  
  public void cleanup() {
    input.cleanup();
    sizeCallback.free();
    GLFW.glfwWindowShouldClose(window);
    GLFW.glfwDestroyWindow(window);
    
    GLFW.glfwTerminate();
  }
  
  public void setBackgroundColour(float r, float g, float b) {
    this.backgroundR = r;
    this.backgroundG = g;
    this.backgroundB = b;
  }

  public boolean isFullscreen() {
    return isFullscreen;
  }

  public void setFullscreen(boolean isFullscreen) {
    this.isFullscreen = isFullscreen;
    isResized = true;
    if (isFullscreen) {
      GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
      GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), windowPosX[0], windowPosY[0], width, height, 0);
    } else {
      GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getTitle() {
    return title;
  }

  public long getWindow() {
    return window;
  }
}
