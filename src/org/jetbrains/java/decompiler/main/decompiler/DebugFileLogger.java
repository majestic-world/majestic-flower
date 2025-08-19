// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.java.decompiler.main.decompiler;

import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.util.TextUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DebugFileLogger extends IFernflowerLogger {

  private final PrintStream consoleStream;
  private final PrintWriter fileWriter;
  private int indent;
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public DebugFileLogger(PrintStream consoleStream, String logFilePath) throws IOException {
    this.consoleStream = consoleStream;
    this.indent = 0;
    
    if (logFilePath != null) {
      // Create logs.log file
      File logFile = new File(logFilePath);
      if (logFile.getParentFile() != null) {
        logFile.getParentFile().mkdirs(); // Create directories if they don't exist
      }
      
      this.fileWriter = new PrintWriter(new OutputStreamWriter(
          new FileOutputStream(logFile, true), StandardCharsets.UTF_8));
      
      // Write header to log file
      String timestamp = LocalDateTime.now().format(formatter);
      fileWriter.println("\n=== FERNFLOWER DEBUG SESSION STARTED AT " + timestamp + " ===");
      fileWriter.flush();
    } else {
      this.fileWriter = null;
    }
  }

  // Constructor for console-only logging
  public DebugFileLogger(PrintStream consoleStream) {
    this.consoleStream = consoleStream;
    this.indent = 0;
    this.fileWriter = null;
  }

  private void logToFile(String message, Severity severity) {
    if (fileWriter != null) {
      String timestamp = LocalDateTime.now().format(formatter);
      String logMessage = String.format("[%s] %s %s%s", 
          timestamp, severity.name(), TextUtil.getIndentString(indent), message);
      fileWriter.println(logMessage);
      fileWriter.flush();
    }
  }

  private void logToFile(String message, Severity severity, Throwable t) {
    if (fileWriter != null) {
      logToFile(message, severity);
      if (t != null) {
        t.printStackTrace(fileWriter);
        fileWriter.flush();
      }
    }
  }

  @Override
  public void writeMessage(String message, Severity severity) {
    // Log to file
    logToFile(message, severity);
    
    // Log to console
    if (accepts(severity)) {
      consoleStream.println(severity.prefix + TextUtil.getIndentString(indent) + message);
    }
  }

  @Override
  public void writeMessage(String message, Severity severity, Throwable t) {
    // Log to file
    logToFile(message, severity, t);
    
    // Log to console
    if (accepts(severity)) {
      writeMessage(message, severity);
      t.printStackTrace(consoleStream);
    }
  }

  @Override
  public void startReadingClass(String className) {
    // Special handling for l2/gameserver/model/Creature
    if ("l2/gameserver/model/Creature".equals(className)) {
      String debugMessage = "=== BREAKPOINT: Starting to process problematic class: " + className + " ===";
      logToFile(debugMessage, Severity.WARN);
      consoleStream.println("DEBUG BREAKPOINT: " + debugMessage);
    }
    
    if (accepts(Severity.INFO)) {
      writeMessage("Decompiling class " + className, Severity.INFO);
      ++indent;
    }
  }

  @Override
  public void endReadingClass() {
    if (accepts(Severity.INFO)) {
      --indent;
      writeMessage("... done", Severity.INFO);
    }
  }

  @Override
  public void startClass(String className) {
    // Special handling for l2/gameserver/model/Creature
    if ("l2/gameserver/model/Creature".equals(className)) {
      String debugMessage = "=== BREAKPOINT: Processing class structure for: " + className + " ===";
      logToFile(debugMessage, Severity.WARN);
      consoleStream.println("DEBUG BREAKPOINT: " + debugMessage);
    }
    
    if (accepts(Severity.INFO)) {
      writeMessage("Processing class " + className, Severity.TRACE);
      ++indent;
    }
  }

  @Override
  public void endClass() {
    if (accepts(Severity.INFO)) {
      --indent;
      writeMessage("... proceeded", Severity.TRACE);
    }
  }

  @Override
  public void startMethod(String methodName) {
    if (accepts(Severity.INFO)) {
      writeMessage("Processing method " + methodName, Severity.TRACE);
      ++indent;
    }
  }

  @Override
  public void endMethod() {
    if (accepts(Severity.INFO)) {
      --indent;
      writeMessage("... proceeded", Severity.TRACE);
    }
  }

  @Override
  public void startWriteClass(String className) {
    // Special handling for l2/gameserver/model/Creature
    if ("l2/gameserver/model/Creature".equals(className)) {
      String debugMessage = "=== BREAKPOINT: Writing class content for: " + className + " ===";
      logToFile(debugMessage, Severity.WARN);
      consoleStream.println("DEBUG BREAKPOINT: " + debugMessage);
    }
    
    if (accepts(Severity.INFO)) {
      writeMessage("Writing class " + className, Severity.TRACE);
      ++indent;
    }
  }

  @Override
  public void endWriteClass() {
    if (accepts(Severity.INFO)) {
      --indent;
      writeMessage("... written", Severity.TRACE);
    }
  }

  public void close() {
    if (fileWriter != null) {
      String timestamp = LocalDateTime.now().format(formatter);
      fileWriter.println("=== FERNFLOWER DEBUG SESSION ENDED AT " + timestamp + " ===\n");
      fileWriter.close();
    }
  }
}
