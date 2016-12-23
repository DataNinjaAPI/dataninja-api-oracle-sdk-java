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

package net.dataninja.oracle.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

// This simple client uses the Apache HTTP Client to connect to Smart Sentiment service
// Retrofit has problems parsing RDF content returned by Smart Sentiment
public class DataNinjaHttpClient {

    private static final DataNinjaOracleConfig config = new DataNinjaOracleConfig();

    private static HttpClient httpClient = null;

    public String fetchSmartSentimentRdf(String text, String url, int maxSize) {
        String output = "";
        try {

            HttpClient client = getHttpClient();

            HttpPost postRequest = new HttpPost(config.getApiUrl() + "/smartsentiment/tagentityrdf");

            DataNinjaInput dataNinjaInput = new DataNinjaInput();
            dataNinjaInput.setText(text);
            dataNinjaInput.setUrl(url);
            dataNinjaInput.setMax_size(maxSize);
            System.out.println(dataNinjaInput.toJsonString());

            StringEntity entity = new StringEntity(dataNinjaInput.toJsonString());
            entity.setContentType("application/json");
            postRequest.setEntity(entity);

            // Add additional header to postRequest which accepts application/json data
            postRequest.addHeader("accept", "application/json");
            postRequest.addHeader("X-Mashape-Key", config.getMashapeKey());

            System.out.println(postRequest.toString());

            // Execute the POST request and catch response
            HttpResponse response = client.execute(postRequest);

            // Check for HTTP response code: 200 = success
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.printf(response.toString());
                throw new RuntimeException("Failed : HTTP error code : " +
                        response.getStatusLine().getStatusCode());
            }

            // Get-Capture Complete the RDF body response
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            StringBuffer sb = new StringBuffer();
            String line;

            // Simply iterate through RDF response and show on console.
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
                // System.out.println(line);
            }
            output = sb.toString();

        } catch (ClientProtocolException e) {
            System.out.println("Problem fetching RDF content");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Problem fetching RDF content");
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            System.out.println("Problem fetching RDF content");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Problem fetching RDF content");
            e.printStackTrace();
        } catch (KeyStoreException e) {
            System.out.println("Problem fetching RDF content");
            e.printStackTrace();
        } catch (KeyManagementException e) {
            System.out.println("Problem fetching RDF content");
            e.printStackTrace();
        }
        return output;
    }

    private HttpClient getHttpClient() throws NoSuchAlgorithmException, KeyManagementException,
                                              KeyStoreException, UnrecoverableKeyException {
        // Keep only one copy of the HttpClient
        if (httpClient != null) {
            return httpClient;
        }

        // Create a new instance of HTTPClient
        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] cert, String authType) throws CertificateException {
                return true;
            }
        };
        SSLSocketFactory factory = new SSLSocketFactory(acceptingTrustStrategy,
                SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", 443, factory));
        ClientConnectionManager ccm = new PoolingClientConnectionManager(registry);

        httpClient = new DefaultHttpClient(ccm);
        return httpClient;
    }

    public static void main(String[] args) {
        DataNinjaHttpClient client = new DataNinjaHttpClient();
        String output = client.fetchSmartSentimentRdf("Barack Obama", "http://www.cnn.com", 100);
        System.out.println("============RDF Output:============");
        System.out.println(output);
    }
}
