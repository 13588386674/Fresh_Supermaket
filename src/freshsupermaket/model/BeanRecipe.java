package freshsupermaket.model;

public class BeanRecipe {
    private int recipe_id;//食谱ID
    private String recipe_name;//食谱名称
    private String recipe_material;//食谱原料
    private String steps;//食谱步骤
    private String detail;//食谱描述

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getRecipe_material() {
        return recipe_material;
    }

    public void setRecipe_material(String recipe_material) {
        this.recipe_material = recipe_material;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
