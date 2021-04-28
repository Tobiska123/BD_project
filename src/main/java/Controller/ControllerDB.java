package Controller;

import Model.ModelDB;
import Model.Pair;
import Model.Queries.AbstractQuery;
import View.DB_viewer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class ControllerDB {

    private DB_viewer viewer;
    private ModelDB model;

    public ControllerDB(){
        this.viewer = new DB_viewer(this);
        this.model = new ModelDB(this);


//        this.viewer.setActionListenerLoginUI(e -> {
//
//        });
//
//        this.viewer.setActionListenerMainUIQuery(e -> {
//            this.viewer.startEditFrame();
//        });
    }

    public void loginButtonAction()throws SQLException {
            this.model.setUsername(this.viewer.getLoginUI().getTextUserTextField());
            this.model.setPassword(this.viewer.getLoginUI().getTextPasswordTextField());
            this.model.setURL(this.viewer.getLoginUI().getURLTextField());

            this.model.makeConnectionDB();
    }

    public Pair<Vector<Vector<String>>, Vector<String>> userRequestExec(Pair<String, Map<String, String>> query)throws SQLException{
         return this.model.execUserQuery(query);
    }

    public void editButtonAction()throws SQLException{
        List<String> listOfTables = this.model.getTables();
        Map<String, Vector<String>> queries = this.model.getQueries();
        this.viewer.setSelectableTables(listOfTables);
        this.viewer.setQueries(queries);
        this.viewer.addListenerControlPane();
    }

    public Pair<Vector<Vector<String>>, Vector<String>> selectTableData(String tableData)throws SQLException{
            return this.model.getDataOfSelectTable(tableData);
    }

    public Pair<Vector<Vector<String>>, Vector<String>> addRowToModel(String tableName, Vector<String> headers, Vector<String> curRowData)throws SQLException{
        return this.model.addDataToTable(tableName, headers, curRowData);
    }

    public Pair<Vector<Vector<String>>, Vector<String>> deleteRowFromModel(String tableName, Vector<String> headers, Vector<String> curRowData)throws SQLException{
        return this.model.deleteRowFromTable(tableName, headers, curRowData);
    }

    public Pair<Vector<Vector<String>>, Vector<String>> updateModel(String tableName, Vector<String> headers, Vector<String> curRowData)throws SQLException{
        return this.model.updateDataToTable(tableName, headers, curRowData);
    }

    public Pair<Vector<Vector<String>>, Vector<String>> someQuery(String queryText)throws SQLException{
        return this.model.executeSomeQuery(queryText);
    }

    public boolean refreshConnetctionModel()throws SQLException{
        return this.model.refreshConnectionDB();
    }

    public void loginOut()throws SQLException{
        this.model.loginOut();
    }


    public void testControl(){
        try {
            final String tableName = "BOOK_LIT";
            this.model.makeConnectionDB();
//            Pair<Vector<Vector<String>>,Vector<String>> pair = this.model.getDataOfSelectTable(tableName);
//            System.out.print(pair.getFirst());
//
//            Vector<String> Ah = new Vector<String>(pair.getSecond());
//            Vector<String> Ad = new Vector<String>();
//            Ad.addElement("1");
//            Ad.addElement("24");
//            Ad.addElement("4");
//
//            Pair<Vector<Vector<String>>,Vector<String>> pairTwo = this.model.addDataToTable(tableName, Ah, Ad);
//            System.out.print(pairTwo.getFirst());
            this.model.getPrimaryKeysTable(tableName);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }






}
