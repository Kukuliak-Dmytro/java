package factories;

import entities.Flower;
import utils.ConfigLoader;
import utils.JSONUtil;

public abstract class AbstractFlowerFactory {
    protected String defaultName;
    protected String defaultColor;
    //    freshness is measured in hours since being harvested

    protected float defaultFreshness;
    protected float defaultStemLength;
    //    price is measured in UAH
    protected float price;

    public abstract Flower createFlower();
    
    public void loadConfigFromJSON(String flowerType){
        String json = ConfigLoader.getConfig();
        
        if (json == null) {
            setDefaults();
            return;
        }
        
        String key = "\"" + flowerType.toLowerCase() + "\"";
        int start = json.indexOf(key);
        if (start == -1) {
            setDefaults();
            return;
        }
        
        int objStart = json.indexOf("{", start);
        int objEnd = JSONUtil.findMatchingBrace(json, objStart);
        if (objEnd == -1) {
            setDefaults();
            return;
        }
        
        String config = json.substring(objStart, objEnd + 1);
        defaultName = JSONUtil.getString(config, "name");
        defaultColor = JSONUtil.getString(config, "color");
        defaultFreshness = JSONUtil.getFloat(config, "freshness");
        defaultStemLength = JSONUtil.getFloat(config, "stemLength");
        price = JSONUtil.getFloat(config, "price");
    }
    
    protected String getConfigJson() {
        String json = ConfigLoader.getConfig();
        if (json == null) return null;
        
        String flowerType = getFlowerType();
        String key = "\"" + flowerType.toLowerCase() + "\"";
        int start = json.indexOf(key);
        if (start == -1) return null;
        
        int objStart = json.indexOf("{", start);
        int objEnd = JSONUtil.findMatchingBrace(json, objStart);
        if (objEnd == -1) return null;
        
        return json.substring(objStart, objEnd + 1);
    }
    
    protected abstract String getFlowerType();
    
    public void getInfo(){
        System.out.println(getFlowerType().substring(0, 1).toUpperCase() + getFlowerType().substring(1));
    }
    
    private void setDefaults() {
        defaultName = "Default";
        defaultColor = "Default";
        defaultFreshness = 0.0f;
        defaultStemLength = 0.0f;
        price = 0.0f;
    }
}
