package Model;

import Controller.ControllerDB;
import Model.Queries.AbstractQuery;
import Model.Queries.ReadersCharachterQuery;

import java.sql.*;
import java.util.*;

public class ModelDB{
    private ControllerDB controllerDB;

    private String username = "18209_Levchenko";
    private String password = "1111";
    private String url = "jdbc:oracle:thin:@84.237.50.81:1521:xe";
    private DbConnectionHandler dbConnectionHandler;
    private Pair<Vector<Vector<String>>, Vector<String>> resultLastQuery;
    private boolean lastQueryIsExistTable = false;
    private Connection connection;
    private List<AbstractQuery> listOfQueries;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setURL(String url){
        this.url = url;
    }

    public ModelDB(ControllerDB controllerDB){
        this.controllerDB = controllerDB;
        this.listOfQueries = new LinkedList<>();
        this.listOfQueries.add(new ReadersCharachterQuery());
    }

    public AbstractQuery findQuery(String nameQuery){
        for(AbstractQuery iter_query: listOfQueries){
            if(iter_query.getNameQuery().equals(nameQuery))
                return iter_query;
        }
        return null;
    }

    public Map<String, Vector<String>> getQueries(){
        Map<String, Vector<String>> tmpMap = new HashMap<>();
        for(AbstractQuery iter_query: listOfQueries){
            tmpMap.put(iter_query.getNameQuery(), iter_query.getParamsName());
        }
        return tmpMap;
    }

    public void makeConnectionDB() throws SQLException{
        DbConnectionHandler dbConnectionHandler = new DbConnectionHandler(this.url,
                this.username, this.password);
        this.connection = dbConnectionHandler.getNewConnection();
    }

    public boolean refreshConnectionDB() throws SQLException{
        if(!this.connection.isValid(2)){
            this.connection = dbConnectionHandler.getNewConnection();
            return true;
        }
        return false;
    }

    public Pair<Vector<Vector<String>>, Vector<String>> execUserQuery(Pair<String, Map<String, String>> query)throws SQLException{
        AbstractQuery selectedQuery = findQuery(query.getFirst());
        selectedQuery.setParams(query.getSecond());
        return executeSomeQuery(selectedQuery.generateQuery());
    }

    public void loginOut()throws SQLException{
        this.connection.close();
    }

