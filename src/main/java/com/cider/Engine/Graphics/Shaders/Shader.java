package com.cider.Engine.Graphics.Shaders;

import com.cider.Engine.Errors.GLSLFileNotFound;
import com.cider.Engine.Errors.InvalidToken;
import com.cider.Engine.Errors.VertexCompilationFailed;
import com.cider.Engine.Scene.SceneManager;
import com.cider.Engine.Utils.Logger.Logger;
import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
  private int shaderProgramID;
  private boolean beingUsed = false;
  int vertexID, fragmentID;

  private String vertexSource;
  private String fragmentSource;
  private final String filepath;

  public Shader(String filepath) {
    this.filepath = filepath;
    Logger.LogTrace("Loading Shaders from: " + this.filepath);
    try {
      String source = new String(Files.readAllBytes(Paths.get(filepath)));
      String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

      // Find the first pattern after #type 'pattern'
      int index = source.indexOf("#type") + 6;
      int eol = source.indexOf("\r\n", index);
      String firstPattern = source.substring(index, eol).trim();

      // Find the second pattern after #type 'pattern'
      index = source.indexOf("#type", eol) + 6;
      eol = source.indexOf("\r\n", index);
      String secondPattern = source.substring(index, eol).trim();

      if (firstPattern.equals("vertex")) {
        vertexSource = splitString[1];
      } else if (firstPattern.equals("fragment")) {
        fragmentSource = splitString[1];
      } else {
        throw new InvalidToken("Unexpected token '" + firstPattern + "'");
      }

      if (secondPattern.equals("vertex")) {
        vertexSource = splitString[2];
      } else if (secondPattern.equals("fragment")) {
        fragmentSource = splitString[2];
      } else {
        throw new InvalidToken("Unexpected token '" + secondPattern + "'");
      }
    } catch(IOException | InvalidToken e) {
      e.printStackTrace();
      assert false : "Error: Could not open file for shader: '" + filepath + "'";
    }
  }

  public void compile() {
    // ============================================================
    // Compile and link shaders
    // ============================================================

    // First load and compile the vertex shader
    vertexID = glCreateShader(GL_VERTEX_SHADER);
    // Pass the shader source to the GPU
    glShaderSource(vertexID, vertexSource);
    glCompileShader(vertexID);

    // Check for errors in compilation
    int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
    if (success == GL_FALSE) {
      int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
      Logger.LogError(new VertexCompilationFailed("Vertex shader compilation failed: " + fragmentSource));
      System.out.println(glGetShaderInfoLog(vertexID, len));
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
      Logger.LogError(new VertexCompilationFailed("Fragment shader compilation failed: " + fragmentSource));
      System.out.println(glGetShaderInfoLog(fragmentID, len));
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
      System.out.println("ERROR: '" + filepath + "'\n\tLinking of shaders failed.");
      System.out.println(glGetProgramInfoLog(shaderProgramID, len));
      assert false : "";
    }

    Logger.LogTrace("Shader Program ID: " + shaderProgramID);
    Logger.LogTrace("Vertex ID: " + vertexID);
    Logger.LogTrace("Fragment ID: " + fragmentID);
  }

  public void use() {
    if (!beingUsed) {
      // Bind shader program
      glUseProgram(shaderProgramID);
      beingUsed = true;
    }
  }

  public void detach() {
    glUseProgram(0);
    beingUsed = false;
  }

  public void uploadMat4f(String varName, Matrix4f mat4) {
    int varLocation = glGetUniformLocation(shaderProgramID, varName);
    use();
    FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
    mat4.get(matBuffer);
    glUniformMatrix4fv(varLocation, false, matBuffer);
  }

  public void uploadMat3f(String varName, Matrix3f mat3) {
    int varLocation = glGetUniformLocation(shaderProgramID, varName);
    use();
    FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
    mat3.get(matBuffer);
    glUniformMatrix3fv(varLocation, false, matBuffer);
  }

  public void uploadVec4f(String varName, Vector4f vec) {
    int varLocation = glGetUniformLocation(shaderProgramID, varName);
    use();
    glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
  }

  public void uploadVec3f(String varName, Vector3f vec) {
    int varLocation = glGetUniformLocation(shaderProgramID, varName);
    use();
    glUniform3f(varLocation, vec.x, vec.y, vec.z);
  }

  public void uploadVec2f(String varName, Vector2f vec) {
    int varLocation = glGetUniformLocation(shaderProgramID, varName);
    use();
    glUniform2f(varLocation, vec.x, vec.y);
  }

  public void uploadFloat(String varName, float val) {
    int varLocation = glGetUniformLocation(shaderProgramID, varName);
    use();
    glUniform1f(varLocation, val);
  }

  public void uploadInt(String varName, int val) {
    int varLocation = glGetUniformLocation(shaderProgramID, varName);
    use();
    glUniform1i(varLocation, val);
  }

  public void uploadTexture(String varName, int slot) {
    int varLocation = glGetUniformLocation(shaderProgramID, varName);
    use();
    glUniform1i(varLocation, slot);
  }
}
