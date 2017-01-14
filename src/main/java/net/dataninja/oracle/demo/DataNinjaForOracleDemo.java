/*
 * Copyright 2017 DOCOMO Innovations, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * See the License for the specific language governing permissions and limitations under the License.
 */

package net.dataninja.oracle.demo;

import net.dataninja.oracle.client.DataNinjaClientForOracle;
import net.dataninja.oracle.client.DataNinjaHttpClient;

import java.io.Console;
import java.sql.SQLException;

public class DataNinjaForOracleDemo {

    private DataNinjaHttpClient dnClient = null;
    private DataNinjaClientForOracle oraClient = null;

    public DataNinjaForOracleDemo() {
        dnClient = new DataNinjaHttpClient();
        try {
            oraClient = new DataNinjaClientForOracle();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DataNinjaForOracleDemo(String configFile) {
        dnClient = new DataNinjaHttpClient(configFile);
        try {
            oraClient = new DataNinjaClientForOracle(configFile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run through the Smart Sentiment APIs and show usage
     */
    public void process() {
        String input;

        Console cmdline = System.console();
        if (cmdline == null) {
            System.err.println("No console for command line demo.");
            System.exit(1);
        }

        while (true) {
            // Find a Smart Data concept
            input = cmdline.readLine("Enter text to analyze and extract RDF: ");

            String rdfData = fetchRdf(input);
            System.out.println(rdfData);

            storeRdf(rdfData);
        }
    }

    public void init() throws SQLException {
        oraClient.init();
    }

    public String fetchRdf(String data) {
        return dnClient.fetchSmartSentimentRdf(data, "http://dataninja.net", 100);
    }

    public void storeRdf(String rdfData) {
        if (oraClient == null) {
            System.out.println("Oracle client is not available, please check your config.");
        }
        try {
            oraClient.insertRdf(rdfData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        DataNinjaForOracleDemo demo;
        if (args.length >= 1) {
            System.out.println("Using config file " + args[0]);
            demo = new DataNinjaForOracleDemo(args[0]);
        } else {
            System.out.println("Warning: using default configuration from client classpath.");
            demo = new DataNinjaForOracleDemo();
        }

        demo.process();
    }
}
