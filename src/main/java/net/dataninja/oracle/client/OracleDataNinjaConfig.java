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

package net.dataninja.oracle.client;

import com.google.gson.stream.JsonReader;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Holds configuration information related to the Smart Sentiment services
 */
public class OracleDataNinjaConfig {

    private String apiUrl;
    private String mashapeKey;
    private String logFile;
    private String logLevel;
    private String jdbcURL;
    private String dbUser;
    private String dbPasswd;
    private String dbModelName;

    public OracleDataNinjaConfig() {

        // Use Yaml like config to read the conf file
        Config conf = ConfigFactory.load("dataninja.conf");
        this.mashapeKey = conf.getString("dataninja.mashape-key");
        this.apiUrl = conf.getString("smartsentiment.service-url");

        // Logging config
        this.logFile = conf.getString("logging.log-file");
        this.logLevel = conf.getString("logging.log-level");

        // Oracle config
        this.jdbcURL = conf.getString("oracle.jdbcURL");
        this.dbUser = conf.getString("oracle.dbUser");
        this.dbPasswd = conf.getString("oracle.dbPasswd");
        this.dbModelName = conf.getString("oracle.dbModelName");
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getMashapeKey() {
        return mashapeKey;
    }

    public String getLogFile() {
        return logFile;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public String getJdbcURL() {
        return jdbcURL;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPasswd() {
        return dbPasswd;
    }

    public String getDbModelName() {
        return dbModelName;
    }

    public static void main(String[] args) {
    }
}
