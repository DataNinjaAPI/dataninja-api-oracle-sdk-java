/*
 * Copyright 2015 DOCOMO Innovations, Inc.
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

import net.dataninja.oracle.client.OracleDataNinjaClient;
import net.dataninja.oracle.client.SimpleDataNinjaClient;

import java.io.Console;
import java.sql.SQLException;

public class OracleDataNinjaDemo {

    private static SimpleDataNinjaClient dnClient = new SimpleDataNinjaClient();
    private static OracleDataNinjaClient oraClient = new OracleDataNinjaClient();

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

        // Find a Smart Data concept
        input = cmdline.readLine("Enter text to analyze and extract RDF: ");

        String rdfData = fetchRdf(input);
        System.out.println(rdfData);

        storeRdf(rdfData);
    }

    public void init() {
        oraClient.init();
    }

    public String fetchRdf(String data) {
        return dnClient.fetchSmartSentimentRdf(data, "http://dataninja.net", 100);
    }

    public void storeRdf(String rdfData) {
        try {
            oraClient.insertRdf(rdfData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        OracleDataNinjaDemo demo = new OracleDataNinjaDemo();
        // demo.init();
        demo.process();
    }
}
