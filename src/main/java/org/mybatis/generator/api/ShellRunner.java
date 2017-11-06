/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.api;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * This class allows the code generator to be run from the command line.
 * 
 * @author Jeff Butler
 */
public class ShellRunner {

    /** 配置文件路径**/
    private static final String CONFIG_FILE = System.getProperty("user.dir") + "\\config.xml";

    /**是否覆盖已存在的文件**/
    private static final boolean OVERWRITE = true;

    private static final boolean VERBOSE = false ;

    /** 作者**/
    public static final String AUTHOR = "xxx";

    public static void main(String[] args) {

        if (isBlank(CONFIG_FILE)) {
            writeLine(getString("RuntimeError.0")); 
            return;
        }

        List<String> warnings = new ArrayList<String>();

        String configfile = CONFIG_FILE;
        File configurationFile = new File(configfile);
        if (!configurationFile.exists()) {
            writeLine(getString("RuntimeError.1", configfile)); 
            return;
        }

        try {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configurationFile);

            DefaultShellCallback shellCallback = new DefaultShellCallback(OVERWRITE);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);

            ProgressCallback progressCallback = VERBOSE ? new VerboseProgressCallback() : null;

            myBatisGenerator.generate(progressCallback, null, null);

        } catch (XMLParserException e) {
            writeLine(getString("Progress.3")); 
            writeLine();
            for (String error : e.getErrors()) {
                writeLine(error);
            }

            return;
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            writeLine(getString("Progress.16")); 
            for (String error : e.getErrors()) {
                writeLine(error);
            }
            return;
        } catch (InterruptedException e) {
            writeLine(e.getMessage());
        }

        for (String warning : warnings) {
            writeLine(warning);
        }

        if (warnings.size() == 0) {
            writeLine(getString("Progress.4")); 
        } else {
            writeLine();
            writeLine(getString("Progress.5")); 
        }
    }

    private static void usage() {
        String lines = getString("Usage.Lines"); 
        int iLines = Integer.parseInt(lines);
        for (int i = 0; i < iLines; i++) {
            String key = "Usage." + i; 
            writeLine(getString(key));
        }
    }

    private static void writeLine(String message) {
        System.out.println(message);
    }

    private static void writeLine() {
        System.out.println();
    }

    /**
     *
     *检验string类型值不为空
     *@author: Weiyf
     *Date: 2017/10/31 11:12
     */
    public static boolean isBlank(String var){
        return var == null || var == "";
    }
}
