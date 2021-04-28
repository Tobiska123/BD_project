package Model.Queries;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public abstract class AbstractQuery {
    protected Map<String, String> params;
    public abstract String generateQuery();
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
    public abstract String getNameQuery();

    public abstract Vector<String> getParamsName();
}
