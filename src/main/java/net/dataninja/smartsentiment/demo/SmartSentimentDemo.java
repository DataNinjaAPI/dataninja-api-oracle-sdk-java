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

package net.dataninja.smartsentiment.demo;

import net.dataninja.oracle.client.Input;
import net.dataninja.smartsentiment.client.SmartSentimentApi;
import net.dataninja.smartsentiment.client.SmartSentimentClient;

import java.io.Console;

/**
 * A simple command demo to show how to use the Smart Sentiment Java APIs
 */
public class SmartSentimentDemo {

    /**
     * Run through the Smart Sentiment APIs and show usage
     */
    public void process() {
        SmartSentimentApi client = new SmartSentimentClient().getClient();
        String input;

        Console cmdline = System.console();
        if (cmdline == null) {
            System.err.println("No console for command line demo.");
            System.exit(1);
        }

        // Find a Smart Data concept
        input = cmdline.readLine("Enter text to be analyzed by the Smart Sentiment Service: ");

        System.out.println(findSmartSentimentRdfDemo(client, input).toString());
    }

    // Service options can be con,cat,ee,ke,ks
    public Object findSmartSentimentDemo(SmartSentimentApi client, String data) {
        Input input = new Input();
        input.setMax_size(100);
        input.setText(data);
        return client.processSmartSentiment("ke,ee,ks", input);
    }

    public Object findSmartSentimentRdfDemo(SmartSentimentApi client, String data) {
        Input input = new Input();
        input.setMax_size(100);
        input.setText(data);

        // Get the response as string?
        // client --?
        // new String(((TypedByteArray) response.getBody()).getBytes());

        return client.processSmartSentimentRdf("ke,ee,ks", input);
    }

    public static void main(String[] args) {
        SmartSentimentDemo demo = new SmartSentimentDemo();
        demo.process();
    }
}
