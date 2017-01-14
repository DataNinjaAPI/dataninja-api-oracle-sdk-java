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

import com.google.gson.stream.JsonReader;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Holds configuration information related to the Data Ninja services and Oracle connectivity.
 */
public class DataNinjaOracleConfig {

    private String apiUrl;
    private String mashapeKey;
    private String logFile;
    private String logLevel;
    private String jdbcURL;
    private String dbUser;
    private String dbPasswd;
    private String dbModelName;

    private String fileType="N-QUADS";
    private String baseUri="http://dataninja.net";

    // Performance parameters
    private String tablespace="SEMTS";
    private String flags=null;
    // private String listener=null;
    private String stagingTable=null;
    private boolean truncateStagingTable=true;
    private boolean bulkLoad=false;
    private String bulkFlags="PARSE PARALLEL_CREATE_INDEX PARALLEL=4";
    private String mbvMethod="shadow";

    public DataNinjaOracleConfig() {
        // Use Yaml like config to read the conf file
        Config conf = ConfigFactory.load("dataninja.conf");
        // System.out.println("Loaded default config; data Ninja client version: " +
        //        conf.getString("dataninja.client-version"));
        init(conf);
    }

    public DataNinjaOracleConfig(String fileName) {
        Config conf = ConfigFactory.parseFile(new java.io.File(fileName));
        // System.out.println("Loaded config from " + fileName + "; data Ninja client version: " +
        //        conf.getString("dataninja.client-version"));
        init(conf);
    }

    private void init(Config conf) {
        this.mashapeKey = conf.getString("dataninja.mashape.key");
        this.apiUrl = conf.getString("smartsentiment.service-url");

        // Logging config
        this.logFile = conf.getString("logging.log-file");
        this.logLevel = conf.getString("logging.log-level");

        // Oracle config
        this.jdbcURL = conf.getString("oracle.jdbcURL");
        this.dbUser = conf.getString("oracle.dbUser");
        this.dbPasswd = conf.getString("oracle.dbPasswd");
        this.dbModelName = conf.getString("oracle.dbModelName");
        this.fileType=conf.getString("oracle.jdbcURL");
        this.baseUri=conf.getString("oracle.jdbcURL");

        // Performance parameters
        this.tablespace=conf.getString("oracle.tablespace");
        this.flags=conf.getString("oracle.flags");
        // this.listener=conf.getString("oracle.jdbcURL");
        this.stagingTable=conf.getString("oracle.stagingTable");
        this.truncateStagingTable=conf.getBoolean("oracle.truncateStagingTable");
        this.bulkLoad=conf.getBoolean("oracle.bulkLoad");
        this.bulkFlags=conf.getString("oracle.bulkFlags");
        this.mbvMethod=conf.getString("oracle.mbvMethod");
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

    public String getFileType() {
        return fileType;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public String getTablespace() {
        return tablespace;
    }

    public String getFlags() {
        return flags;
    }

//    public String getListener() {
//        return listener;
//    }

    public String getStagingTable() {
        return stagingTable;
    }

    public boolean isTruncateStagingTable() {
        return truncateStagingTable;
    }

    public boolean isBulkLoad() {
        return bulkLoad;
    }

    public String getBulkFlags() {
        return bulkFlags;
    }

    public String getMbvMethod() {
        return mbvMethod;
    }

    public static void main(String[] args) {
    }
}
