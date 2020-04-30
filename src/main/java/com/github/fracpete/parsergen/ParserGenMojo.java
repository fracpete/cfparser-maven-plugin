package com.github.fracpete.parsergen;

/*
 * Copyright 2020 University of Waikato, Hamilton, NZ.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.IOException;

/**
 * Mojo for parser generation using JavaCup and JFlex.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @goal build
 * @phase process-resources
 */
public class ParserGenMojo extends AbstractMojo {

  /**
   * @parameter
   * @required
   */
  protected String[] directories;

  public void execute() throws MojoExecutionException, MojoFailureException {
    String[] options;
    File dir;

    if (directories.length == 0)
      throw new MojoExecutionException("At least one directory needs to be specified!");

    for (String directory: directories) {
      dir = new File(directory);
      getLog().info("Processing directory: " + dir);

      try {
        if (!dir.exists())
          throw new IOException("Directory does not exist: " + dir);

        // JFlex
        options = new String[]{
          "--jlex",
          "--quiet",
          "--nobak",
          "--outdir",
          dir.getAbsolutePath().replace("resources", "java"),
          dir.getAbsolutePath() + "/Scanner.jflex"
        };
        JFlex.Main.main(options);

        // java-cup
        options = new String[]{
          "-parser",
          "Parser",
          "-interface",
          "-nosummary",
          "-destdir",
          dir.getAbsolutePath().replace("resources", "java"),
          dir.getAbsolutePath() + "/Parser.cup"
        };
        java_cup.Main.main(options);
      }
      catch (Exception e) {
        getLog().error(e);
        throw new MojoFailureException(e.getMessage());
      }
    }
  }
}