    public Vector<String> getTables() throws SQLException{
        DatabaseMetaData metaData = this.connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, metaData.getUserName(), "%", null);
        Vector<String> tables = new Vector<>();
        while(resultSet.next()){
            tables.add(resultSet.getString(3));
        }
        return tables;
    }

    public Pair<Vector<Vector<String>>,Vector<String>> executeSomeQuery(String textQuery)throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement(textQuery);
        if(!statement.execute())
            throw new SQLException("Empty result");
        ResultSet resultSet = statement.getResultSet();
        this.lastQueryIsExistTable = false;
        return rSetToStr(resultSet);
    }

    private String reprInsertValue(Vector<String> vector, String tableName)throws SQLException{
        ResultSet typesTable = this.connection.getMetaData().getColumns(null, this.connection.getMetaData().getUserName(), tableName, "%");
        typesTable.next();
        StringBuilder temp = new StringBuilder("(");
        String prefix = "";
        for(String iter_vector : vector){
            temp.append(prefix);
            prefix = ", ";
            String type = typesTable.getString(6);
            if(type.equals("NUMBER"))
                temp.append(iter_vector);
            if(type.equals("VARCHAR2") || type.equals("VARCHAR"))
                temp.append("'" + iter_vector + "'");
            typesTable.next();
        }
        temp.append(")");
        return new String(temp);
    }

    private String reprInsertCols(Vector<String> vector){
        StringBuilder temp = new StringBuilder("(");
        String prefix = "";
        for(String iter_vector : vector){
            temp.append(prefix);
            prefix = ", ";
            temp.append(iter_vector);
        }
        temp.append(")");
        return new String(temp);
    }
    
    public Pair<Vector<Vector<String>>, Vector<String>> addDataToTable(String tableName, Vector<String> insertingDataIntoCurTable, Vector<String> headersCurTable)throws SQLException{
        if(!this.lastQueryIsExistTable) throw new SQLException("is the Select Query");
        String query = "INSERT INTO " + tableName + reprInsertCols(headersCurTable) + " VALUES " + reprInsertValue(insertingDataIntoCurTable, tableName);
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(query);
        return getDataOfSelectTable(tableName);
    }

    public Pair<Vector<Vector<String>>, Vector<String>> updateDataToTable(String tableName, Vector<String> updatingDataIntoCurTable, Vector<String> headersCurTable)throws SQLException{
        if(!this.lastQueryIsExistTable) throw new SQLException("is the Select Query");

        String query = "UPDATE " + tableName + " SET " + shapingSetUpdate(headersCurTable, headersCurTable, updatingDataIntoCurTable, tableName) + " WHERE " + shapingConditionDeleting(this.getPrimaryKeysTable(tableName), headersCurTable, updatingDataIntoCurTable);
        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.execute();
        return getDataOfSelectTable(tableName);
    }

    public Vector<String> getPrimaryKeysTable(String tableName)throws SQLException{
        DatabaseMetaData metaData = this.connection.getMetaData();
        ResultSet primaryKeysOfTable = metaData.getPrimaryKeys(null, metaData.getUserName(), tableName.toUpperCase());
        Vector<String> resultKeys = new Vector<>();
        while(primaryKeysOfTable.next())
            resultKeys.add(primaryKeysOfTable.getString(4));
        return resultKeys;
    }

    public String shapingSetUpdate(Vector<String> fieldsCond, Vector<String> headers, Vector<String> tableData, String tableName)throws SQLException{
        ResultSet typesTable = this.connection.getMetaData().getColumns(null, this.connection.getMetaData().getUserName(), tableName, "%");
        typesTable.next();
        StringBuilder tempCond = new StringBuilder();
        String type, prefix = "";
        for(String iter_field: fieldsCond){
            tempCond.append(prefix);
            prefix = ", ";
            type = typesTable.getString(6);
            if(type.equals("NUMBER") || type.equals("CHAR"))//Унифицировать представление типов данных
                tempCond.append(iter_field + "=" + tableData.elementAt(headers.indexOf(iter_field)));
            if(type.contains("VARCHAR"))
                tempCond.append(iter_field + "=" + "'" + tableData.elementAt(headers.indexOf(iter_field)) + "'");
            typesTable.next();
        }
        return new String(tempCond);
    }

    public String shapingConditionDeleting(Vector<String> fieldsCond, Vector<String> headers, Vector<String> tableData){
        StringBuilder tempCond = new StringBuilder("(");
        String prefix = "";
        for(String iter_field: fieldsCond){
            tempCond.append(prefix);
            prefix = " AND ";
            tempCond.append(iter_field + "=" + tableData.elementAt(headers.indexOf(iter_field)));
        }
        tempCond.append(")");
        return new String(tempCond);
    }

    public Pair<Vector<Vector<String>>,Vector<String>> deleteRowFromTable(String tableName, Vector<String> headers, Vector<String> tableCurData)throws SQLException{
        if(!this.lastQueryIsExistTable) throw new SQLException("is the Select Query");
        String conditionForQuery = shapingConditionDeleting(this.getPrimaryKeysTable(tableName), headers, tableCurData);
        String query = "DELETE FROM " + tableName + " WHERE " + conditionForQuery;
        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.execute();
        return getDataOfSelectTable(tableName);
    }

    public Pair<Vector<Vector<String>>,Vector<String>> getDataOfSelectTable(String tableName)throws SQLException{
        String query = "SELECT * FROM " + tableName;
        PreparedStatement statement = this.connection.prepareStatement(query);
        if(!statement.execute())
            throw new SQLException("Has no results " + tableName);
        ResultSet resultSet = statement.getResultSet();
        this.resultLastQuery = rSetToStr(resultSet);
        this.lastQueryIsExistTable = true;
        return this.resultLastQuery;
    }

    public Pair<Vector<Vector<String>>,Vector<String>> rSetToStr(ResultSet resultSet)throws SQLException{
        resultSet.getMetaData();
        Vector<Vector<String>> tempData = new Vector<>();
        Vector<String> IO = this.collectHeaders(resultSet);
        while(resultSet.next()){
            Vector<String> tmp = new Vector<>();
            for(int i = 1; i <= IO.size(); i++)
                tmp.add(resultSet.getString(i));
            tempData.add(tmp);
        }
        return new Pair<Vector<Vector<String>>,Vector<String>>(tempData, IO);
    }

    public Vector<String> collectHeaders(ResultSet resultSet)throws SQLException{
        Vector<String> tempHeaders = new Vector<>();
        ResultSetMetaData metaDataQuery = resultSet.getMetaData();
        int count = metaDataQuery.getColumnCount();
        for(int i = 1; i <= count; i++){
            tempHeaders.add(metaDataQuery.getColumnLabel(i));
        }
        return tempHeaders;
    }
}
