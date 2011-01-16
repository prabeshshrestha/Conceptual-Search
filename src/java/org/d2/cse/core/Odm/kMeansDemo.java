package org.d2.cse.core.Odm;

import java.io.IOException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.Vector;
import oracle.dmt.odm.AttributeHistogram;
import oracle.dmt.odm.AttributeType;
import oracle.dmt.odm.AttributeUsage;
import oracle.dmt.odm.Category;
import oracle.dmt.odm.DataMiningServer;
import oracle.dmt.odm.DataPreparationStatus;
import oracle.dmt.odm.DataType;
import oracle.dmt.odm.DistanceFunction;
import oracle.dmt.odm.InvalidArgumentException;
import oracle.dmt.odm.LocationAccessData;
import oracle.dmt.odm.MiningObjectException;
import oracle.dmt.odm.MiningTaskState;
import oracle.dmt.odm.ODMException;
import oracle.dmt.odm.data.Attribute;
import oracle.dmt.odm.data.MiningAttribute;
import oracle.dmt.odm.data.NonTransactionalDataSpecification;
import oracle.dmt.odm.data.PhysicalDataSpecification;
import oracle.dmt.odm.model.Cluster;
import oracle.dmt.odm.model.ClusterCentroid;
import oracle.dmt.odm.model.ClusterCentroidEntry;
import oracle.dmt.odm.model.ClusteringModel;
import oracle.dmt.odm.model.MiningModel;
import oracle.dmt.odm.result.ApplySourceAttributeItem;
import oracle.dmt.odm.result.MiningApplyOutput;
import oracle.dmt.odm.result.MiningApplyResult;
import oracle.dmt.odm.rule.MiningRule;
import oracle.dmt.odm.rule.MiningRuleSet;
import oracle.dmt.odm.settings.algorithm.ClusteringAlgorithmSettings;
import oracle.dmt.odm.settings.algorithm.KMeansAlgorithmSettings;
import oracle.dmt.odm.settings.function.ClusteringFunctionSettings;
import oracle.dmt.odm.task.MiningApplyTask;
import oracle.dmt.odm.task.MiningBuildTask;
import oracle.dmt.odm.task.MiningTask;
import oracle.dmt.odm.task.MiningTaskStatus;

/**
 *
 * @author csearch
 */
public class kMeansDemo extends Object {

    private static DataMiningServer m_dms = null;
    private static oracle.dmt.odm.Connection m_dmsConn = null;
    //provide schema name that has the data tables/views
    private static final String DATA_SCHEMA_NAME = "SYSTEM";

    public static void main(String args[]) {
        try {
            //1. Login to the Data Mining Server
            //Create a DMS object
            m_dms = new DataMiningServer(
                    "jdbc:oracle:thin:@dhaubaji:1521:search", //JDBC URL jdbc:oracle:thin:@<Host name>:<Port>:<SID>
                    "sys as sysdba", //User Name
                    "password" //Password 
                    );
            //Login to the DMS and create a DMS Connection
            m_dmsConn = m_dms.login();
            //Remove the objects to avoid name conflicts
            cleanup();
            //2. Build a model
            buildModel();
            //2. Get clustering model details
            getModelDetails();
            //3. Apply the model
            applyModel();
            docidMapClustid();
            
        } catch (Exception anyExp) {
            anyExp.printStackTrace(System.out);
        } finally {
            try {
                //6. Logout from the Data Mining Server
                m_dms.logout(m_dmsConn);
            } catch (Exception anyExp1) {
            }//Ignore
        }
    }

