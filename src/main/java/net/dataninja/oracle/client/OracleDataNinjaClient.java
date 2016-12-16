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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

// import com.hp.hpl.jena.query.*;
// import com.hp.hpl.jena.rdf.model.Model;
// import com.hp.hpl.jena.util.FileManager;
import oracle.spatial.rdf.client.jena.*;

// java -cp ./classes/:../jar/'*' DataNinjaLoader \
// jdbc:oracle:thin:@127.0.0.1:1521:orcl scott tiger extraction $RDFFILE

public class OracleDataNinjaClient {

    private static final OracleDataNinjaConfig config = new OracleDataNinjaConfig();
    private Oracle oracle;
    private DatasetGraphOracleSem dataset;

    public OracleDataNinjaClient() {
        this.init();
    }

    private Oracle getOracle(String szJdbcURL,
                             String szUser,
                             String szPasswd) {
        return new Oracle(szJdbcURL, szUser, szPasswd);
    }

    private DatasetGraphOracleSem getDataset(Oracle oracle,
                                             String szModelName) throws SQLException {
        GraphOracleSem graph = new GraphOracleSem(oracle, szModelName);
        DatasetGraphOracleSem dataset = DatasetGraphOracleSem.createFrom(graph);

        // Don't need graph anymore, close it to free resources
        graph.close();
        return dataset;
    }

    public void init() {
        try {
            this.oracle = getOracle(config.getJdbcURL(), config.getDbUser(), config.getDbPasswd());
            this.dataset = getDataset(oracle, config.getDbModelName());
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void finish() throws SQLException {
        this.oracle.dispose();
    }

    // TODO: Use String input stream instead
    public void insertRdf(String rdfText)
            throws SQLException {
        try {
            InputStream rdfStream = new ByteArrayInputStream(rdfText.getBytes(StandardCharsets.UTF_8));
            // InputStream is = new FileInputStream(szFileName);
            // load NQUADS file into a staging table. This file can be gzipp'ed.
            dataset.prepareBulk(
                    rdfStream,                  // input stream
                    "http://dataninja.net",  // base URI
                    "N-QUADS",              // data file type; can be "TRIG"
                    "SEMTS",                // tablespace
                    null,                   // flags
                    null,          // listener
                    null,                   // staging table name
                    true                     // truncate staging table before load
            );
            // Load quads from staging table into the dataset
            dataset.completeBulk(
                    null, // flags; can be "PARSE PARALLEL_CREATE_INDEX PARALLEL=4
                    // mbv_method=shadow" on a quad core machine
                    null  // staging table name
            );
        } catch (Throwable t) {
            System.out.println("Hit exception " + t.getMessage());
        } finally {
            dataset.close();
            oracle.dispose();
        }
    }

    public static void main(String[] args) throws Exception {
        OracleDataNinjaClient oraClient = new OracleDataNinjaClient();
        oraClient.init();
        oraClient.insertRdf("Brack Obama");
        oraClient.finish();
    }
}
