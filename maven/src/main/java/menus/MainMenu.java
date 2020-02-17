package menus;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class MainMenu {
    private long window;

    public void run(){
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);


        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        //Create Window
        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GFLW window");

        //Setup key callback
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        //Push new frame
        try (MemoryStack stack = stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            //Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            //Get resolution of primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            //Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) /2
            );
        }

        glfwMakeContextCurrent(window);

        //Enable V-Sync
        glfwSwapInterval(1);

        //Make the window visable
        glfwShowWindow(window);
    }

    private void loop(){
        //Creates openGL capabilities and makes openGL available
        GL.createCapabilities();

        //Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        //Run rendering loop until user attempts close or pressed escape key
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwSwapBuffers(window); //swap color buffers

            //Poll for window events
            glfwPollEvents();
        }
    }
}