    /**
     * To build a mining model, the build dataset location details and 
     * the mining function settings are required. 
     * In this example, we will build a clustering model using the 
     * build dataset "MINING_DATA_BUILD_V" in the user schema, with the 
     * kMeans algorithm.
     * After completing the build task, the model named "kMeans_Model" will 
     * be created in the DMS.
     */
    public static void buildModel() throws ODMException, SQLException, IOException {
        //1. Create PhysicalDataSpecification
        LocationAccessData lad = new LocationAccessData(
                "ORA_CLUSTER_1", //Build Table Name
                DATA_SCHEMA_NAME //Schema Name
                );
        PhysicalDataSpecification pds =
                new NonTransactionalDataSpecification(lad);
        //2. Create & Store Mining Function Settings
        ClusteringAlgorithmSettings clAlgo = new KMeansAlgorithmSettings(10, 0.01f, DistanceFunction.euclidean);
        ClusteringFunctionSettings mfs =
                ClusteringFunctionSettings.create(
                m_dmsConn, //DMS Connection
                clAlgo, //Clustering algorithm settings
                pds, //Build data specification
                DataPreparationStatus.unprepared, //data preparation status
                5, // number of clusters
                null,//optional list of attributes which should be interpreted as categorical
                null,//optional list of attributes which should be interpreted as numeric
                null //optional list of attributes which should be interpreted as having
                // integer data type
                );
        // all categorical attributes and CUST_ID should marked as inactive
        mfs.adjustAttributeUsage(new String[]{
            "DOC_ID"//, "PRABESH", "FRIENDS", "LARGE",
        // "BOY", "COUNTRY", "GOOD", "BAD"
        }, AttributeUsage.inactive);

        mfs.store(m_dmsConn, "kMeansDemo_MFS");
        //3. Create, store & execute Build Task
        MiningBuildTask buildTask = new MiningBuildTask(
                pds, //Build data specification
                "kMeansDemo_MFS", //Mining function settings name
                "kMeansDemo_Model" //Mining model name
                );
        executeTask(buildTask, "kMeansDemo_BuildTask");
    }

    /**
     * This method stores the given task with the specified name in the DMS 
     * and submits the task for asynchronous execution in the DMS. After 
     * completing the task successfully it returns true. If there is a task 
     * failure, then it prints error description and returns false.
     * 
     * @param taskObj task object
     * @param taskName name of the task
     * 
     * @return boolean returns true when the task is successful
     */
    public static boolean executeTask(MiningTask taskObj, String taskName)
            throws ODMException, SQLException {
        boolean isTaskSuccess = false;
        taskObj.store(m_dmsConn, taskName); //Store the task
        taskObj.execute(m_dmsConn); //Submit the task for asynchronous execution
        System.out.print(taskName + " is started, please wait. ");
        //Wait for completion of the task
        MiningTaskStatus taskStatus = taskObj.waitForCompletion(m_dmsConn);
        //Check the status of the task after completion
        isTaskSuccess = taskStatus.getTaskState().equals(MiningTaskState.success);
        if (isTaskSuccess) {
            //Task completed successfully
            System.out.println("It is successful.");
        } else {//Task failed
            throw new ODMException("It is failed.\nFailure Description: " +
                    taskStatus.getStateDescription());
        }
        return isTaskSuccess;
    }

    /**
     * To apply a mining model to a data, apply dataset location details, 
     * input model and the output table location details are required. 
     * In this example, we apply the kMeansDemo_Model to the "MINING_DATA_APPLY_V"  
     * dataset and produce probabilistic assignments for each record for the existing 
     * clusters.
     * After completing the apply task, OClusterDemo_ApplyResults object will be  
     * created in the DMS and an apply output table will be created at the  
     * specfied location.
     */
    public static void applyModel() throws ODMException, SQLException, IOException {
        //1. Create PhysicalDataSpecification
        LocationAccessData lad = new LocationAccessData(
                "ora_cluster_1", //Apply table/view Name
                DATA_SCHEMA_NAME //Schema Name
                );
        PhysicalDataSpecification pds =
                new NonTransactionalDataSpecification(lad);
        //2. Create MiningApplyOutput & apply output table/view details
        // Create MiningApplyOutput object
        MiningApplyOutput mao = MiningApplyOutput.createDefault();
        // Add all the source attributes to be returned with the scored result.
        // In this example, attribute "CUST_ID" from the source table will be 
        // returned with the result aliased as "CUST_ID"
        MiningAttribute sourceAttribute =
                new MiningAttribute("DOC_ID", DataType.intType,
                AttributeType.notApplicable);
        Attribute destinationAttribute = new Attribute("ATTR1", DataType.intType);

        ApplySourceAttributeItem applySourceAttributeItem =
                new ApplySourceAttributeItem(sourceAttribute, destinationAttribute);
        mao.addItem(applySourceAttributeItem);

        //Output table location details
        LocationAccessData outputTable =
                new LocationAccessData("kMeansDemo_ApplyOutput", DATA_SCHEMA_NAME);
        //3. Create, store & execute apply Task
        MiningApplyTask applyTask = new MiningApplyTask(
                pds, //test data specification
                "kMeansDemo_Model", //Input model name
                mao, //MiningApplyOutput object
                outputTable, //Apply output table location details
                "kMeansDemo_ApplyResults" //Apply results name
                );
        //Store & execute the task
        boolean isTaskSuccess = executeTask(applyTask, "kMeansDemo_ApplyTask");
        if (isTaskSuccess) {
            //Restore & display apply results
            MiningApplyResult applyResult =
                    MiningApplyResult.restore(m_dmsConn, "kMeansDemo_ApplyResults");
            System.out.println(applyResult.toString());
        }
    }

