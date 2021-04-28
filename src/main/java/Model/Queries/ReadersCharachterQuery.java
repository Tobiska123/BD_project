package Model.Queries;

import java.util.*;

public class ReadersCharachterQuery extends AbstractQuery{
    String queryName = "readersCheck";//FIX!!!
    //Map<String, String> params;

    public ReadersCharachterQuery(){
        params = new HashMap<>();
        params.put("FIRST_NAME", null);
        params.put("SECOND_NAME", null);
        params.put("LAST_NAME", null);
        params.put("ADDRESS", null);
        params.put("LAST_VISITED", null);
        params.put("BIRTHDAY", null);
    }

    @Override
    public String generateQuery() {
        StringBuilder query = new StringBuilder("SELECT * FROM READERS WHEN (");
        for(Map.Entry<String, String> iter_param: this.params.entrySet()) {
            String value = iter_param.getValue();
            if (!value.equals(""))
                query.append(iter_param.getKey() + "=" + iter_param.getValue());
        }
        return new String(query);
    }

    @Override
    public String getNameQuery() {
        return this.queryName;
    }

    @Override
    public Vector<String> getParamsName() {
        return new Vector<>(params.keySet());
    }

}
