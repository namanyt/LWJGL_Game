package com.cider.Engine.Graphics.Shaders;

import com.cider.Engine.Errors.GLSLFileNotFound;
import com.cider.Engine.Errors.InvalidToken;
import com.cider.Engine.Utils.Logger.Logger;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
  private int shaderProgramID;

  private String vertexSource;
  private String fragmentSource;
  private String filePath;

  public Shader(String FilePath) {
    this.filePath = FilePath;
    try {
      String source = new String(Files.readAllBytes(Paths.get(filePath)));
      String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

      int index = source.indexOf("#type") + 6;
      int eol = source.indexOf("\r\n", index);
      String firstString = source.substring(index, eol).trim();

      index = source.indexOf("#type", eol) + 6;
      eol = source.indexOf("\r\n", index);
      String secondPatter = source.substring(index, eol).trim();

      if (firstString.equals("vertex")) {
        vertexSource = splitString[1];
      } else if (firstString.equals("fragment")) {
        fragmentSource = splitString[1];
      } else {
        throw new InvalidToken("Unexpected token: \n\t" + firstString);
      }

      if (secondPatter.equals("vertex")) {
        vertexSource = splitString[2];
      } else if (secondPatter.equals("fragment")) {
        fragmentSource = splitString[2];
      } else {
        throw new InvalidToken("Unexpected token: \n\t" + secondPatter);
      }
    } catch (InvalidToken | IOException exception) {
      exception.printStackTrace();
      Logger.LogError(new GLSLFileNotFound("File Not found: " + filePath));
      return;
    }
  }

  public void compile() {
    // ============================================================
    // Compile and link shaders
    // ============================================================
    int vertexID, fragmentID;

    // First load and compile the vertex shader
    vertexID = glCreateShader(GL_VERTEX_SHADER);
    // Pass the shader source to the GPU
    glShaderSource(vertexID, vertexSource);
    glCompileShader(vertexID);

    // Check for errors in compilation
    int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
    if (success == GL_FALSE) {
      int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
      System.out.println("ERROR: '" + this.filePath + "'\n\tVertex shader compilation failed.");
      System.out.println(glGetShaderInfoLog(vertexID, len));
      assert false : "";
    }

    // First load and compile the vertex shader
    fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
    // Pass the shader source to the GPU
    glShaderSource(fragmentID, fragmentSource);
    glCompileShader(fragmentID);

    // Check for errors in compilation
    success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
    if (success == GL_FALSE) {
      int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
      System.out.println("ERROR: '" + this.filePath + "'\n\tFragment shader compilation failed.");
      System.out.println(glGetShaderInfoLog(fragmentID, len));
      assert false : "";
    }

    // Link shaders and check for errors
    shaderProgramID = glCreateProgram();
    glAttachShader(shaderProgramID, vertexID);
    glAttachShader(shaderProgramID, fragmentID);
    glLinkProgram(shaderProgramID);

    // Check for linking errors
    success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
    if (success == GL_FALSE) {
      int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
      System.out.println("ERROR: '" + this.filePath + "'\n\tLinking of shaders failed.");
      System.out.println(glGetProgramInfoLog(shaderProgramID, len));
      assert false : "";
    }
  }

  public void use() {
    glUseProgram(shaderProgramID);
  }

  public void detach() {
    glUseProgram(0);
  }

  public void uploadMat4f(String varName, Matrix4f mat4) {
    int varLocation = glGetUniformLocation(shaderProgramID, varName);
    FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
    mat4.get(matBuffer);
    glUniformMatrix4fv(varLocation, false, matBuffer);
  }
}