    /*
     * Restores the model and prints out the cluster information, rules 
     * and attribute histogram
     */
    public static void getModelDetails()
            throws SQLException, ODMException, InvalidArgumentException {
        // Restore model
        ClusteringModel clModel =
                (ClusteringModel) ClusteringModel.restore(m_dmsConn, "kMeansDemo_Model");
   
        printClusterHierarchy(clModel);

        // Get Leaf Clusters
        Vector vLeafClusters = clModel.getLeaves(m_dmsConn);

        // Display cluster information
        System.out.println("Leaf Cluster information:");
        someNumber=vLeafClusters.size();
        for (int na = 0; na < vLeafClusters.size(); na++) {
            Double[] numbers;
            String[] labelArray;
            Vector number = null;
            String oracleMappingQuery = "insert into docid_map_clustid(clusterId,parentId,levelNo,recordCount,labelName)values(";
            Cluster clCluster = (Cluster) vLeafClusters.elementAt(na);
            int clusterId = clCluster.getId();
            System.out.println("    Cluster : ");
            System.out.println("        ID: " + clusterId);
            oracleMappingQuery += clusterId + " ,";
            Cluster clParent = clCluster.getParent();
            System.out.println("        Parent: " +
                    (clParent != null ? (new Integer(clParent.getId())).toString() : "null"));
            oracleMappingQuery += (clParent != null ? (new Integer(clParent.getId())).toString() : "null") + " ,";
            System.out.println("        Level: " + clCluster.getLevel());
            oracleMappingQuery += clCluster.getLevel() + " ,";
            System.out.println("        RecordCount: " + clCluster.getRecordCount());
            oracleMappingQuery += clCluster.getRecordCount() + " ,";
            ClusterCentroid clCentroid = clCluster.getClusterCentroid();
            Vector vCentroidEntries = clCentroid.getEntries();
            System.out.println("        Centroid Values\n");
            numbers = new Double[vCentroidEntries.size()];
            labelArray = new String[vCentroidEntries.size()];
            for (int nj = 0; nj < vCentroidEntries.size(); nj++) {
                ClusterCentroidEntry clCentEntry = (ClusterCentroidEntry) vCentroidEntries.elementAt(nj);
                Category value = clCentEntry.getValue();
                System.out.print("            " + clCentEntry.getAttribute().getName());
                System.out.print(" : " + value.getDisplayName() + "\n");
                StringTokenizer st = new StringTokenizer(value.getDisplayName(), "-");
                Double val = 1.0;
                //System.out.println(" after: "+((Double.parseDouble(st.nextToken()))+val));
                numbers[nj] = Double.parseDouble(st.nextToken());
                labelArray[nj] = clCentEntry.getAttribute().getName();
            }
            /*  for(int va=0;va<vCentroidEntries.size();va++){
            //System.out.println("hello: "+numbers[va]);
            }*/
            //===================================================== max

            Double maximum = numbers[0];   // start with the first value
            int index = 1;
            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] > maximum) {
                    maximum = numbers[i];   // new maximum
                    index = i;                //index of maximum to find the relative word
                }
            }
            //return maximum;
            System.out.println("Maximum value is: " + maximum);
            System.out.println("And the label is:" + labelArray[index]);
            oracleMappingQuery += "'"+labelArray[index] + "' )";
            System.out.println(oracleMappingQuery);
            try {
                Connection con = null;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@dhaubaji:1521:search", "system", "password");
                Statement st = (Statement) con.createStatement();
                st.execute(oracleMappingQuery);
                st.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("   Print rules for leaf clusters:");
        MiningRuleSet ruleSet = clModel.getClusterRules(m_dmsConn);
      //  printRules(ruleSet, clModel.getLeafClusterIDs(m_dmsConn));

        System.out.println("\n   Get Attribute Histogram for the doc_id attribute");
        Cluster cluster = (Cluster) vLeafClusters.elementAt(vLeafClusters.size() - 1);
        System.out.println("     Cluster ID: " + cluster.getId());
        System.out.println("     Attribute Name: " + "DOC_ID");

        AttributeHistogram aHistogram = clModel.getAttributeHistogram(
                m_dmsConn, cluster.getId(), "DOC_ID");
        int numberOfBeans = aHistogram.getNumberOfBins();
        System.out.println("      BIN\tDISPLAY NAME\tFREQUENCY");
        for (int i = 1; i <= numberOfBeans; i++) {
            System.out.println("      " + i + "   " + aHistogram.getDisplayName(i) + "    " + aHistogram.getFrequency(i));
        }
    }

    /**
     * Prints out rules in the given MiningRuleSet for the leaf clusters.
     */
    private static void printRules(MiningRuleSet ruleSet, int[] arLeafIds)
            throws SQLException, MiningObjectException, ODMException {
        MiningRule[] rules = ruleSet.getRules();
        for (int ni = 0; ni < rules.length; ni++) {
            int ruleID = rules[ni].getRuleId();
            for (int nr = 0; nr < arLeafIds.length; nr++) {
                if (ruleID == arLeafIds[nr]) {
                    System.out.println("\nRule [" + (ni + 1) + "]: " + rules[ni].toString());
                    break;
                }
            }
        }
        ruleSet.close();
    }
    private static int indentLevel;
    private static String SPACE = "\t";
    private static int someNumber=0;

    /**
     * Prints out cluster hierarchy.
     */
    private static void printClusterHierarchy(ClusteringModel clModel)
            throws SQLException, InvalidArgumentException, ODMException {
        Vector vClusters = clModel.getClusters(m_dmsConn);
        Cluster rootCluster = (Cluster) vClusters.elementAt(0);
        System.out.println("Cluster : " + rootCluster.getId());
        traverseTree(rootCluster);
    }

    /**
     * Recursively traverses the cluster hierarchy
     */
    private static void traverseTree(Cluster clParent) {
        indentLevel++;
        Vector vChildClusters = clParent.getChildren();
        if (vChildClusters == null || vChildClusters.isEmpty()) {
            indentLevel--;
            return;
        }
        StringBuffer indent = new StringBuffer();
        for (int ni = 0; ni < indentLevel; ni++) {
            indent.append(SPACE);
        }

        for (int nc = 0; nc < vChildClusters.size(); nc++) {
            Cluster childCluster = (Cluster) vChildClusters.elementAt(nc);

            System.out.println(indent.toString() + "Cluster : " + childCluster.getId());
            traverseTree(childCluster);
        }
        indentLevel--;
    }

    /**
     * Cleanup the previously created objects if any. 
     */
    public static void cleanup() throws ODMException, SQLException {
        //Remove the MiningFunctionSettings
        try {
            ClusteringFunctionSettings.remove(m_dmsConn, "kMeansDemo_MFS");
        } catch (Exception anyExp1) {
        }//Ignore
        //Remove the MiningBuildTask
        try {
            MiningBuildTask.remove(m_dmsConn, "kMeansDemo_BuildTask");
        } catch (Exception anyExp2) {
        }//Ignore
        //Remove the MiningModel
        try {
            MiningModel.remove(m_dmsConn, "kMeansDemo_Model");
        } catch (Exception anyExp3) {
        }//Ignore
        //Remove the MiningApplyTask
        try {
            MiningApplyTask.remove(m_dmsConn, "kMeansDemo_ApplyTask");
        } catch (Exception anyExp8) {
        }//Ignore
        //Remove the MiningApplyResult and apply output table
        try {
            MiningApplyResult.remove(m_dmsConn, "kMeansDemo_ApplyResults", true);
        } catch (Exception anyExp9) {
        }//Ignore    
    }
    public static void docidMapClustid(){
   /*     //String mappingQuery="select mao_prediction from kMeansDemo_ApplyOutput where attr1=1";
       String mappingQuery="select attr1 from kMeansDemo_ApplyOutput where mao_prediction='";
       String updateQuery="UPDATE docid_map_clustid SET docId ='";// 1 WHERE clusterId = 9";
       ResultSet rs = null;
       String docids;
        for(int loopCount=0;loopCount<someNumber;loopCount++){
            mappingQuery+=(loopCount+1);
            mappingQuery+="'";
        try {
                Connection con = null;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@dhaubaji:1521:search", "system", "password");
                Statement st = (Statement) con.createStatement();
                //st.execute(mappingQuery);
                rs = (ResultSet) st.executeQuery(mappingQuery);
            while (rs.next()) {
               // docids = rs.getInt("attr1")+"-";
                updateQuery+=rs.getInt("attr1")+"-";
            }
             //updateQuery+="docids"; 
                updateQuery+="'"+" where clusterId='"+(loopCount+1);
             System.out.println("Update Query: "+updateQuery);
                st.close();
                con.close();
            } catch (Exception e) {
                System.out.println("Mao Prediction Does not exist");
                e.printStackTrace();
            }
    }*/
        //String mappingQuery="select attr1,mao_prediction from kMeansDemo_ApplyOutput";
        String mappingQuery="select mao_prediction from kMeansDemo_ApplyOutput";
        String selectdocid="select attr1 from kMeansDemo_ApplyOutput where mao_prediction='";
        
        try {
                String docids="";
                int clusterid;
                String updateQuery1="UPDATE docid_map_clustid SET docId ='";
                Connection con = null;
                ResultSet rs = null;
                String updateQuery="";
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@dhaubaji:1521:search", "system", "password");
                Statement st = (Statement) con.createStatement();
                //st.execute(mappingQuery);
                rs = (ResultSet) st.executeQuery(mappingQuery);
            while (rs.next()) {
                getdocids(rs.getInt("mao_prediction"));
               //System.out.println("Docid:"+rs.getInt("attr1")+" ClusterId:"+rs.getInt("mao_prediction"));
            }
                st.close();
                con.close();
            } catch (Exception e) {
                System.out.println("Mao Prediction Does not exist");
                e.printStackTrace();
            }
    }
    public static void getdocids(int clustid){
        System.out.println("ClusterId:"+clustid);
                try {
                    String docidharu="";
                String getdocs="select attr1 from kMeansDemo_ApplyOutput where mao_prediction='"+clustid+"'";
                Connection conn = null;
                ResultSet res = null;
                String updateQuery="";
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@dhaubaji:1521:search", "system", "password");
                Statement sts = (Statement) conn.createStatement();
                //st.execute(mappingQuery);
                res = (ResultSet) sts.executeQuery(getdocs);
            while (res.next()) {
                docidharu+=res.getInt("attr1")+"-";
               //System.out.println("Docid:"+rs.getInt("attr1")+" ClusterId:"+rs.getInt("mao_prediction"));
            }
                System.out.println("Doc ids:"+docidharu+"for cluster id: " +clustid);
                updatedocids(clustid, docidharu);
                sts.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Mao Prediction Does not exist");
                e.printStackTrace();
            }
    }
    public static void updatedocids(int clustid,String docidharu){
                        try {
                String updatedocids="UPDATE docid_map_clustid SET docId ='"+docidharu+"'"+" WHERE clusterId ='"+clustid+"'";
                Connection uconn = null;
                ResultSet ures = null;

                Class.forName("oracle.jdbc.driver.OracleDriver");
                uconn = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@dhaubaji:1521:search", "system", "password");
                Statement usts = (Statement) uconn.createStatement();
                //st.execute(mappingQuery);
                usts.executeUpdate(updatedocids);
                usts.close();
                uconn.close();
            } catch (Exception e) {
                System.out.println("Mao Prediction Does not exist");
                e.printStackTrace();
            }
    }
}
